package io.github.cottonmc.cotton.gui.client;

import io.github.cottonmc.cotton.gui.impl.client.CialloLibClient;

/**
 * This class provides access to CialloLib configuration and other global data.
 *
 * @since 4.0.0
 */
public final class CialloLib {
	private CialloLib() {
	}

	/**
	 * Returns whether CialloLib is running in dark mode and widgets should use dark theming.
	 *
	 * @return true if widgets should use dark theming, false otherwise
	 */
	public static boolean isDarkMode() {
		return CialloLibClient.config.darkMode;
	}
}
