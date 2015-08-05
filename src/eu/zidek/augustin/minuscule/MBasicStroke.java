package eu.zidek.augustin.minuscule;

import java.awt.BasicStroke;
import java.awt.Stroke;

/**
 * Wrapper for {@link BasicStroke} which implements the {@link MStroke}
 * interface.
 * 
 * @see BasicStroke
 * @author Augustin Zidek
 *
 */
public class MBasicStroke extends BasicStroke implements MStroke, Stroke {

	/**
	 * Constructs a new MBacisStroke.
	 */
	public MBasicStroke() {
		super();
	}

	/**
	 * @see BasicStroke#BasicStroke(float)
	 */
	public MBasicStroke(final float width) {
		super(width);
	}

	/**
	 * @see BasicStroke#BasicStroke(float, int, int)
	 */
	public MBasicStroke(final float width, final int cap, final int join) {
		super(width, cap, join);
	}

	/**
	 * @see BasicStroke#BasicStroke(float, int, int, float)
	 */
	public MBasicStroke(final float width, final int cap, final int join,
			final float mitterLimit) {
		super(width, cap, join, mitterLimit);
	}

	/**
	 * @see BasicStroke#BasicStroke(float, int, int, float, float[], float)
	 */
	public MBasicStroke(final float width, final int cap, final int join,
			final float mitterLimit, final float[] dash, final float dash_phase) {
		super(width, cap, join, mitterLimit, dash, dash_phase);
	}

	@Override
	public double getThickness() {
		return super.getLineWidth();
	}

	@Override
	public MStroke setThickness(final float thickness) {
		return new MBasicStroke(thickness, super.getEndCap(),
				super.getLineJoin(), super.getMiterLimit(),
				super.getDashArray(), super.getDashPhase());
	}
}
