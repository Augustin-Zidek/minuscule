package eu.zidek.augustin.minuscule;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JSlider;

/**
 * Canvas Mouse Wheel Listener, that registers mouse wheel movement and zooms
 * in/out the canvas accordingly using the slider.
 * 
 * @author Augustin Zidek
 * 
 */
class CanvasMouseWheelListener implements MouseWheelListener {
	private final Canvas canvas;
	private final JSlider zoomSlider;

	/**
	 * Creates a new mouse wheel listener.
	 * 
	 * @param canvas The canvas to which wheel rotation (i.e. zoom) events
	 *            should be sent.
	 * @param zoomSlider The zoom slider which shall be moved when wheel rotated
	 *            (the slider has a listener that does the actual zooming)
	 */
	CanvasMouseWheelListener(final Canvas canvas, final JSlider zoomSlider) {
		this.canvas = canvas;
		this.zoomSlider = zoomSlider;
	}

	@Override
	public void mouseWheelMoved(final MouseWheelEvent e) {
		if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
			// Store the old value of the zoom
			final double oldZoom = this.canvas.getZoom();
			final MCoordinate oldTranslation = this.canvas.getTranslation();

			// Increase the zoom slider value
			final int oldSliderValue = this.zoomSlider.getValue();
			final int newSliderValue = oldSliderValue + e.getWheelRotation();
			this.zoomSlider.setValue(newSliderValue);

			// Get the new value of the zoom
			final double newZoom = this.canvas.getZoom();

			// Translate by vector p.newZoom - p.oldZoom
			final double zoomRatio = newZoom / oldZoom;

			// Translate the canvas so the mouse pointer stays on the same point
			// See documentation for the derivation of this equation
			this.canvas.translate((oldTranslation.x - e.getX())
					* (zoomRatio - 1), (oldTranslation.y - e.getY())
					* (zoomRatio - 1));

		}
	}
}
