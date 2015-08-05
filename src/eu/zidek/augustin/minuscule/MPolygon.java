package eu.zidek.augustin.minuscule;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Minuscule polygon. The polygon is a set of vertices (points) connected by
 * edges (lines) to the two neighbor points. To construct a polygon use the
 * method <code>addVertex()</code> to add new vertices. All the edges will be
 * added automatically (which can be turned off).
 * 
 * @author Augustin Zidek
 *
 */
public class MPolygon extends MGeometricObject {
	private List<MPoint> vertices = new ArrayList<>();
	private Color fillColor = Constants.DEFAULT_POLYGON_FILL_COLOR;

	/**
	 * Creates a new polygon with default properties.
	 */
	public MPolygon() {
		super(Constants.DEFAULT_POLYGON_COLOR, Constants.DEFAULT_POLYGON_FILL,
				null, Constants.DEFAULT_POLYGON_STROKE,
				Constants.DEFAULT_POLYGON_LAYER,
				Constants.DEFAULT_POLYGON_ZOOM_INDIFFERENCE);
	}

	/**
	 * @return The list of vertices of this polygon
	 */
	public List<MPoint> getVertices() {
		return this.vertices;
	}

	/**
	 * Gets the n-th vertex.
	 * 
	 * @param vertexNo The number of the vertex, determined by the order of
	 *            addition of the vertices.
	 * @return The vertex with the given number. Returns <code>null</code> if
	 *         the argument is negative or greater than the vertices amount.
	 */
	public MPoint getVertex(final int vertexNo) {
		if (vertexNo < 0 || vertexNo >= this.vertices.size()) {
			return null;
		}
		return this.vertices.get(vertexNo);
	}

	/**
	 * @return The fill color.
	 */
	public Color getFillColor() {
		return this.fillColor;
	}

	/**
	 * Adds new vertex to the polygon. The new vertex will be connected by an
	 * edge to the previously added vertex and the first vertex.
	 * 
	 * @param vertex The vertex to be added
	 * @return The polygon with added vertex
	 */
	public MPolygon addVertex(final MPoint vertex) {
		this.vertices.add(vertex);
		return this;
	}

	/**
	 * Adds new vertex to the polygon. The new vertex will be connected by an
	 * edge to the previously added vertex and the first vertex.
	 * 
	 * @param x The x coordinate of the new vertex
	 * @param y The y coordinate of the new vertex
	 * @return The polygon with added vertex
	 */
	public MPolygon addVertex(final double x, final double y) {
		final MPoint vertex = new MPoint().x(x).y(y);
		this.vertices.add(vertex);
		return this;
	}

	/**
	 * Deletes the n-th vertex of the polygon. The order is given by the order
	 * in which the vertices have been added.
	 * 
	 * @param vertexNo The number of the vertex to be deleted. If vertex with
	 *            the given number doesn't exist, nothing happens.
	 * @return The polygon with deleted vertex. The edges are reconnected
	 *         automatically.
	 */
	public MPolygon deleteVertex(final int vertexNo) {
		// Check if within the range
		if (vertexNo < 0 || vertexNo >= this.vertices.size()) {
			return this;
		}
		// Within range, delete
		this.vertices.remove(vertexNo);
		return this;
	}

	/**
	 * Deletes the vertex with the given coordinates. If no such vertex exists,
	 * nothing happens.
	 * 
	 * @param x The x coordinate of the vertex
	 * @param y The y coordinate of the vertex
	 * @return The polygon with deleted vertex. The edges are reconnected
	 *         automatically.
	 */
	public MPolygon deleteVertex(final double x, final double y) {
		// Go through all vertices and find the one with the given coordinates
		for (int i = 0; i < this.vertices.size(); i++) {
			final MPoint p = this.vertices.get(i);
			// If coordinates match, delete the vertex
			if (p.getX() == x && p.getY() == y) {
				this.vertices.remove(i);
			}
		}
		return this;
	}

	@Override
	public MPolygon draw(final Canvas canvas) {
		super.doDraw(canvas);
		return this;
	}

	@Override
	public void delete(final Canvas c) {
		c.removeGeometricObject(this);
	}

	@Override
	public MPolygon color(final Color color) {
		super.setColor(color);
		return this;
	}

	@Override
	public MPolygon fill(final boolean value) {
		super.setFill(value);
		return this;
	}

	/**
	 * Changes the fill color. The default fill color is black with
	 * <code>alpha = 100</code>. To obtain alpha colors use adequate
	 * constructors of {@link Color}.
	 * 
	 * @param color The color of the filling, <code>alpha = 100</code> is
	 *            recommended
	 * @return The polygon with modified fill color
	 */
	public MPolygon fillColor(final Color color) {
		this.fillColor = color;
		return this;
	}

	@Override
	public MPolygon stroke(final MStroke stroke) {
		super.setStroke(stroke);
		return this;
	}

	@Override
	public MPolygon layer(final int layer) {
		super.setLayer(layer);
		return this;
	}

	/**
	 * The base coordinate is calculated as centroid of the polygon (i.e. The
	 * centroid of a finite set of k points <code>x1, x2, ..., xk</code> is
	 * <code>Base = (x1 + x2 + ... + xk) / k</code>.
	 */
	@Override
	public MCoordinate getLabelBaseCoordinate() {
		double xSum = 0;
		double ySum = 0;
		// Sum all x coordinates and y coordinates
		for (final MPoint p : this.vertices) {
			xSum += p.getX();
			ySum += p.getY();
		}
		// Calculate average of these x coordinates and y coordinates
		final double vertexCount = this.vertices.size();
		return new MCoordinate(xSum / vertexCount, ySum / vertexCount);
	}

	@Override
	public MPolygon label(final String labelText) {
		return this.label(labelText, Constants.DEFAULT_POINT_LABEL_POSITION);
	}

	@Override
	public MPolygon label(final String labelText, final double angleDeg) {
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
	public MPolygon label(final MLabel label) {
		// Set the label's parent
		label.parent(this);
		super.setLabel(label);
		return this;
	}

	@Override
	public MPolygon zoomIndifferent(final boolean value) {
		super.setZoomIndifference(value);
		return this;
	}

	@Override
	public MPolygon translate(final double dx, final double dy) {
		// Translate the polygon
		for (final MPoint p : this.vertices) {
			p.translate(dx, dy);
		}
		// Note that if the polygon has label, it will be translated
		// automatically as it uses polygon's coordinates
		return this;
	}

	@Override
	public MCoordinate getLabelCoordinates(final double angleDeg) {
		return new MCoordinate(0, 0);
	}

	@Override
	public MBoundingBox getBoundingRectangle() {
		// Find min/max x/y coordinates
		double minX = this.vertices.get(0).getX();
		double maxX = this.vertices.get(0).getX();
		double minY = this.vertices.get(0).getY();
		double maxY = this.vertices.get(0).getY();

		for (final MPoint p : this.vertices) {
			final double px = p.getX();
			final double py = p.getY();
			// Search for vertical min/max
			if (px < minX) {
				minX = px;
			}
			else if (px > maxX) {
				maxX = px;
			}
			// Search for horizontal min/max
			if (py < minY) {
				minY = py;
			}
			else if (py > maxY) {
				maxY = py;
			}
		}

		return new MBoundingBox(minX, minY, maxX - minX, maxY - minY);
	}

}
