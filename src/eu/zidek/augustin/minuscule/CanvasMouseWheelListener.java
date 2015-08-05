package eu.zidek.augustin.minuscule;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Canvas Mouse Wheel Listener, that registers mouse wheel movement and zooms
 * in/out the canvas accordingly using the slider.
 * 
 * @author Augustin Zidek
 * 
 */
class CanvasMouseWheelListener implements MouseWheelListener {
	private final Canvas canvas;

	/**
	 * Creates a new mouse wheel listener.
	 * 
	 * @param canvas The canvas to which wheel rotation (i.e. zoom) events
	 *            should be sent.
	 * @param zoomSlider The zoom slider which shall be moved when wheel rotated
	 *            (the slider has a listener that does the actual zooming)
	 */
	CanvasMouseWheelListener(final Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void mouseWheelMoved(final MouseWheelEvent e) {
		// If mouse wheel rotated
		if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
			final double zoom = this.canvas.getZoom();
			// If rotation in positive direction --> zoom in
			if (e.getWheelRotation() > 0) {
				this.canvas.setZoomToPoint(
						zoom * Constants.DEFAULT_ZOOM_IN_FACTOR,
						new MCoordinate(e.getX(), e.getY()));
			}
			// Otherwise zoom out
			else {
				this.canvas.setZoomToPoint(zoom
						* Constants.DEFAULT_ZOOM_OUT_FACTOR,
						new MCoordinate(e.getX(), e.getY()));
			}
		}
	}
}
