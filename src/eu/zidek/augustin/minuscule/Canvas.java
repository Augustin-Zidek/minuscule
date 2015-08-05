package eu.zidek.augustin.minuscule;

import static eu.zidek.augustin.minuscule.Constants.DEFAULT_GRID_THICKNESS;
import static eu.zidek.augustin.minuscule.Constants.DEFAULT_REPAINT_INTERVAL_MS;
import static eu.zidek.augustin.minuscule.Constants.ERROR_MESSAGE_NO_PAINTER;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * A canvas which supports easy drawing of elementary geometric objects. As it
 * extends JPanel, it should be used within a frame, for instance in the
 * <code>CanvasWindow</code>.
 * 
 * @author Augustin Zidek
 */
public class Canvas extends JPanel {
	private static final long serialVersionUID = 42L;
	// Stores the properties of the grid. If null, no grid.
	private GridProperties gridProperties;
	// A data structure that holds the objects and hides things like buffering,
	// synchronization and sorting only if necessary
	private final GeometricObjectQueue objects = new GeometricObjectQueue();
	// Must be stored to make the grid work (this.getHeight() doesn't work)
	private final int width;
	private final int height;
	// If true, the origin is not in the top left corner but in the middle and
	// y axis values increase upwards
	private boolean eucledian = false;
	// The zoom factor
	private double zoom = Constants.DEFAULT_CANVAS_ZOOM;
	// The translation vector that is currently in use
	private double translateX = 0;
	private double translateY = 0;
	private final PainterManager painterMgr;

	/**
	 * Creates a new Canvas with the given width and weight.
	 * 
	 * @param width The width of the canvas
	 * @param height The height of the canvas
	 */
	public Canvas(final int width, final int height) {
		this.width = width;
		this.height = height;
		this.painterMgr = new PainterManager(this);
	}

	/**
	 * @return <code>true</code> if the canvas is Euclidean, i.e. it has origin
	 *         in the middle and y axis increases in the upper direction.
	 *         Returns <code>false</code> otherwise, i.e. when origin is in the
	 *         left top corner and y axis increases in the lower direction.
	 */
	public boolean isEuclidean() {
		return this.eucledian;
	}

	/**
	 * Draws the grid directly to the Graphics object, so that if window size
	 * changed, the grid changes appropriately.
	 * 
	 * @param g2d The 2D graphics object
	 * @param dynamicWidth The current width of the canvas
	 * @param dynamicHeight The current height of the canvas
	 */
	private void drawGrid(final Graphics2D g2d, final double dynamicWidth,
			final double dynamicHeight) {
		final double colSize = this.gridProperties.getColSize();
		final double rowSize = this.gridProperties.getRowSize();
		// The grid always goes through the origin (0,0), hence translation
		// and zoom must be taken into account
		final double xmin;
		final double ymin;
		if (this.eucledian) {
			// Translate & zoom, add the grid offset
			xmin = -this.translateX / this.zoom
					+ (this.width / 2 + this.translateX / this.zoom) % colSize;
			ymin = -this.translateY / this.zoom
					+ (this.height / 2 + this.translateY / this.zoom) % rowSize;
		}
		else {
			// Translate and zoom: (-x/z). Align axes with origin ((x/z) % c)
			xmin = -this.translateX / this.zoom + (this.translateX / this.zoom)
					% colSize;
			ymin = -this.translateY / this.zoom + (this.translateY / this.zoom)
					% rowSize;
		}
		// Translate and scale, since we only care about boundary value
		final double xmax = (dynamicWidth - this.translateX) / this.zoom;
		final double ymax = (dynamicHeight - this.translateY) / this.zoom;
		g2d.setColor(this.gridProperties.getColor());
		g2d.setStroke(new BasicStroke(this.gridProperties.getThickness()));

		// Draw vertical lines
		for (double x = xmin; x < xmax; x += colSize) {
			// ymin-rowSize to make sure the lines start outside the canvas
			g2d.draw(new Line2D.Double(x, ymin - rowSize, x, ymax));
		}
		// Draw horizontal lines
		for (double y = ymin; y < ymax; y += rowSize) {
			// xmin-colSize to make sure the lines start outside the canvas
			g2d.draw(new Line2D.Double(xmin - colSize, y, xmax, y));
		}
	}

