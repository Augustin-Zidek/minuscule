package eu.zidek.augustin.minuscule;

import java.awt.Graphics2D;

/**
 * A Painter for MSahpe.
 * 
 * @author Augustin Zidek
 *
 */
public class MShapePainter implements MGeometricObjectPainter {

	@Override
	public void paint(final MGeometricObject object, final Graphics2D g2d) {
		final MShape shape = (MShape) object;

		// Set the color and stroke
		g2d.setColor(shape.getColor());
		g2d.setStroke(shape.getStroke());

		// Fill or draw according to the fill property of the object
		if (shape.isFill()) {
			g2d.fill(shape.getShape());
		}
		else {
			g2d.draw(shape.getShape());
		}

	}

}
