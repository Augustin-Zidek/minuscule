package eu.zidek.augustin.minuscule;

import java.awt.Color;
import java.awt.Font;

/**
 * Container for labels.
 * 
 * @author Augustin Zidek
 * 
 */
class LabelContainer {
	private final String text;
	private final double x;
	private final double y;
	private final Color color;
	private final Font font;
	private final boolean fill;
	private final double eucledianYOffset;

	/**
	 * @param text The text in the label
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param color The color
	 * @param font The font of the label
	 * @param fill True if the label letters should be filled, false otherwise
	 * @param eucledianYOffset The offset by which the label should be moved
	 *            when displayed in Eucledian coordinate system. This is zero
	 *            for labels which are on their own, and half of the y offset if
	 *            the label belongs to a point.
	 */
	LabelContainer(final String text, final double x, final double y,
			final Color color, final Font font, final boolean fill,
			final double eucledianYOffset) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.color = color;
		this.font = font;
		this.fill = fill;
		this.eucledianYOffset = eucledianYOffset;
	}

	/**
	 * @return the text of the label
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * @return The x coordinate
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * @return The y coordinate
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * @return the font
	 */
	public Font getFont() {
		return this.font;
	}

	/**
	 * @return True if the label letters should be filled, false otherwise
	 */
	public boolean isFill() {
		return this.fill;
	}

	/**
	 * @return The offset by which the label should be moved when displayed in
	 *         Eucledian coordinate system. This is zero for labels which are on
	 *         their own, and half of the y offset if the label belongs to a
	 *         point (e.g. for a point with radius 5, the offset would be 5 /
	 *         sqrt(2)).
	 */
	public double getEucledianYOffset() {
		return this.eucledianYOffset;
	}

}
