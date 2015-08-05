package eu.zidek.augustin.minuscule;

import java.awt.Color;

/**
 * The most general interface for all geometric objects the canvas supports.
 * Every object that wants to draw on the canvas should implement this
 * interface.
 * 
 * The <code>compareTo()</code> method uses the information about the object's
 * layer to impose total order. This is used heavily in the Canvas to display
 * objects in the right z-order.
 * 
 * @author Augustin Zidek
 * 
 */
public abstract class MGeometricObject implements Comparable<MGeometricObject> {
	private Color color;
	private boolean fill;
	private MLabel label;
	private MStroke stroke;
	private int layer;
	private boolean zoomIndifferent;

	// Determines if the object should be added to canvas, or just repainted
	private boolean shouldBeAddedToCanvas = true;
	// Determines if the layer has been changed since the last draw and the
	// canvas objects needs to be resorted
	private boolean shouldBeLayerUpdated = false;

	/**
	 * Constructor for the classes that extend MGeometricObject.
	 * 
	 * @param color The color of the object
	 * @param fill <code>true</code> if the object should be filled,
	 *            <code>false</code> otherwise
	 * @param label The label of the object
	 * @param stroke The stroke used to draw the object
	 * @param layer The layer at which the object is located
	 * @param zoomIndifferent <code>true</code> if the object should be zoom
	 *            indifferent, <code>false</code> otherwise
	 */
	protected MGeometricObject(final Color color, final boolean fill,
			final MLabel label, final MStroke stroke, final int layer,
			final boolean zoomIndifferent) {
		this.color = color;
		this.fill = fill;
		this.label = label;
		this.stroke = stroke;
		this.layer = layer;
		this.zoomIndifferent = zoomIndifferent;
	}

	// PUBLIC GETTERS: To be used mostly by Canvas

	/**
	 * @return The color of the geometric object. If none set returns the
	 *         concrete object's default color.
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * @return <code>true</code> if the geometric object is filled,
	 *         <code>false</code> otherwise
	 */
	public boolean isFill() {
		return this.fill;
	}

	/**
	 * @return The label of this geometric object. If none, returns
	 *         <code>null</code>.
	 */
	public MLabel getLabel() {
		return this.label;
	}

	/**
	 * @return The stroke used by this object. If none set returns the concrete
	 *         object's default stroke.
	 */
	public MStroke getStroke() {
		return this.stroke;
	}

	/**
	 * @return The layer of the object. The higher layer number, the later the
	 *         object is drawn - e.g. object at layer 0 will be under object at
	 *         layer 4.<br>
	 *         The default layer value is determined by the type of the
	 *         geometric object (points are at layer 1, label 2, all other
	 *         objects 0).
	 */
	public int getLayer() {
		return this.layer;
	}

	/**
	 * @return The zoom indifference, i.e. <code>true</code> if the object is
	 *         zoom indifferent, <code>false</code> otherwise
	 */
	public boolean isZoomIndifferent() {
		return this.zoomIndifferent;
	}

	// PROTECTED SETTERS AND OTHER METHODS: To be used only by extending classes

	protected void setColor(final Color color) {
		this.color = color;
	}

	protected void setFill(final boolean fill) {
		this.fill = fill;
	}

	protected void setLabel(final MLabel label) {
		// If no label, create a new one
		if (this.label == null) {
			this.label = label;
		}
		// Else copy the new label into the old one
		else {
			this.label.copy(label);
		}
	}

	protected void setStroke(final MStroke stroke) {
		this.stroke = stroke;
	}

	protected void setLayer(final int layer) {
		this.layer = layer;
		// Force the canvas to resort the objects upon drawing
		this.shouldBeLayerUpdated = true;
	}

	protected void setZoomIndifference(final boolean value) {
		this.zoomIndifferent = value;
	}

