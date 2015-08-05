package eu.zidek.augustin.minuscule;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;

/**
 * A 2d shape with other properties such as color and stroke type.
 * 
 * @author Augustin Zidek
 * 
 */
class Object2DContainer {
	private final Shape shape;
	private final Color color;
	private final Stroke stroke;
	private final boolean fill;

	/**
	 * @param shape The shape
	 * @param color The color
	 * @param stroke The stroke
	 * @param fill True if the object should be filled, false otherwise
	 */
	Object2DContainer(final Shape shape, final Color color,
			final Stroke stroke, final boolean fill) {
		this.shape = shape;
		this.color = color;
		this.stroke = stroke;
		this.fill = fill;
	}

	/**
	 * @return the shape
	 */
	public Shape getShape() {
		return this.shape;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * @return the stroke
	 */
	public Stroke getStroke() {
		return this.stroke;
	}

	/**
	 * @return True if the object should be filled, false otherwise
	 */
	public boolean isFill() {
		return this.fill;
	}

}
