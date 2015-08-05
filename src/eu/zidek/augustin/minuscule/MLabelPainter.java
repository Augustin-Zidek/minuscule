package eu.zidek.augustin.minuscule;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * A Painter for MLabels.
 * 
 * @author Augustin Zidek
 *
 */
public class MLabelPainter implements MGeometricObjectPainter {

	private final Canvas canvas;

	/**
	 * MLabelPainter requires access to canvas: it needs to ask the canvas
	 * whether Euclidean coordinates are in use or not and draw the labels
	 * accordingly.
	 * 
	 * @param c The canvas this painter draws on
	 */
	public MLabelPainter(final Canvas c) {
		this.canvas = c;
	}

	@Override
	public void paint(final MGeometricObject labelObject, Graphics2D g2d) {
		final MLabel label = (MLabel) labelObject;

		// Get the label text and coordinates, makes code below more readable
		final String text = label.getText();

		final MGeometricObject parent = label.getParent();

		float x = 0;
		float y = 0;
		// Label has parent who determines position, get coordinates dynamically
		if (parent != null && label.isPositionSetByParent()) {
			final MCoordinate labelCoord = parent.getLabelCoordinates(label
					.getAngleToParent());
			x = (float) labelCoord.x;
			y = (float) labelCoord.y;
		}
		// Get the coordinates in a normal way
		else {
			x = (float) label.getX();
			y = (float) label.getY();
		}

		g2d.setColor(label.getColor());
		g2d.setFont(label.getFont());
		g2d.setStroke(label.getStroke());

		// Handle cases when the label is positioned relative to the parent
		// (i.e. not relative to the origin). Then the parent's coordinates need
		// to be added.
		float xOffset = 0;
		float yOffset = 0;
		if (parent != null) {
			final MCoordinate labelBaseCoord = parent.getLabelBaseCoordinate();
			xOffset = (float) labelBaseCoord.x;
			yOffset = (float) labelBaseCoord.y;
		}

		// If Euclidean, turn off y-scaling and do the transform manually
		if (this.canvas != null && this.canvas.isEuclidean()) {
			// Scale the label's font by (1, -1) as the Euclidean scale on the
			// whole canvas scales the labels as well
			final AffineTransform euclideanScale = AffineTransform
					.getScaleInstance(1, -1);
			g2d.setFont(label.getFont().deriveFont(euclideanScale));

			// If label has no parent use its x, y coordinates instead of offset
			if (parent == null) {
				g2d.drawString(text, x, y);
			}
			// Label has parent
			else {
				// Draw the label but use negative y coordinate, to do the
				// Euclidean transform but without y-scaling the label text
				g2d.drawString(text, x + xOffset, -y + yOffset);
			}
		}
		// Normal drawing, i.e. no Euclidean coordinates
		else {
			g2d.drawString(text, x + xOffset, y + yOffset);
		}
	}

}
