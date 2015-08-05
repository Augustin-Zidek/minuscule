package eu.zidek.augustin.minuscule;

import java.util.HashMap;
import java.util.Map;

/**
 * Painter Manager makes sure that the same geometric objects share one painter.
 * 
 * @author Augustin Zidek
 *
 */
public class PainterManager {
	// Internal map holding the pairs
	private final Map<Class<?>, MGeometricObjectPainter> shapePainterMap;

	/**
	 * Initializes the <code>PainterManager</code> which makes sure that the
	 * same geometric objects share one painter.
	 * 
	 * PainterManager contains two methods - one for retrieving a painter for a
	 * geometric object using the object's class, the other to register new
	 * (user created) objects with painters.
	 * 
	 * @param canvas The canvas for which this manager handles the
	 *            <code>getPainter</code> queries
	 */
	public PainterManager(final Canvas canvas) {
		this.shapePainterMap = new HashMap<>();

		// Construct the painters (some are linked => anonymous are not used)
		final MPointPainter pointPainter = new MPointPainter();
		final MLinePainter linePainter = new MLinePainter();
		final MLabelPainter labelPainter = new MLabelPainter(canvas);
		final MPolygonPainter polygonPainter = new MPolygonPainter(
				pointPainter, labelPainter);
		final MShapePainter shapePainter = new MShapePainter();
		final MRectanglePainter rectanglePainter = new MRectanglePainter();

		// Pair the (built-in) geometric objects with their respective painters
		this.shapePainterMap.put(MPoint.class, pointPainter);
		this.shapePainterMap.put(MLine.class, linePainter);
		this.shapePainterMap.put(MLabel.class, labelPainter);
		this.shapePainterMap.put(MPolygon.class, polygonPainter);
		this.shapePainterMap.put(MShape.class, shapePainter);
		this.shapePainterMap.put(MRectangle.class, rectanglePainter);
	}

	/**
	 * Returns the painter belonging to the given Class object of the geometric
	 * object.
	 * 
	 * @param geometricObject The Class of the geometric object (e.g.
	 *            <code>MPoint.class</code>)
	 * @return Painter belonging to the given geometric object
	 * @throws NoPainterException If there is no registered Painter for the
	 *             given geometric object
	 */
	public MGeometricObjectPainter getPainter(final Class<?> geometricObject)
			throws NoPainterException {
		// Try to get painter for the object
		final MGeometricObjectPainter painter = this.shapePainterMap
				.get(geometricObject);
		// No painter found, throw an exception
		if (painter == null) {
			throw new NoPainterException();
		}
		return painter;
	}

	/**
	 * Registers the given painter with the given geometric object.
	 * 
	 * @param geometricObject The class of the geometric object
	 * @param painter The painter for these geometric objects
	 */
	public void registerPainter(final Class<MGeometricObject> geometricObject,
			final MGeometricObjectPainter painter) {
		// Add the pair into the map
		this.shapePainterMap.put(geometricObject, painter);
	}

}