	protected void doDraw(final Canvas c) {
		// If any, draw the objects's label
		if (this.label != null) {
			this.label.draw(c);
		}
		// If the object hasn't been drawn yet, draw it
		if (this.shouldBeAddedToCanvas) {
			// Add the object itself, will do repaint
			c.addGeometricObject(this);
			// Make sure object is from this moment only repainted
			this.shouldBeAddedToCanvas = false;
			return;
		}
		// Object only needs to be redrawn as its properties have been changed
		else {
			// Layer has been modified, force z order sort
			if (this.shouldBeLayerUpdated) {
				c.updateObjectsLayer(this);
				this.shouldBeLayerUpdated = false;
			}
			// Repaint the modified object after x ms. This way multiple objects
			// repainted closely after each other are repainted only once.
			c.repaint(Constants.DEFAULT_REPAINT_INTERVAL_MS);
		}
	}

	/**
	 * Returns the relative coordinates for a label at the given angle and
	 * distance from the base. The method should make sure that the coordinates
	 * put the label in such place that it is visually nicely placed relative to
	 * the object.
	 * 
	 * @param angleDeg The angle between the positive x-axis and the left corner
	 *            of the label at which the label should be put
	 * @param distanceFromBase The desired distance from the base coordinate
	 * @return The coordinates of the label according to the given angle and the
	 *         properties of the object
	 */
	protected MCoordinate getLabelCoordinate(final double angleDeg,
			final double distanceFromBase) {
		// If null return position for a default label
		if (this.label == null) {
			return LabelPositioningUtils.getAutoPlacementToCircle(angleDeg,
					Constants.DEFAULT_FONT, "", distanceFromBase);
		}
		// Determine the actual font size of the label and get its text
		return LabelPositioningUtils.getAutoPlacementToCircle(angleDeg,
				this.label.getFont(), this.label.getText(), distanceFromBase);
	}

	// ABSTRACT METHODS: To be implemented by extending objects

	/**
	 * Adds the object into the internal list of objects in the canvas and calls
	 * the <code>repaint()</code> method of the {@link Canvas}.
	 * 
	 * Any modifications to the geometric object done after calling this method
	 * will be ignored (e.g. <code>MPoint.draw(canvas).x(20).y(20);</code>) will
	 * draw the point at the default (0, 0) coordinates).
	 * 
	 * @param canvas The canvas on which the object should be drawn
	 * @return The object that has been drawn
	 */
	public abstract MGeometricObject draw(final Canvas canvas);

	/**
	 * Sets the object's color to the given color. If not called upon an object,
	 * the object's default color is used - see documentation for individual
	 * objects.
	 * 
	 * @param color The new color to be set
	 * @return The object which color has been set
	 */
	public abstract MGeometricObject color(final Color color);

	/**
	 * Changes the property of the object so that it will be filled rather when
	 * being drawn. If not called upon an object, the object's default value is
	 * used - see documentation for individual objects.
	 * 
	 * @param value <code>true</code> if the object should be filled,
	 *            <code>false</code> otherwise
	 * @return The object which has been set/unset to be filled
	 */
	public abstract MGeometricObject fill(final boolean value);

	/**
	 * Sets the object's stroke. Stroke modifies the pen that draws the object,
	 * e.g. one can modify the thickness of the pen.
	 * 
	 * @param stroke The stroke to be used when drawing this object. If not
	 *            called, default Minuscule Stroke will be used.
	 * @return The object which has the given stroke applied
	 * @see MStroke
	 */
	public abstract MGeometricObject stroke(final MStroke stroke);

	/**
	 * Sets the object's layer to the given value. The layer determines when the
	 * object is drawn - the higher layer, the later the object will be drawn.
	 * 
	 * @param layer The layer number
	 * @return The object with layer set
	 */
	public abstract MGeometricObject layer(final int layer);

	/**
	 * Adds a label with default color, default font family and default font
	 * size located northeast relative to the object. If the object already has
	 * any label, it will be replaced by this label.
	 * 
	 * @param labelText The label string value
	 * @return The object with the label set
	 */
	public abstract MGeometricObject label(final String labelText);

