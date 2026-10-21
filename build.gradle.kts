plugins {
    id("dev.architectury.loom") version "1.9-SNAPSHOT"
}

base {
    val minecraftVersion: String = project.property("minecraft_version") as String
    val archivesBaseName: String = project.property("archives_base_name") as String
    archivesName.set("$archivesBaseName-mc$minecraftVersion")
}

version = project.property("mod_version") as String
group = project.property("maven_group") as String

repositories {
    // 同 MahjongCraft: 本环境下 maven.neoforged.net 会被 DNS 污染, 用 forgecdn 镜像
    maven("https://neoforged.forgecdn.net/releases")
    mavenCentral()
    maven("https://maven.fabricmc.net/")
    maven("https://maven.architectury.dev/")
    maven("https://jitpack.io/")
}

dependencies {
    val minecraftVersion: String = project.property("minecraft_version") as String
    minecraft("com.mojang:minecraft:$minecraftVersion")
    val yarnMappings: String = project.property("yarn_mappings") as String
    val yarnPatch: String = project.property("yarn_patch") as String
    mappings(loom.layered {
        mappings("net.fabricmc:yarn:$yarnMappings:v2")
        mappings("dev.architectury:yarn-mappings-patch-neoforge:$yarnPatch")
    })
    val neoforgeVersion: String = project.property("neoforge_version") as String
    neoForge("net.neoforged:neoforge:$neoforgeVersion")

    // dev 啟動時 FML 解析 versionRange 需要 (與 MahjongCraft 同因)
    runtimeOnly("org.apache.maven:maven-artifact:3.8.5")

    // 以下兩個以 JiJ 內嵌, 讓 CialloLib 自包含 (不依賴 MahjongCraft)
    // LibNinepatch: 九宮格貼圖繪製
    include(api("com.github.TexBlock:LibNinepatch:master-SNAPSHOT")!!)
    // Jankson: .json5 設定檔讀寫
    include(api("blue.endless:jankson:1.2.3")!!)
}

tasks {
    val javaVersion = JavaVersion.VERSION_21
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(javaVersion.toString().toInt())
        sourceCompatibility = "$javaVersion"
        targetCompatibility = "$javaVersion"
    }
    processResources {
        inputs.property("version", project.version)
        filesMatching("META-INF/neoforge.mods.toml") {
            expand(mutableMapOf("version" to project.version))
        }
    }
    jar {
        from("LICENSE") { rename { "${it}_${base.archivesName.get()}" } }
    }
    java {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        withSourcesJar()
    }
}
