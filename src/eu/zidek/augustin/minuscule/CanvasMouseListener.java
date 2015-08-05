package eu.zidek.augustin.minuscule;

import java.awt.Cursor;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Canvas Mouse Move Listener, that registers mouse dragging and moves the
 * canvas view accordingly.
 * 
 * @author Augustin Zidek
 * 
 */
class CanvasMouseListener extends MouseAdapter {
	// Hold the coordinates before moving starts
	private int x;
	private int y;
	private final Canvas canvas;
	private final MinusculeWindow window;

	CanvasMouseListener(final Canvas canvas, final MinusculeWindow window) {
		this.canvas = canvas;
		this.window = window;
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		// Right button was pressed, show the pop-up menu
		if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
			final CanvasPopUpMenu menu = new CanvasPopUpMenu(this.canvas,
					this.window);
			menu.show(e.getComponent(), e.getX(), e.getY());
			return;
		}

		// Store original coordinates
		this.x = e.getX();
		this.y = e.getY();
		// When moving, change cursor to the move cursor
		this.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		this.canvas.requestFocus();
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		this.canvas
				.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	};

	@Override
	public void mouseDragged(final MouseEvent e) {
		// Right button was dragged, ignore it
		if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
			return;
		}

		// Calculate the translation
		final int dx = e.getX() - this.x;
		final int dy = e.getY() - this.y;
		// Update the values of x and y
		this.x += dx;
		this.y += dy;

		this.canvas.translate(dx, dy);
	}
}