	/**
	 * Turns on the anti-aliasing for shapes and texts on the given graphics 2d
	 * object.
	 * 
	 * @param g2d The 2D graphics object
	 */
	private void turnOnAntialiasing(final Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}

	/**
	 * Draws all the objects which are in the objects list onto the given 2d
	 * graphics object.
	 * 
	 * @param g2d The 2D graphics object
	 */
	private void displayObjects(final Graphics2D g2d) {
		// Go through all objects
		for (final MGeometricObject object : this.objects.getSortedList()) {
			// Retrieve the painter for the given object
			final MGeometricObjectPainter painter;
			try {
				painter = this.painterMgr.getPainter(object.getClass());
			}
			// No painter for this type of object, display error notice
			catch (final NoPainterException e) {
				JOptionPane.showMessageDialog(this, ERROR_MESSAGE_NO_PAINTER
						+ object.getClass().getName(), "No Painter found",
						JOptionPane.ERROR_MESSAGE);
				continue;
			}
			// Paint the object using the painter
			painter.paint(object, g2d);
		}
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2d = (Graphics2D) g;

		// Turn on the anti-aliasing
		this.turnOnAntialiasing(g2d);

		// Perform translation
		g2d.translate(this.translateX, this.translateY);

		// Perform zooming (if any)
		g2d.scale(this.zoom, this.zoom);

		// Draw the grid before anything else (so it is in the background) and
		// before the Euclidean transform (if any) is applied (easier coordinate
		// calculations). The grid is drawn directly onto the g2d object, as it
		// needs to be dynamically redrawn if the window size is changed.
		if (this.gridProperties != null) {
			this.drawGrid(g2d, this.getWidth(), this.getHeight());
		}

		// Apply the transform to the Euclidean coordinates
		if (this.eucledian) {
			g2d.translate(this.width / 2, this.height / 2);
			g2d.scale(1, -1);
		}

		// Go through the list of objects and (re)draw them.
		this.displayObjects(g2d);
	}

	/**
	 * Adds new geometric object into the canvas.
	 * 
	 * @param object The object to be added
	 */
	protected void addGeometricObject(final MGeometricObject object) {
		this.objects.add(object);
		// Repaint the modified object after x ms. This way multiple objects
		// repainted closely after each other are repainted only once.
		this.repaint(DEFAULT_REPAINT_INTERVAL_MS);
	}

	/**
	 * Removes the given geometric object from the canvas.
	 * 
	 * @param object The object to be removed
	 */
	protected void removeGeometricObject(final MGeometricObject object) {
		this.objects.remove(object);
		// Repaint the modified object after x ms. This way multiple objects
		// repainted closely after each other are repainted only once.
		this.repaint(DEFAULT_REPAINT_INTERVAL_MS);
	}

	/**
	 * If layer of an object is updated, it has to be moved in the z-ordered
	 * queue which canvas uses.
	 * 
	 * @param object The object which layer has changed
	 */
	protected void updateObjectsLayer(final MGeometricObject object) {
		this.objects.markDirty();
	}

	/**
	 * Clears the canvas: deletes permanently all objects, labels and the grid
	 * and repaints the canvas.
	 */
	public void clearAll() {
		this.clearAllButGrid();
		// Clear the grid
		this.gridProperties = null;
		this.repaint();
	}

	/**
	 * Clears the canvas, but leaves the grid in place: deletes permanently all
	 * objects, labels and repaints the canvas.
	 */
	public void clearAllButGrid() {
		this.objects.clear();
		this.repaint();
	}

	/**
	 * Draws the default grid. The line thickness is set to 0.1, the color is
	 * set to gray. If called multiple times, the original grid is replaced with
	 * the newer one.
	 * 
	 * @param columnSize The horizontal distance between grid lines
	 * @param rowSize The vertical distance between grid lines
	 */
	public void drawGrid(final double columnSize, final double rowSize) {
		drawGrid(DEFAULT_GRID_THICKNESS, columnSize, rowSize, Color.LIGHT_GRAY);
	}

