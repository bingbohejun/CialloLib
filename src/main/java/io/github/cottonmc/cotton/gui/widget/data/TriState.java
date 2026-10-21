package io.github.cottonmc.cotton.gui.widget.data;

/**
 * A three-state boolean, used for tri-state settings.
 *
 * <p>This is a replacement for the Fabric API's {@code TriState} that was
 * used before the NeoForge port.
 */
public enum TriState {
	/** The value is {@code true}. */
	TRUE,
	/** The value is {@code false}. */
	FALSE,
	/** The value is the default, which is decided elsewhere. */
	DEFAULT;

	/**
	 * Gets the value of this tri-state, falling back to the specified value
	 * if this tri-state is {@link #DEFAULT}.
	 *
	 * @param value the fallback value
	 * @return the resolved boolean value
	 */
	public boolean orElse(boolean value) {
		return this == DEFAULT ? value : this == TRUE;
	}

	/**
	 * Gets the boxed value of this tri-state, or {@code null} if it is {@link #DEFAULT}.
	 *
	 * @return the boxed value or null
	 */
	public Boolean get() {
		return switch (this) {
			case TRUE -> Boolean.TRUE;
			case FALSE -> Boolean.FALSE;
			case DEFAULT -> null;
		};
	}
}
