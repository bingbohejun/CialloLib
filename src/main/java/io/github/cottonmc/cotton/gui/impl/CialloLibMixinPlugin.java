package io.github.cottonmc.cotton.gui.impl;

import net.neoforged.fml.loading.FMLLoader;

import com.google.common.base.Suppliers;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public final class CialloLibMixinPlugin implements IMixinConfigPlugin {
	private static final Supplier<Boolean> IS_DEVELOPMENT =
			Suppliers.memoize(() -> !FMLLoader.isProduction());

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		return switch (mixinClassName) {
			// 僅開發環境需要的檢查(阻止以物品使用開啟 CialloLib 畫面), 正式環境不套用。
			// 注意: 原字串包名少了 .cotton.gui, 導致永遠不匹配而變成全域套用。
			case "io.github.cottonmc.cotton.gui.impl.mixin.client.MinecraftClientMixin" -> IS_DEVELOPMENT.get();
			default -> true;
		};
	}

	// Boilerplate below

	@Override
	public void onLoad(String mixinPackage) {
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
	}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}
}
