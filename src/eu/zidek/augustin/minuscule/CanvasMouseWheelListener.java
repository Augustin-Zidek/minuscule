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
	private final JSlider zoomSlider;

	/**
	 * Creates a new mouse wheel listener.
	 * 
	 * @param c The canvas to which wheel rotation (i.e. zoom) events should be
	 *            sent.
	 */
	CanvasMouseWheelListener(final JSlider zoomSlider) {
		this.zoomSlider = zoomSlider;
	}

	@Override
	public void mouseWheelMoved(final MouseWheelEvent e) {
		if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
			
			int originalSliderValue = this.zoomSlider.getValue();
			this.zoomSlider.setValue(originalSliderValue + e.getWheelRotation());
		}
	}
}
