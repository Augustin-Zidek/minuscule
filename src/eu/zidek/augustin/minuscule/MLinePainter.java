package eu.zidek.augustin.minuscule;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * A Painter for MLines.
 * 
 * @author Augustin Zidek
 *
 */
public class MLinePainter implements MGeometricObjectPainter {

	@Override
	public void paint(final MGeometricObject lineObject, final Graphics2D g2d) {
		final MLine object = (MLine) lineObject;

		final Line2D line = new Line2D.Double(object.getStartX(),
				object.getStartY(), object.getEndX(), object.getEndY());
		// If zoom indifferent, divide thickness by the zoom
		if (object.isZoomIndifferent()) {
			g2d.setStroke(new BasicStroke((float) (object.getThickness() / g2d
					.getTransform().getScaleX())));
		}
		else {
			g2d.setStroke(object.getStroke());
		}
		g2d.setColor(object.getColor());

		// Line can't be filled, filling modified using Stroke
		g2d.draw(line);

	}

}
