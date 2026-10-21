package io.github.cottonmc.cotton.gui.impl;

import io.github.cottonmc.cotton.gui.impl.client.CialloLibClient;
import io.github.cottonmc.cotton.gui.impl.client.CialloLibShaders;
import io.github.cottonmc.cotton.gui.impl.modmenu.ModMenuSupport;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@Mod(CialloLibCommon.MOD_ID)
public final class CialloLibCommon {
	public static final String MOD_ID = "ciallolib";

	public CialloLibCommon(ModContainer modContainer, IEventBus modEventBus) {
		modEventBus.addListener(RegisterPayloadHandlersEvent.class, event ->
				ScreenNetworkingImpl.init(event.registrar("1")));

		if (FMLLoader.getDist().isClient()) {
			modEventBus.addListener(RegisterShadersEvent.class, CialloLibShaders::register);
			modContainer.registerExtensionPoint(IConfigScreenFactory.class, new ModMenuSupport().getConfigScreenFactory());
			CialloLibClient.onInitializeClient();
		}
	}
}
