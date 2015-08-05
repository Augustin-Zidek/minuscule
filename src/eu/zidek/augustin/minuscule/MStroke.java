package eu.zidek.augustin.minuscule;

import java.awt.BasicStroke;
import java.awt.Stroke;

/**
 * The Minuscule library requires all strokes to have getThickness to make the
 * indifferent zooming work. So if you don't want to use the {@link BasicStroke}
 * and create your own custom strokes, they must implement this interface.
 * 
 * @author Augustin Zidek
 *
 */
public interface MStroke extends Stroke {

	/**
	 * @return The thickness of the stroke
	 */
	public double getThickness();

	/**
	 * Returns new identical <code>MStroke</code> but with changed thickness.
	 * 
	 * @param thickness The new thickness
	 * @return New MStroke which is identical to the original one except in
	 *         thickness
	 */
	public MStroke setThickness(final float thickness);
}
