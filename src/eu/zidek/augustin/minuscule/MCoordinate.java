package eu.zidek.augustin.minuscule;

/**
 * Lightweight immutable 2D coordinate.
 * 
 * @author Augustin Zidek
 * 
 */
public class MCoordinate {
	/**
	 * The x coordinate
	 */
	public final double x;
	/**
	 * The y coordinate
	 */
	public final double y;

	/**
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public MCoordinate(final double x, final double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "[" + this.x + ", " + this.y + "]";
	}

}
