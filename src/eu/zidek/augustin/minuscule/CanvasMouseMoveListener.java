package eu.zidek.augustin.minuscule;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Canvas Mouse Move Listener, that registers mouse dragging and moves the
 * canvas view accordingly.
 * 
 * @author Augustin Zidek
 * 
 */
class CanvasMouseMoveListener extends MouseAdapter {
	// Hold the coordinates before moving starts
	private int x;
	private int y;
	private final Canvas c;

	public CanvasMouseMoveListener(final Canvas canvas) {
		this.c = canvas;
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		// Store original coordinates
		this.x = e.getX();
		this.y = e.getY();
		// When moving, change cursor to the move cursor
		this.c.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		this.c.requestFocus();
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		this.c.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	};

	@Override
	public void mouseDragged(final MouseEvent e) {
		// Calculate the translation
		final int dx = e.getX() - this.x;
		final int dy = e.getY() - this.y;
		// Update the values of x and y
		this.x += dx;
		this.y += dy;

		this.c.translate(dx, dy);
	}
}