	/**
	 * Draws grid with the given properties. If called multiple times, the
	 * original grid is replaced with the newer one.
	 * 
	 * @param thickness The thickness of the grid lines
	 * @param columnSize The horizontal distance between grid lines
	 * @param rowSize The vertical distance between grid lines
	 * @param color The color of the grid
	 */
	public void drawGrid(final float thickness, final double columnSize,
			final double rowSize, final Color color) {
		this.gridProperties = new GridProperties(thickness, columnSize,
				rowSize, color);
		this.repaint();
	}

	/**
	 * By default, the canvas uses the standard Java graphics coordinate system,
	 * i.e. the origin (0,0) is in the left top corner and y axis coordinates
	 * increase downwards.
	 * 
	 * This method changes it so that Euclidean-like coordinates are used, i.e.
	 * the origin (0,0) is in the middle of the canvas and y increases upwards.
	 * 
	 * @param value If <code>true</code>, Euclidean-like coordinate system is
	 *            used. If <code>false</code>, the standard Java graphics
	 *            coordinate system is used.
	 */
	public void setEuclideanCoordinates(final boolean value) {
		this.eucledian = value;
	}

	/**
	 * Zooms in/out the canvas and repaints the canvas. The zoom factor
	 * determines by how much the canvas shall be zoomed. The zoom has no effect
	 * on the grid.
	 * 
	 * @param zoomFactor Determines the zooming factor. Numbers between 0 and 1
	 *            zoom out, numbers greater than 1 zoom in. Negative numbers
	 *            behave equivalently, but flip the image around the origin
	 *            first.
	 */
	public void setZoom(final double zoomFactor) {
		this.zoom = zoomFactor;
		this.repaint();
	}

	/**
	 * @return Returns the current zoom factor of the canvas.
	 */
	public double getZoom() {
		return this.zoom;
	}

	/**
	 * Translates the view by the given translation vector (i.e. the original
	 * translation vector is increased by the given translation vector) and
	 * repaints the canvas. The grid is translated as well.
	 * 
	 * @param dx The translation change in x direction
	 * @param dy The translation change in y direction
	 */
	public void translate(final double dx, final double dy) {
		this.translateX += dx;
		this.translateY += dy;
		this.repaint();
	}

	/**
	 * Sets the translation of the view to the given translation vector and
	 * repaints the canvas. The grid is translated as well.
	 * 
	 * @param tx The translation in x direction
	 * @param ty The translation in y direction
	 */
	public void setTranslation(final double tx, final double ty) {
		this.translateX = tx;
		this.translateY = ty;
		this.repaint();
	}

	/**
	 * @return The current translation vector.
	 */
	public MCoordinate getTranslation() {
		return new MCoordinate(this.translateX, this.translateY);
	}

	/**
	 * Resets the translation vector to (0,0), sets the zoom factor to 1 and
	 * repaints.
	 */
	public void resetTranslationAndZoom() {
		this.translateX = 0;
		this.translateY = 0;
		this.zoom = 1;
		this.repaint();
	}

	/**
	 * Resets the translation vector to (0,0) and repaints.
	 */
	public void resetTranslation() {
		this.translateX = 0;
		this.translateY = 0;
		this.repaint();
	}

	/**
	 * Resets the zoom factor to 1 and repaints.
	 */
	public void resetZoom() {
		this.zoom = 1;
		this.repaint();
	}

	/**
	 * @return The screenshot of the current canvas
	 */
	public BufferedImage getImage() {
		// Create buffered image from the canvas
		final BufferedImage image = (BufferedImage) this.createImage(
				this.getWidth(), this.getHeight());

		// Get the graphics of the image
		final Graphics2D g2dimg = image.createGraphics();

		// Paint into it using the canvas's method
		this.paintComponent(g2dimg);

		return image;
	}

}
