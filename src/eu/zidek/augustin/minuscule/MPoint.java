package eu.zidek.augustin.minuscule;

import java.awt.Color;
import java.awt.Stroke;

/**
 * Minuscule point with various attributes such as position, radius, label,
 * color and filling. MPoint uses the Builder pattern to add new values to
 * attributes, if not invoked, default values are used.
 * 
 * @author Augustin Zidek
 * 
 */
public class MPoint extends MGeometricObject {
	private double x = Constants.DEFAULT_POINT_X_COORDINATE;
	private double y = Constants.DEFAULT_POINT_Y_COORDINATE;
	private double radius = Constants.DEFAULT_POINT_RADIUS;

	/**
	 * Creates a new point with default properties.
	 */
	public MPoint() {
		super(Constants.DEFAULT_POINT_COLOR, Constants.DEFAULT_POINT_FILL,
				null, Constants.DEFAULT_POINT_STROKE,
				Constants.DEFAULT_POINT_LAYER);
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
	 * @return The radius of the point
	 */
	public double getRadius() {
		return this.radius;
	}

	/**
	 * Sets point's x coordinate.
	 * 
	 * @param x The new x coordinate
	 * @return The point with modified x coordinate
	 */
	public MPoint x(final double x) {
		this.x = x;
		return this;
	}

	/**
	 * Sets point's y coordinate.
	 * 
	 * @param y The new y coordinate
	 * @return The point with modified y coordinate
	 */
	public MPoint y(final double y) {
		this.y = y;
		return this;
	}

	/**
	 * Sets point's position, i.e. x and y coordinates.
	 * 
	 * @param x The new x coordinate
	 * @param y The new y coordinate
	 * @return The point with modified x and y coordinates
	 */
	public MPoint pos(final double x, final double y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * Sets the point radius to the given value.
	 * 
	 * @param radius The new radius of the point
	 * @return The point with modified radius
	 */
	public MPoint radius(final double radius) {
		this.radius = radius;
		return this;
	}

	/**
	 * Sets the point diameter to the given value.
	 * 
	 * @param diameter The new diameter of the point
	 * @return The point with modified diameter
	 */
	public MPoint diameter(final double diameter) {
		return this.radius(diameter / 2);
	}

	@Override
	public MPoint label(final String labelText) {
		return this.label(labelText, Constants.DEFAULT_POINT_LABEL_POSITION);
	}

	@Override
	public MPoint label(final String labelText, final double angleDeg) {
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
	public MPoint label(final MLabel label) {
		// Set the label's parent
		label.parent(this);
		super.setLabel(label);
		return this;
	}

	@Override
	public MPoint color(final Color color) {
		super.setColor(color);
		return this;
	}

	@Override
	public MPoint fill(final boolean value) {
		super.setFill(value);
		return this;
	}

	@Override
	public MPoint stroke(final Stroke stroke) {
		super.setStroke(stroke);
		return this;
	}

	@Override
	public MPoint layer(final int layer) {
		super.setLayer(layer);
		return this;
	}

	@Override
	public MPoint translate(final double dx, final double dy) {
		// Translate the point
		this.x += dx;
		this.y += dy;
		// Note that if the point has label, it will be translated automatically
		// as it uses point's coordinates
		return this;
	}

	@Override
	public MPoint draw(final Canvas c) {
		super.doDraw(c);
		return this;
	}

	@Override
	public MCoordinate getLabelBaseCoordinate() {
		return new MCoordinate(this.x, this.y);
	}

	@Override
	public MCoordinate getLabelCoordinates(final double angleDeg) {
		// Return radius + 5 to make some space around the point
		return super.getLabelCoordinate(angleDeg, this.radius);
	}

	@Override
	public MBoundingBox getBoundingRectangle() {
		// Calculate upper-left coordinates
		final double x = this.x - this.radius;
		final double y = this.y - this.radius;
		return new MBoundingBox(x, y, this.radius * 2, this.radius * 2);
	}

}
