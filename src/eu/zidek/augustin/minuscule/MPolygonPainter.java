package eu.zidek.augustin.minuscule;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.List;

/**
 * A Painter for MPolygons.
 * 
 * @author Augustin Zidek
 *
 */
public class MPolygonPainter implements MGeometricObjectPainter {
	private final MPointPainter pointPainter;
	private final MLabelPainter labelPainter;

	/**
	 * Creates a new <code>MPointPainter</code>. However, as the MpointPainter
	 * allows custom vertices, it needs to use other painters to draw them.
	 * 
	 * @param pointPainter The <code>MPointPainter</code> used to paint the
	 *            vertices of the polygon.
	 * @param labelPainter The <code>MLabelPainter</code> used to paint the
	 *            labels of the vertices of the polygon.
	 */
	public MPolygonPainter(final MPointPainter pointPainter,
			final MLabelPainter labelPainter) {
		this.pointPainter = pointPainter;
		this.labelPainter = labelPainter;
	}

	/**
	 * Draw the filling of the polygon
	 * 
	 * @param fillColor The color of the fill
	 * @param g2d The graphics object on which to draw
	 * @param vertices The vertices of the polygon
	 */
	private void drawFilling(final Color fillColor, final Graphics2D g2d,
			final List<MPoint> vertices) {
		// The edges of the polygon form a 2D path (which can be also filled)
		final Path2D polygonFilling = new Path2D.Double();
		// Move the pen to the first vertex of the polygon
		polygonFilling.moveTo(vertices.get(0).getX(), vertices.get(0).getY());

		// Construct the path
		for (int i = 0; i < vertices.size(); i++) {
			final MPoint vertex1 = vertices.get(i);
			polygonFilling.lineTo(vertex1.getX(), vertex1.getY());
		}

		// Fill the polygon with the same color but add alpha
		polygonFilling.closePath();
		g2d.setColor(fillColor);
		g2d.fill(polygonFilling);
	}

	@Override
	public void paint(final MGeometricObject object, final Graphics2D g2d) {
		final MPolygon polygon = (MPolygon) object;

		// Get vertices and use them to construct edges
		final List<MPoint> vertices = polygon.getVertices();

		// If no vertices present, nothing to be drawn
		if (vertices.size() == 0) {
			return;
		}

		// If wanted, draw the filling first (so it is in the lowest layer)
		if (polygon.isFill()) {
			this.drawFilling(polygon.getFillColor(), g2d, vertices);
		}

		for (int i = 0; i < vertices.size(); i++) {
			// Get two consecutive vertices
			final MPoint vertex1 = vertices.get(i);
			final MPoint vertex2 = vertices.get((i + 1) % vertices.size());

			// Draw the edge first, so it is under the points
			g2d.setStroke(polygon.getStroke());
			g2d.setColor(polygon.getColor());

			// Draw the line between the two consecutive vertices
			final Line2D line = new Line2D.Double(vertex1.getX(),
					vertex1.getY(), vertex2.getX(), vertex2.getY());
			g2d.draw(line);

			// Draw the first vertex (the other one drawn in the next iteration)
			this.pointPainter.paint(vertex1, g2d);
			// If vertex has a label, paint it as well
			if (vertex1.getLabel() != null) {
				this.labelPainter.paint(vertex1.getLabel(), g2d);
			}
		}
	}
}
