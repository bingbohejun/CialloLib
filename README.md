# CialloLib

Minecraft NeoForge 1.21.1 的 GUI 函式库模组，由我从 [CottonMC/LibGui](https://github.com/CottonMC/LibGui) 移植并独立化维护（上游 10.0.0，MIT）。

原本以「LibGui 移植版」内嵌在 MahjongGame里，现独立为单独的模组，作为任何需要网格式 GUI 的模组的可复用依赖（MahjongGame 已将其声明为必需依赖）。

- 运行环境：**Minecraft 1.21.1 + NeoForge 21.1.127+**
- 许可：MIT

---

## 功能特性

- **网格式 GUI 框架**：以 `WPlainPanel` 等容器用网格方式摆放控件，不用手动算每个控件的坐标。
- **常用控件**：标签（`WLabel` / `WDynamicLabel`）、按钮（`WButton`）、文本框（`WTextField`）、滑块（`WSlider`）、物品槽等继承自 LibGui 的组件齐全。
- **HUD 框架 CottonHud**：可在游戏画面叠加自定义 HUD 面板（MahjongGame 的左侧快捷操作、牌提示就是用它做的）。
- **背景绘制 BackgroundPainter**：支持纯色 / 九宫格（内嵌 LibNinepatch）等多种背景。
- **配置读写**：内嵌 Jankson，支持 `.json5` 配置文件的读写。

---

## 环境要求与依赖

CialloLib 自身需要的运行环境：

| 依赖                   | 说明                       |
| -------------------- | ------------------------ |
| NeoForge 21.1.127+    | 必需运行环境                  |
| Minecraft 1.21.1      | 必需                       |
| LibNinepatch（内嵌）      | 九宫格贴图绘制（MIT）            |
| Jankson 1.2.3（内嵌）    | `.json5` 配置读写（MIT）       |

LibNinepatch 与 Jankson 通过 Jar-in-Jar 内嵌，**无需单独下载**。

---

## 与上游 LibGui 的差异

- **模组标识**：`libgui` → `ciallolib`；资源命名空间 `assets/libgui` → `assets/ciallolib`。
- **入口类**：`LibGuiCommon` → `CialloLibCommon`（另有 `CialloLibClient` / `CialloLibConfig` / `CialloLibShaders` / `CialloLibMixinPlugin` / `CialloLib`）。
- **配置文件**：`config/libgui.json5` → `config/ciallolib.json5`。
- **Java 包名保持不变**（`io.github.cottonmc.cotton.gui.*`），便于与上游对照、也避免破坏调用方的 import。
- **1.21.1 适配**：`Tessellator.begin(...)`、`Identifier.of(...)`、mixin `compatibilityLevel = JAVA_21`、`HandledScreen` 的 GUI 描述绘制改为 `@At("TAIL")`。

---

## 构建（开发用）

```bash
./gradlew build
# 产物:
#   build/libs/ciallolib-mc1.21.1-<version>.jar         ← 正式环境用 (Mojmap 重映射)
#   build/devlibs/ciallolib-mc1.21.1-<version>-dev.jar   ← 开发环境编译/运行用 (yarn)
```

消费方（如 MahjongGame）开发时，需要把这里的 **dev jar** 放进其工程的 `libs/`（或 `run/mods/`）：

```bash
cp build/devlibs/ciallolib-mc1.21.1-*-dev.jar ../MahjongCraft-1.21.1-neoforge/libs/
```

---

## 致谢与许可

- 本库 fork 自 [CottonMC/LibGui](https://github.com/CottonMC/LibGui)（MIT, © The Cotton Project，credits: Falkreon, Juuz），并针对 NeoForge 1.21.1 移植、独立化。
- 内嵌 LibNinepatch（MIT）、Jankson（MIT）。
- 以 **MIT** 许可发布。
