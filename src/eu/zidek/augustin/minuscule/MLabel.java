package eu.zidek.augustin.minuscule;

import static eu.zidek.augustin.minuscule.Constants.DEFAULT_FONT;
import static eu.zidek.augustin.minuscule.Constants.DEFAULT_LABEL_COLOR;
import static eu.zidek.augustin.minuscule.Constants.DEFAULT_LABEL_FILL;
import static eu.zidek.augustin.minuscule.Constants.DEFAULT_LABEL_IS_UNMOVABLE;
import static eu.zidek.augustin.minuscule.Constants.DEFAULT_LABEL_LAYER;
import static eu.zidek.augustin.minuscule.Constants.DEFAULT_LABEL_STROKE;
import static eu.zidek.augustin.minuscule.Constants.DEFAULT_POINT_LABEL_POSITION;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

/**
 * Minuscule label with various attributes, such as position, text, color,
 * filling and parent (a MObject to which this label is bound). MLabel uses the
 * Builder pattern to add new values to attributes, if not invoked, default
 * values are used.
 * 
 * @author Augustin Zidek
 * 
 */
public class MLabel extends MGeometricObject {
	private String labelText = "";
	private double x = 0;
	private double y = 0;
	private Font font = DEFAULT_FONT;
	private MGeometricObject parent = null;
	private boolean isPosSetByParent = DEFAULT_LABEL_IS_UNMOVABLE;
	private double angleToParent = DEFAULT_POINT_LABEL_POSITION;

	/**
	 * Creates a new label with the given text and default properties.
	 * 
	 * @param text The text of the label
	 */
	public MLabel(final String text) {
		super(DEFAULT_LABEL_COLOR, DEFAULT_LABEL_FILL, null,
				DEFAULT_LABEL_STROKE, DEFAULT_LABEL_LAYER);
		this.labelText = text;
	}

	/**
	 * A copy constructor for MLabel. This is useful in various Geometric
	 * Objects in the method that sets the object's label to the given label.
	 * This constructor can be used to easily replace the old label with this
	 * new one.
	 * 
	 * @param label The label which contents should be copied into this label
	 */
	public void copy(final MLabel label) {
		this.labelText = label.getText();
		this.x = label.getX();
		this.y = label.getY();
		this.font = label.getFont();
		this.parent = label.getParent();
		this.isPosSetByParent = label.isPositionSetByParent();
		this.angleToParent = label.getAngleToParent();

		super.setColor(label.getColor());
		super.setFill(label.isFill());
		super.setLabel(label.getLabel());
		super.setStroke(label.getStroke());
	}

	/**
	 * @return The text of the label. Default value is empty string.
	 */
	public String getText() {
		return this.labelText;
	}

	/**
	 * @return The x coordinate of the label. Default value is 0.
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * @return The y coordinate of the label. Default value is 0.
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * @return The font of the label. Default font is Arial.
	 */
	public Font getFont() {
		return this.font;
	}

	/**
	 * @return The parent
	 */
	public MGeometricObject getParent() {
		return this.parent;
	}

	/**
	 * 
	 * @return the size of the label's font
	 */
	public int getFontSize() {
		return this.font.getSize();
	}

	/**
	 * @return <code>true</code> if the label is movable (i.e. its position is
	 *         determined by its parent), <code>false</code> otherwise (its
	 *         position is entirely dependent only on parent's position and the
	 *         label's position)
	 */
	public boolean isPositionSetByParent() {
		return this.isPosSetByParent;
	}

	/**
	 * @return An angle in degrees from the x-axis in positive direction at
	 *         which the label is located relative to the parent
	 */
	public double getAngleToParent() {
		return this.angleToParent;
	}

	/**
	 * Sets the text of the label.
	 * 
	 * @param text The new text to be set
	 * @return The label with modified text
	 */
	public MLabel text(final String text) {
		this.labelText = text;
		return this;
	}

	/**
	 * Sets the x coordinate of the label, the coordinate determines the
	 * position of the lower left corner of the label. If not set, the default
	 * value is 0.
	 * 
	 * @param x The x coordinate of the lower left corner
	 * @return The label with modified x coordinate
	 */
	public MLabel x(final double x) {
		this.x = x;
		return this;
	}

	/**
	 * Sets the y coordinate of the label, the coordinate determines the
	 * position of the lower left corner of the label. If not set, the default
	 * value is 0.
	 * 
	 * @param y The y coordinate of the lower left corner
	 * @return The label with modified y coordinate
	 */
	public MLabel y(final double y) {
		this.y = y;
		return this;
	}

