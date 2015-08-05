package eu.zidek.augustin.minuscule;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * A Painter for MRectangles.
 * 
 * @author Augustin Zidek
 *
 */
public class MRectanglePainter implements MGeometricObjectPainter {

	@Override
	public void paint(final MGeometricObject rectangleObject,
			final Graphics2D g2d) {
		final MRectangle object = (MRectangle) rectangleObject;
		
		// Use Java built-in rectangle
		final Rectangle2D rectangle = new Rectangle2D.Double(object.getX(),
				object.getY(), object.getWidth(), object.getHeight());
		// Set stroke and color
		g2d.setStroke(object.getStroke());
		g2d.setColor(object.getColor());

		// Fill or draw
		if (object.isFill()) {
			g2d.fill(rectangle);
		}
		else {
			g2d.draw(rectangle);
		}

	}
}
