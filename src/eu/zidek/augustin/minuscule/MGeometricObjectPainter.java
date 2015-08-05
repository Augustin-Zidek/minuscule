package eu.zidek.augustin.minuscule;

import java.awt.Graphics2D;

/**
 * An interface for strategy that draws a given object. Each canvas object that
 * implements MObject has to have a strategy implementing this interface that is
 * able to draw this object.
 * 
 * There are two advantages in holding this in a separate class (not as a method
 * of each MObject implementation): <br>
 * 1) The MObject implementations are lighter, as they all share this code and
 * hence only one strategy class can be reused for multiple OBject
 * implementations of the same type. <br>
 * 2) Code clarity and using the principle
 * "Single class, single responsibility".
 * 
 * Naming convention: For an M[object name] use M[object name]Painter
 * 
 * @author Augustin Zidek
 *
 */
public interface MGeometricObjectPainter {

	/**
	 * Method called by the {@link Canvas} when painting the object. This method
	 * receives the Graphics2D held internally by the <code>Canvas</code> and
	 * paints the object on it. E.g. if there was an object <code>House</code>
	 * and corresponding <code>HousePainter&lt;House&gt;</code>, <b>this</b> method
	 * would draw the bottom yellow square, two blue squares for windows, a red
	 * triangle as a roof.
	 * 
	 * If you want to have direct (low-level) access to the graphics, define
	 * this method when adding own custom complex shapes and draw directly to
	 * the given {@link Graphics2D} object using appropriate methods from the
	 * Java libraries.
	 * 
	 * If you want to define your own shape using Minuscule objects only, use
	 * preferably the <code>draw</code> method in the object itself.
	 * 
	 * @param object The object to be drawn
	 * @param g2d The {@link Graphics2D} on which the object should paint itself
	 */
	public void paint(final MGeometricObject object, final Graphics2D g2d);

}
