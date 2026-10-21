package io.github.cottonmc.cotton.gui.impl.client;

import java.io.IOException;

import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

import io.github.cottonmc.cotton.gui.impl.CialloLibCommon;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import org.jetbrains.annotations.Nullable;

public final class CialloLibShaders {
	private static @Nullable ShaderProgram tiledRectangle;

	public static void register(RegisterShadersEvent event) {
		// Register our core shaders.
		// The tiled rectangle shader is used for performant tiled texture rendering.
		try {
			event.registerShader(
					new ShaderProgram(event.getResourceProvider(), Identifier.of(CialloLibCommon.MOD_ID, "tiled_rectangle"), VertexFormats.POSITION),
					program -> tiledRectangle = program
			);
		} catch (IOException e) {
			// ShaderProgram's constructor declares a checked IOException since 1.21.1;
			// event listeners cannot rethrow it, so fail fast at registration time.
			throw new RuntimeException("Failed to register CialloLib shader: tiled_rectangle", e);
		}
	}

	private static ShaderProgram assertPresent(ShaderProgram program, String name) {
		if (program == null) {
			throw new NullPointerException("Shader ciallolib:" + name + " not initialised!");
		}

		return program;
	}

	public static ShaderProgram getTiledRectangle() {
		return assertPresent(tiledRectangle, "tiled_rectangle");
	}
}
