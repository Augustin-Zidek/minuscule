package eu.zidek.augustin.minuscule;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JSlider;

class CanvasKeyboardListener implements KeyListener {
	private final Canvas canvas;
	private final JSlider zoomSlider;

	public CanvasKeyboardListener(final Canvas canvas, final JSlider zoomSlider) {
		this.canvas = canvas;
		this.zoomSlider = zoomSlider;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Nothing to be done, keyPressed does everything
	}

	@Override
	public void keyPressed(final KeyEvent e) {
		// Get the key code
		final int keyCode = e.getKeyCode();
		final double zoom = this.canvas.getZoom();
		// Decide on action according to the key pressed
		if (keyCode == KeyEvent.VK_UP) {
			this.canvas.translate(0, 10 * zoom);
		}
		else if (keyCode == KeyEvent.VK_LEFT) {
			this.canvas.translate(10 * zoom, 0);
		}
		else if (keyCode == KeyEvent.VK_DOWN) {
			this.canvas.translate(0, -10 * zoom);
		}
		else if (keyCode == KeyEvent.VK_RIGHT) {
			this.canvas.translate(-10 * zoom, 0);
		}
		// Reset the translation, zoom and set the zoom slider
		else if (keyCode == KeyEvent.VK_HOME) {
			this.canvas.resetTranslationAndZoom();
			this.zoomSlider.setValue(Constants.ZOOM_1_VALUE);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Nothing to be done, keyPressed does everything
	}
}
