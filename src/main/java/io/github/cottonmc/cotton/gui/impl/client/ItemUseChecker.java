package io.github.cottonmc.cotton.gui.impl.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.util.Pair;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;

import org.jetbrains.annotations.Nullable;

import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Crashes the game if a CialloLib screen is opened in {@code Item.use/useOnBlock/useOnEntity}.
 */
public final class ItemUseChecker {
	// Setting this property to "true" disables the check.
	private static final String ALLOW_ITEM_USE_PROPERTY = "ciallolib.allowItemUse";

	// Stack walker instance used to check the caller.
	private static final StackWalker STACK_WALKER =
			StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

	// List of banned item use methods (yarn 1.21.1 names).
	private static final List<Pair<String, MethodType>> ITEM_USE_METHODS = Util.make(new ArrayList<>(), result -> {
		try {
			// use
			result.add(new Pair<>("use", MethodType.methodType(
				Class.forName("net.minecraft.util.TypedActionResult"),
				Class.forName("net.minecraft.world.World"),
				Class.forName("net.minecraft.entity.player.PlayerEntity"),
				Class.forName("net.minecraft.util.Hand"))));
			// useOnBlock
			result.add(new Pair<>("useOnBlock", MethodType.methodType(
				Class.forName("net.minecraft.util.ActionResult"),
				Class.forName("net.minecraft.item.ItemUsageContext"))));
			// useOnEntity
			result.add(new Pair<>("useOnEntity", MethodType.methodType(
				Class.forName("net.minecraft.util.ActionResult"),
				Class.forName("net.minecraft.item.ItemStack"),
				Class.forName("net.minecraft.entity.player.PlayerEntity"),
				Class.forName("net.minecraft.entity.LivingEntity"),
				Class.forName("net.minecraft.util.Hand"))));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could not resolve Minecraft class for ItemUseChecker", e);
		}
	});

	/**
	 * Checks whether the specified screen is a CialloLib screen opened
	 * from an item usage method.
	 *
	 * @throws CrashException if opening the screen is not allowed
	 */
	public static void checkSetScreen(Screen screen) {
		if (!(screen instanceof CottonScreenImpl cs) || Boolean.getBoolean(ALLOW_ITEM_USE_PROPERTY)) return;

		// Check if this is called via Item.use. If so, crash the game.

		// The calling variant of Item.use[OnBlock|OnEntity].
		// If null, nothing bad happened.
		@Nullable Pair<? extends Class<?>, String> useMethodCaller = STACK_WALKER.walk(s -> s
						.skip(3) // checkSetScreen, setScreen injection, setScreen
						.flatMap(frame -> {
							if (!Item.class.isAssignableFrom(frame.getDeclaringClass())) return Stream.empty();

							return ITEM_USE_METHODS.stream()
									.filter(method -> method.getLeft().equals(frame.getMethodName()) &&
											method.getRight().equals(frame.getMethodType()))
									.map(method -> new Pair<>(frame.getDeclaringClass(), method.getLeft()))
									.limit(1);
						})
						.findFirst())
				.orElse(null);

		if (useMethodCaller != null) {
			String message = """
						[CialloLib] Screens cannot be opened in item use methods. Some alternatives include:
							- Using a packet together with LightweightGuiDescription
							- Using an ItemSyncedGuiDescription
						Setting the screen in item use methods leads to threading issues and
						other potential crashes on both the client and the server.
						If you want to disable this check, set the system property %s to "true"."""
					.formatted(ALLOW_ITEM_USE_PROPERTY);
			var cause = new UnsupportedOperationException(message);
			cause.fillInStackTrace();
			CrashReport report = CrashReport.create(cause, "Opening screen");
			report.addElement("Screen opening details")
					.add("Screen class", screen.getClass().getName())
					.add("GUI description", () -> cs.getDescription().getClass().getName())
					.add("Item class", () -> useMethodCaller.getLeft().getName())
					.add("Involved method", () -> useMethodCaller.getRight());
			throw new CrashException(report);
		}
	}
}