	/**
	 * Adds a label with default color, default font family and default font
	 * size located according to the location parameter. If the object already
	 * has any label, it will be replaced by this label.
	 * 
	 * @param labelText The label string value
	 * @param angleDeg The angle relative to the x-axis in positive direction at
	 *            which the label should be placed according to the object's
	 *            properties
	 * @return The object with the label set
	 */
	public abstract MGeometricObject label(final String labelText,
			final double angleDeg);

	/**
	 * Adds a custom label to the object, sets the object as its parent. That
	 * means that the label coordinates will be now relative to the parent's
	 * Label Base Coordinate. If the object already has any label, it will be
	 * replaced by this label.
	 * 
	 * If you want the object to determine the label position for you
	 * automatically, use either <code>label(String s)</code> method or
	 * <code>label(String s, int pos)</code> method.
	 * 
	 * @param label The label to be added to the object
	 * @return The object with the label set
	 */
	public abstract MGeometricObject label(final MLabel label);

	/**
	 * Translates the object by the given translation vector.
	 * 
	 * @param dx The translation in the x direction
	 * @param dy The translation in the y direction
	 * @return The translated object
	 */
	public abstract MGeometricObject translate(final double dx, final double dy);

	/**
	 * Sets zoom indifference - i.e. if an object is zoom indifferent, then
	 * zooming the canvas in and out doesn't change its dimensions. By default
	 * objects are <b>not</b> zoom indifferent.
	 * 
	 * @param value <code>true</code> if object should be zoom indifferent,
	 *            <code>false</code> otherwise
	 * @return The object with modified zoom indifference
	 */
	public abstract MGeometricObject zoomIndifferent(final boolean value);

	/**
	 * If a label is on its own (i.e. it doesn't have a parent), its position is
	 * absolute. However, if label has a parent, then its position is relative
	 * to a certain point defined by the parent. This method returns such a
	 * point.
	 * 
	 * For instance, for a point centered at <code>(10,10)</code>, the label
	 * base coordinate point is also at <code>(10,10)</code> and the label's
	 * position is for instance set to <code>(5,5)</code> relative to its
	 * parent's base coordinate. Hence the absolute label's position will be
	 * <code>(15,15)</code>.
	 * 
	 * Other examples: For line the label base point is defined at the midpoint.
	 * For square as its middle.
	 * 
	 * @return The coordinate of the label base point
	 */
	public abstract MCoordinate getLabelBaseCoordinate();

	/**
	 * Returns the relative coordinates for a label of this object at the given
	 * angle given in degrees. The method should make sure that the coordinates
	 * put the label in such place that it is visually nicely placed relative to
	 * the object.
	 * 
	 * @param angleDeg The angle at which the label should be put
	 * @return The coordinates of the label according to the given angle and the
	 *         properties of the object
	 */
	public abstract MCoordinate getLabelCoordinates(final double angleDeg);

	// PUBLIC METHODS: To be used from outside

	/**
	 * Deletes the given object from the canvas. Also all objects which this
	 * object is parent to will be deleted.
	 * 
	 * @param canvas The canvas from which the object should be deleted
	 */
	public void delete(final Canvas canvas) {
		canvas.removeGeometricObject(this);
		if (this.label != null) {
			canvas.removeGeometricObject(this.label);
		}
		// If one wants to add the object again after deleting it
		this.shouldBeAddedToCanvas = true;
	}

	/**
	 * Calculates the bounding rectangle of the given geometric object and
	 * returns it. The coordinates are absolute (i.e. relative to the origin,
	 * not to the parent object).
	 * 
	 * @return The bounding rectangle of the geometric object.
	 */
	public abstract MBoundingBox getBoundingRectangle();

	@Override
	public int compareTo(final MGeometricObject obj) {
		// The higher layer, the greater the object is.
		return this.layer - obj.getLayer();
	}
}
