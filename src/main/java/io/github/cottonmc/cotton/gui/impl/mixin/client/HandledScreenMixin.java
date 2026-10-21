package io.github.cottonmc.cotton.gui.impl.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
abstract class HandledScreenMixin {
	// 1.21.1 的 HandledScreen#render 已不再调用 super.render(Screen#render),
	// 原本的 INVOKE 注入点不存在(會 Scanned 0 target(s))。
	// 改為注入方法末尾: 語意相同 —— 畫面繪製完成後再畫 GUI 描述文字。
	@Inject(method = "render", at = @At("TAIL"), allow = 1)
	private void onSuperRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo info) {
		if ((Object) this instanceof CottonInventoryScreen<?> cottonInventoryScreen) {
			cottonInventoryScreen.paintDescription(context, mouseX, mouseY, delta);
		}
	}
}
