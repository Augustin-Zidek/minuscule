package eu.zidek.augustin.minuscule;

import java.awt.Color;

/**
 * Minuscule Rectangle with various attributes such as position, size, label,
 * circumference color, filling color, etc. MRectangle uses the Builder pattern
 * to add new values to attributes, if not invoked, default values are used.
 * 
 * @author Augustin Zidek
 *
 */
public class MRectangle extends MGeometricObject {
	private double x = Constants.DEFAULT_RECTANGLE_X_COORDINATE;
	private double y = Constants.DEFAULT_RECTANGLE_Y_COORDINATE;
	private double width = Constants.DEFAULT_RECTANGLE_WIDTH;
	private double height = Constants.DEFAULT_RECTANGLE_HEIGHT;

	/**
	 * Creates a new MRectangle with default properties.
	 */
	public MRectangle() {
		super(Constants.DEFAULT_RECTANGLE_COLOR,
				Constants.DEFAULT_RECTANGLE_FILL, null,
				Constants.DEFAULT_RECTANGLE_STROKE,
				Constants.DEFAULT_RECTANGLE_LAYER,
				Constants.DEFAULT_RECTANGLE_ZOOM_INDIFFERENCE);
	}

	/**
	 * @return The x coordinate of the rectangle's top left (in Euclidean
	 *         coordinates lower left) corner position.
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * @return The y coordinate of the rectangle's top left (in Euclidean
	 *         coordinates lower left) corner position.
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * @return The rectangle width
	 */
	public double getWidth() {
		return this.width;
	}

	/**
	 * @return The rectangle height
	 */
	public double getHeight() {
		return this.height;
	}

	/**
	 * Sets rectangle's top left (in Euclidean coordinates lower left) corner x
	 * coordinate.
	 * 
	 * @param x The x coordinate of the corner
	 * @return The rectangle with modified corner x coordinate
	 */
	public MRectangle x(final double x) {
		this.x = x;
		return this;
	}

	/**
	 * Sets rectangle's top left (in Euclidean coordinates lower left) corner y
	 * coordinate.
	 * 
	 * @param y The y coordinate of the corner
	 * @return The rectangle with modified corner y coordinate
	 */
	public MRectangle y(final double y) {
		this.y = y;
		return this;
	}

	/**
	 * Sets rectangle's top left (in Euclidean coordinates lower left) corner
	 * position.
	 * 
	 * @param x The x coordinate of the corner
	 * @param y The y coordinate of the corner
	 * @return The rectangle with modified corner coordinates
	 */
	public MRectangle pos(final double x, final double y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * Sets rectangle's width.
	 * 
	 * @param width The width of the rectangle
	 * @return The rectangle with modified width
	 */
	public MRectangle width(final double width) {
		this.width = width;
		return this;
	}

	/**
	 * Sets rectangle's height.
	 * 
	 * @param height The height of the rectangle
	 * @return The rectangle with modified height
	 */
	public MRectangle height(final double height) {
		this.height = height;
		return this;
	}

	/**
	 * Sets rectangle's dimensions (i.e. width and height).
	 * 
	 * @param width The width of the rectangle
	 * @param height The height of the rectangle
	 * @return The rectangle with modified dimenstions
	 */
	public MRectangle dimensions(final double width, final double height) {
		this.width = width;
		this.height = height;
		return this;
	}

	@Override
	public MRectangle draw(final Canvas canvas) {
		super.doDraw(canvas);
		return this;
	}

	@Override
	public MRectangle color(final Color color) {
		super.setColor(color);
		return this;
	}

	@Override
	public MRectangle fill(final boolean value) {
		super.setFill(value);
		return this;
	}

	@Override
	public MRectangle stroke(final MStroke stroke) {
		super.setStroke(stroke);
		return this;
	}

	@Override
	public MRectangle layer(final int layer) {
		super.setLayer(layer);
		return this;
	}

	@Override
	public MRectangle label(final String labelText) {
		return this
				.label(labelText, Constants.DEFAULT_RECTANGLE_LABEL_POSITION);
	}

	@Override
	public MRectangle label(final String labelText, final double angleDeg) {
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
	public MRectangle label(final MLabel label) {
		// Set the label's parent
		label.parent(this);
		super.setLabel(label);
		return this;
	}

	@Override
	public MRectangle zoomIndifferent(final boolean value) {
		super.setZoomIndifference(value);
		return this;
	}

	@Override
	public MRectangle translate(final double dx, final double dy) {
		this.x += dx;
		this.y += dy;
		return this;
	}

	@Override
	public MCoordinate getLabelBaseCoordinate() {
		final double baseX = this.x + this.width / 2;
		final double baseY = this.y + this.height / 2;
		return new MCoordinate(baseX, baseY);
	}

	@Override
	public MCoordinate getLabelCoordinates(final double angleDeg) {
		final MBoundingBox bb = this.getBoundingRectangle();
		return LabelPositioningUtils.getAutoPlacementToRectangle(angleDeg, bb,
				super.getLabel().getFont(), super.getLabel().getText());
	}

	@Override
	public MBoundingBox getBoundingRectangle() {
		return new MBoundingBox(this.x, this.y, this.width, this.height);
	}

}
