package io.github.cottonmc.jankson;

import blue.endless.jankson.Jankson;

/**
 * CialloLib 原本依賴 cotton 的 jankson fork 所提供的工廠類,
 * 移植到 NeoForge 時改用上游 blue.endless:jankson, 這裡補一個等價的工廠方法。
 */
public class JanksonFactory {
    public static Jankson createJankson() {
        return Jankson.builder().build();
    }
}
