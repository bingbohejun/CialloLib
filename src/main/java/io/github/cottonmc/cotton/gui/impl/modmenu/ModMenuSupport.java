package io.github.cottonmc.cotton.gui.impl.modmenu;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import io.github.cottonmc.cotton.gui.client.CottonClientScreen;

public class ModMenuSupport {
	public IConfigScreenFactory getConfigScreenFactory() {
		return (ModContainer modContainer, Screen screen) -> new CottonClientScreen(Text.translatable("options.libgui.libgui_settings"), new ConfigGui(screen)) {
			@Override
			public void close() {
				this.client.setScreen(screen);
			}
		};
	}
}
