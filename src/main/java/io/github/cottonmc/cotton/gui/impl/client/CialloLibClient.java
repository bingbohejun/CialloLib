package io.github.cottonmc.cotton.gui.impl.client;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import io.github.cottonmc.cotton.gui.client.CottonHud;
import io.github.cottonmc.cotton.gui.impl.Proxy;
import io.github.cottonmc.jankson.JanksonFactory;
import net.neoforged.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class CialloLibClient {
	public static final Logger logger = LogManager.getLogger();
	public static volatile CialloLibConfig config;

	public static final Jankson jankson = JanksonFactory.createJankson();

	public static void onInitializeClient() {
		config = loadConfig();
		CottonHud.registerEvents();
		Proxy.proxy = new ClientProxy();
	}

	public static CialloLibConfig loadConfig() {
		try {
			Path file = FMLPaths.CONFIGDIR.get().resolve("ciallolib.json5");

			if (Files.notExists(file)) saveConfig(new CialloLibConfig());

			JsonObject json;
			try (InputStream in = Files.newInputStream(file)) {
				json = jankson.load(in);
			}

			config =  jankson.fromJson(json, CialloLibConfig.class);

			/*
			JsonElement jsonElementNew = jankson.toJson(new CialloLibConfig());
			if(jsonElementNew instanceof JsonObject) {
				JsonObject jsonNew = (JsonObject) jsonElementNew;
				if(json.getDelta(jsonNew).size()>= 0) { //TODO: Insert new keys as defaults into `json` IR object instead of writing the config out, so comments are preserved
					saveConfig(config);
				}
			}*/
		} catch (Exception e) {
			logger.error("[CialloLib] Error loading config: {}", e.getMessage());
		}
		return config;
	}

	public static void saveConfig(CialloLibConfig config) {
		try {
			Path file = FMLPaths.CONFIGDIR.get().resolve("ciallolib.json5");

			JsonElement json = jankson.toJson(config);
			String result = json.toJson(true, true);
			Files.write(file, result.getBytes(StandardCharsets.UTF_8));
		} catch (Exception e) {
			logger.error("[CialloLib] Error saving config: {}", e.getMessage());
		}
	}
}
