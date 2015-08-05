package eu.zidek.augustin.minuscule;

import java.awt.Rectangle;

/**
 * Representation of bounding rectangle, i.e. a rectangle, that with the
 * smallest area within which all the bounded objects lie. E.g. for a circle
 * with center at (5,5) and radius 5 the bounding rectangle will have upper left
 * corner at (0,10) and lower right corner at (10,0).
 *
 * The bounding rectangle is defined by its upper-left corner coordinates and
 * its height and width.
 * 
 * @author Augustin Zidek
 *
 */
public class MBoundingBox {
	/**
	 * The x coordinate of the upper-left corner of the bounding rectangle.
	 */
	public double x;
	/**
	 * The y coordinate of the upper-left corner of the bounding rectangle.
	 */
	public double y;
	/**
	 * The width of the bounding rectangle.
	 */
	public double width;
	/**
	 * The height of the bounding rectangle.
	 */
	public double height;

	/**
	 * Constructs a new bounding rectangle whose upper-left corner is specified
	 * as <code>(x,y)</code> and whose width and height are specified by the
	 * arguments of the same name.
	 * 
	 * @param x The x coordinate of the upper-left corner of the bounding
	 *            rectangle
	 * @param y The y coordinate of the upper-left corner of the bounding
	 *            rectangle
	 * @param width The width of the bounding rectangle
	 * @param height The height of the bounding rectangle
	 */
	public MBoundingBox(final double x, final double y, final double width,
			final double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Gets the bounding rectangle with integer coordinates and dimensions that
	 * bounds this bounding rectangle. Also, this method is here for
	 * convenience, as it returns the Java built-in type which is commonly used.
	 * 
	 * @return The integer bounding rectangle
	 */
	public Rectangle getBoundingIntRectangle() {
		final int xInt = (int) Math.floor(this.x);
		final int yInt = (int) Math.floor(this.y);
		final int widthInt = (int) Math.ceil(this.width) + 1;
		final int heightInt = (int) Math.ceil(this.height) + 1;
		return new Rectangle(xInt, yInt, widthInt, heightInt);
	}
}
