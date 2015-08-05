/**
 * 
 */
package eu.zidek.augustin.minuscule;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

/**
 * 
 * Minuscule line defined by two points - the starting point and the ending
 * point. The line has various attributes which can be modified using
 * appropriate methods, otherwise they will be set to the default (reasonable)
 * values.
 * 
 * @author Augustin Zidek
 * 
 */
public class MLine extends MGeometricObject {
	private double x1 = 0;
	private double y1 = 0;
	private double x2 = 0;
	private double y2 = 0;
	private double thickness = Constants.DEFAULT_LINE_THICKNESS;

	// private MLabel label;
	// private Color color = Consts.DEFAULT_LINE_COLOR;
	// private boolean fill = Consts.DEFAULT_LINE_FILL;
	// private Stroke stroke = Consts.DEFAULT_LINE_STROKE;

	/**
	 * Creates a new line with default properties.
	 */
	public MLine() {
		super(Constants.DEFAULT_LINE_COLOR, Constants.DEFAULT_LINE_FILL, null,
				Constants.DEFAULT_LINE_STROKE, Constants.DEFAULT_LINE_LAYER);
	}

	/**
	 * @return The starting point x coordinate
	 */
	public double getStartX() {
		return this.x1;
	}

	/**
	 * @return The starting point y coordinate
	 */
	public double getStartY() {
		return this.y1;
	}

	/**
	 * @return The ending point x coordinate
	 */
	public double getEndX() {
		return this.x2;
	}

	/**
	 * @return The ending point y coordinate
	 */
	public double getEndY() {
		return this.y2;
	}

	/**
	 * @return The thickness of the line
	 */
	public double getThickness() {
		return this.thickness;
	}

	/**
	 * Sets the starting point's x coordinate.
	 * 
	 * @param x The x coordinate of the starting point
	 * @return The line with modified starting point's x coordinate
	 */
	public MLine startX(final double x) {
		this.x1 = x;
		return this;
	}

	/**
	 * Sets the starting point's y coordinate.
	 * 
	 * @param y The y coordinate of the starting point
	 * @return The line with modified starting point's y coordinate
	 */
	public MLine startY(final double y) {
		this.y1 = y;
		return this;
	}

	/**
	 * Sets the ending point's x coordinate.
	 * 
	 * @param x The x coordinate of the ending point
	 * @return The line with modified ending point's x coordinate
	 */
	public MLine endX(final double x) {
		this.x2 = x;
		return this;
	}

	/**
	 * Sets the ending point's y coordinate.
	 * 
	 * @param y The y coordinate of the ending point
	 * @return The line with modified ending point's y coordinate
	 */
	public MLine endY(final double y) {
		this.y2 = y;
		return this;
	}

	/**
	 * Sets the starting point's coordinates.
	 * 
	 * @param x The x coordinate of the starting point
	 * @param y The y coordinate of the starting point
	 * @return The line with modified starting point's coordinates
	 */
	public MLine start(final double x, final double y) {
		this.x1 = x;
		this.y1 = y;
		return this;
	}

	/**
	 * Sets the ending point's coordinates.
	 * 
	 * @param x The x coordinate of the ending point
	 * @param y The y coordinate of the ending point
	 * @return The line with modified ending point's coordinates
	 */
	public MLine end(final double x, final double y) {
		this.x2 = x;
		this.y2 = y;
		return this;
	}

	/**
	 * Changes the thickness of the line. This is achieved by modifying the
	 * stroke, so if you're using your own custom stroke using this method is
	 * not encouraged as it set the stroke to a new one.
	 * 
	 * @param thickness The thickness of the line
	 * @return The line with a new thickness set
	 */
	public MLine thickness(final float thickness) {
		super.setStroke(new BasicStroke(thickness));
		this.thickness = thickness;
		return this;
	}

	@Override
	public MLine draw(final Canvas c) {
		super.doDraw(c);
		return this;
	}

	@Override
	public MLine color(final Color color) {
		super.setColor(color);
		return this;
	}

	@Override
	public MLine fill(final boolean value) {
		super.setFill(value);
		return this;
	}

	@Override
	public MLine stroke(final Stroke stroke) {
		super.setStroke(stroke);
		return this;
	}

	@Override
	public MLine layer(final int layer) {
		super.setLayer(layer);
		return this;
	}

	@Override
	public MLine label(final String labelText) {
		return this.label(labelText, Constants.DEFAULT_POINT_LABEL_POSITION);
	}

	@Override
	public MLine label(final String labelText, final double angleDeg) {
		final MLabel label = new MLabel(labelText);
		// Set text, parent and position set by parent to true. Since position
		// set by parent set, no need to set coordinates.
		label.text(labelText);
		label.parent(this);
		label.positionSetByParent(true, angleDeg);
		super.setLabel(label);
		return this;
	}

	@Override
	public MLine label(final MLabel label) {
		// Set the label's parent
		label.parent(this);
		super.setLabel(label);
		return this;
	}

	@Override
	public MLine translate(final double dx, final double dy) {
		// Translate the line
		this.x1 += dx;
		this.y1 += dy;
		this.x2 += dx;
		this.y2 += dy;
		// Note that if the line has label, it will be translated automatically
		// as it uses line's coordinates
		return this;
	}

	/**
	 * The base point for the label in this case is the midpoint of the line.
	 */
	@Override
	public MCoordinate getLabelBaseCoordinate() {
		final double baseX = (this.x2 + this.x1) / 2;
		final double baseY = (this.y2 + this.y1) / 2;
		return new MCoordinate(baseX, baseY);
	}

	@Override
	public MCoordinate getLabelCoordinates(final double angleDeg) {
		return super.getLabelCoordinate(angleDeg, this.thickness / 2);
	}

	@Override
	public MBoundingBox getBoundingRectangle() {
		final double boundX = Math.min(this.x1, this.x2);
		final double boundY = (boundX == this.x1) ? this.y1 : this.y2;
		final double width = Math.abs(this.x1 - this.x2);
		final double height = Math.abs(this.y1 - this.y2);

		return new MBoundingBox(boundX, boundY, width, height);
	}

}
