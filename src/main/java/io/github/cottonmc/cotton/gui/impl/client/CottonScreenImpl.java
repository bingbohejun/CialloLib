package io.github.cottonmc.cotton.gui.impl.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public interface CottonScreenImpl {
	GuiDescription getDescription();

	@Nullable
	WWidget getLastResponder();

	void setLastResponder(@Nullable WWidget lastResponder);
}
