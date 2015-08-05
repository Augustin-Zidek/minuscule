package eu.zidek.augustin.minuscule;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * A Painter for points.
 * 
 * @author Augustin Zidek
 *
 */
public class MPointPainter implements MGeometricObjectPainter {

	@Override
	public void paint(final MGeometricObject pointObject, final Graphics2D g2d) {
		final MPoint object = (MPoint) pointObject;

		// Construct a circle with given properties
		final double radius;
		// If zoom indifferent, divide the radius by the zoom
		if (object.isZoomIndifferent()) {
			radius = object.getRadius() / g2d.getTransform().getScaleX();
		}
		else {
			radius = object.getRadius();
		}
		final double diameter = radius * 2;
		// Recalculate coordinates so the center is in the middle, not in
		// upper-left corner of the bounding rectangle
		final Ellipse2D point = new Ellipse2D.Double(object.getX() - radius,
				object.getY() - radius, diameter, diameter);
		g2d.setStroke(object.getStroke());
		g2d.setColor(object.getColor());

		// Fill or draw
		if (object.isFill()) {
			g2d.fill(point);
		}
		else {
			g2d.draw(point);
		}
	}
}
