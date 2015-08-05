package eu.zidek.augustin.minuscule;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * This class serves as a wrapper for Java Shape objects. It enables Minuscule
 * users to insert these shapes and use all their properties within Minuscule.
 * 
 * @author Augustin Zidek
 *
 */
public class MShape extends MGeometricObject {
	private Shape shape;

	/**
	 * Creates a new Minuscule shape object which holds a Java graphics Shape in
	 * it.
	 * 
	 * @param shape The shape that should be held and drawn.
	 */
	public MShape(final Shape shape) {
		super(Constants.DEFAULT_SHAPE_COLOR, Constants.DEFAULT_SHAPE_FILL, null,
				Constants.DEFAULT_SHAPE_STROKE, Constants.DEFAULT_SHAPE_LAYER);
		this.shape = shape;
	}

	@Override
	public MShape draw(final Canvas canvas) {
		super.doDraw(canvas);
		return this;
	}

	@Override
	public void delete(final Canvas c) {
		c.removeGeometricObject(this);
	}

	@Override
	public MShape color(final Color color) {
		super.setColor(color);
		return this;
	}

	@Override
	public MShape fill(final boolean value) {
		super.setFill(value);
		return this;
	}

	@Override
	public MShape stroke(final Stroke stroke) {
		super.setStroke(stroke);
		return this;
	}

	@Override
	public MShape layer(final int layer) {
		super.setLayer(layer);
		return this;
	}

	/**
	 * @return The shape that is held by this object
	 */
	public Shape getShape() {
		return this.shape;
	}

	/**
	 * Sets the internal shape to the given shape.
	 * 
	 * @param shape The shape to be held by the Minuscule shape
	 * @return The MShape with the modified shape in it
	 */
	public MShape shape(final Shape shape) {
		this.shape = shape;
		return this;
	}

	@Override
	public MShape label(final String labelText) {
		return this.label(labelText, Constants.DEFAULT_POINT_LABEL_POSITION);
	}

	@Override
	public MShape label(final String labelText, final double angleDeg) {
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
	public MShape label(final MLabel label) {
		// Set the label's parent
		label.parent(this);
		super.setLabel(label);
		return this;
	}

	@Override
	public MShape translate(final double dx, final double dy) {
		// Translate the shape
		final AffineTransform transform = AffineTransform.getTranslateInstance(
				dx, dy);
		this.shape = transform.createTransformedShape(this.shape);
		// Note that if the shape has label, it will be translated automatically
		// as it uses shape's coordinates
		return this;
	}

	/**
	 * Returns the midpoint of the bounding rectangle.
	 */
	@Override
	public MCoordinate getLabelBaseCoordinate() {
		final Rectangle2D bB = this.shape.getBounds2D();
		final double baseX = bB.getX() + bB.getWidth() / 2;
		final double baseY = bB.getY() + bB.getHeight() / 2;

		return new MCoordinate(baseX, baseY);
	}

	@Override
	public MCoordinate getLabelCoordinates(double angleDeg) {
		return new MCoordinate(0, 0);
	}

	@Override
	public MBoundingBox getBoundingRectangle() {
		// Get the Shape's bounding box
		final Rectangle2D bB = this.shape.getBounds2D();
		// Return it in more lightweight Minuscule bounding box
		return new MBoundingBox(bB.getX(), bB.getY(), bB.getWidth(),
				bB.getHeight());
	}

}