	/**
	 * Sets the position of the label, the coordinates determine the position of
	 * the lower left corner of the label. If not set, the default value is
	 * (0,0).
	 * 
	 * @param x The x coordinate of the lower left corner
	 * @param y The y coordinate of the lower left corner
	 * @return The label with modified (x,y) coordinates
	 */
	public MLabel pos(final double x, final double y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * Sets the font size of the label to the given value.
	 * 
	 * @param size The new size of the label's font
	 * @return The label with changed font size
	 */
	public MLabel fontSize(final float size) {
		this.font = this.font.deriveFont(size);
		return this;
	}

	/**
	 * Sets the font of the label to the given font.
	 * 
	 * @param font The new font the label should have
	 * @return The label with the new font set
	 */
	public MLabel font(final Font font) {
		this.font = font;
		return this;
	}

	@Override
	public MLabel label(final String labelText) {
		return this.label(labelText, DEFAULT_POINT_LABEL_POSITION);
	}

	@Override
	public MLabel label(final String labelText, final double angleDeg) {
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
	public MLabel label(final MLabel label) {
		// Set the label's parent
		label.parent(this);
		super.setLabel(label);
		return this;
	}

	@Override
	public MLabel color(final Color color) {
		super.setColor(color);
		return this;
	}

	@Override
	public MLabel fill(final boolean value) {
		super.setFill(value);
		return this;
	}

	@Override
	public MLabel stroke(final Stroke stroke) {
		super.setStroke(stroke);
		return this;
	}

	@Override
	public MLabel layer(final int layer) {
		super.setLayer(layer);
		return this;
	}

	@Override
	public MLabel translate(final double dx, final double dy) {
		// Translate the label
		this.x += dx;
		this.y += dy;
		// Note that if the label has label, it will be translated automatically
		// as it uses label's coordinates
		return this;
	}

	/**
	 * Sets the parent for this label. If the parent is set, whenever it moves
	 * by vector (dx,dy) also the label is moved by vector (dx,dy).
	 * 
	 * @param parent Parent of this label, i.e. an {@link MGeometricObject} that
	 *            "owns" the label
	 * @return The label with a parent set
	 */
	public MLabel parent(final MGeometricObject parent) {
		this.parent = parent;
		return this;
	}

	@Override
	public MLabel draw(final Canvas c) {
		super.doDraw(c);
		return this;
	}

	@Override
	public void delete(final Canvas c) {
		c.removeGeometricObject(this);
	}

	/**
	 * By default the label determines its position on its own. That means its
	 * position is determined by the position of the parent (if any) and the
	 * label's coordinates.
	 * 
	 * If the label is set to be determined by the parent (i.e.
	 * <code>positionSetByParent(true)</code>), its position is determined by
	 * the method <code>getLabelPosition()</code> of the parent. Any coordinates
	 * of the label are ignored in this case. Only if the parent is
	 * <code>null</code> and hence its <code>getLabelPosition()</code> method
	 * can't be called, then the label's coordinates are used.
	 * 
	 * @param value The value to be set
	 * @param angleDeg The position to the parent. See
	 *            <code>getPositionToBaseCoord()</code> above for details.
	 * @return The label with the movability set
	 */
	public MLabel positionSetByParent(final boolean value, final double angleDeg) {
		this.isPosSetByParent = value;
		this.angleToParent = angleDeg;
		return this;
	}

	/**
	 * Returns the midpoint of the bounding rectangle
	 */
	@Override
	public MCoordinate getLabelBaseCoordinate() {
		// Get font width and height
		final double width = this.getBoundingRectangle().width;
		final double height = this.getBoundingRectangle().height;

		// Calculate the midpoint of the bounding rectangle
		final double baseX = this.x + (width / 2);
		final double baseY = this.y - (height / 2);

		return new MCoordinate(baseX, baseY);
	}

	/**
	 * Aligns the label's label to the bounding rectangle of the label.
	 */
	@Override
	public MCoordinate getLabelCoordinates(final double angleDeg) {
		final MBoundingBox bb = this.getBoundingRectangle();
		return LabelPositioningUtils.getAutoPlacementToRectangle(angleDeg, bb,
				super.getLabel().getFont(), super.getLabel().getText());
	}

	@Override
	public MBoundingBox getBoundingRectangle() {
		// Get label width and height
		final MCoordinate labelDim = LabelPositioningUtils.getLabelDimensions(
				this.labelText, this.font);
		final double width = labelDim.x;
		final double height = labelDim.y;

		// Get parent's coordinates (if any)
		double parentXOff = 0;
		double parentYOff = 0;
		if (this.parent != null) {
			final MCoordinate prntBaseCoord = this.parent
					.getLabelBaseCoordinate();
			parentXOff = prntBaseCoord.x;
			parentYOff = prntBaseCoord.y;
			// Coordinates set by parent, calculate them and set them
			final MCoordinate labelCoord = this.parent.getLabelCoordinates(this
					.getAngleToParent());
			this.x = labelCoord.x;
			this.y = labelCoord.y;
		}
		// Return absolute coordinates (hence take into account the parent)
		return new MBoundingBox(this.x + parentXOff, this.y + parentYOff
				- height, width, height);
	}

}
