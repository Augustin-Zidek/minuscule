package eu.zidek.augustin.minuscule;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * A canvas which supports easy drawing of elementary geometric objects. As it
 * extends JPanel, it should be used within a frame, for instance in the
 * <code>CanvasWindow</code>.
 * 
 * @author Augustin Zidek
 */
public class Canvas extends JPanel {
	private static final long serialVersionUID = 1L;
	// Stores the properties of the grid. If null, no grid.
	private GridProperties gridProperties;
	// List which stores all the geometric objects
	private List<Object2DContainer> objects = new ArrayList<>();
	// List which stores all the textual labels
	private List<LabelContainer> labels = new ArrayList<>();
	private final int width;
	private final int height;
	// If true, the origin is not in the left top corner, but in the middle and
	// y axis values increase down to top
	private boolean isEucledian = false;
	// The zoom factor by which the canvas shall be zoomed
	private double zoom = 1;
	// The translation vector that is currently in use
	private double translatex = 0;
	private double translatey = 0;

	/**
	 * Creates a new Canvas with the given width and weight.
	 * 
	 * @param width The width of the canvas
	 * @param height The height of the canvas
	 */
	Canvas(final int width, final int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Determines the position of the label.
	 * 
	 * @param x The x coordinate of the point
	 * @param y The y coordinate of the point
	 * @param diameter The diameter of the point
	 * @param position The position of the label
	 * @return The 2d coordinate of the left bottom corner of the label
	 *         according to the given position.
	 */
	private Point2D.Double getLabelPosition(final double x, final double y,
			final int diameter, final LabelPosition position) {
		// The distance from the center of the point
		final int radialDistance = diameter / 2 + 1;
		// The offset which has to be added to the point center coordinates to
		// get coordinates for the label which is in NE or SE direction
		final double diagonalOffset = radialDistance / Math.sqrt(2);

		// Calculate the label coordinates according to the position
		double labelx = x;
		double labely = y;
		if (position == LabelPosition.NORTH) {
			labely -= radialDistance;
		}
		else if (position == LabelPosition.NORTHEAST) {
			labelx += diagonalOffset;
			labely -= diagonalOffset;
		}
		else if (position == LabelPosition.EAST) {
			labelx += radialDistance;
			labely += Constants.DEFAULT_FONT_SIZE / 2;
		}
		else if (position == LabelPosition.SOUTHEAST) {
			labelx += diagonalOffset;
			labely += diagonalOffset + Constants.DEFAULT_FONT_SIZE;
		}
		else if (position == LabelPosition.SOUTH) {
			labely += radialDistance + Constants.DEFAULT_FONT_SIZE;
		}
		return new Point2D.Double(labelx, labely);
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
		if (this.isEucledian) {
			// Translate & zoom, add the grid offset
			xmin = -this.translatex / this.zoom + (this.width / 2 + this.translatex / this.zoom)
					% colSize;
			ymin = -this.translatey / this.zoom + (this.height / 2 + this.translatey / this.zoom)
					% rowSize;
		}
		else {
			// Translate and zoom: (-x/z). Align axes with origin ((x/z) % c)
			xmin = -this.translatex / this.zoom + (this.translatex / this.zoom) % colSize;
			ymin = -this.translatey / this.zoom + (this.translatey / this.zoom) % rowSize;
		}
		// Translate and scale, since we only care about boundary value
		final double xmax = (dynamicWidth - this.translatex) / this.zoom;
		final double ymax = (dynamicHeight - this.translatey) / this.zoom;
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
	 * Turns on the antialiasing for shapes and texts on the given graphics 2d
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
		for (final Object2DContainer object : this.objects) {
			g2d.setColor(object.getColor());
			g2d.setStroke(object.getStroke());
			// Fill or draw according to the object's property
			if (object.isFill()) {
				g2d.fill(object.getShape());
			}
			else {
				g2d.draw(object.getShape());
			}
		}
	}

	/**
	 * Draws all the labels which are in the labels list onto the given 2d
	 * graphics object.
	 * 
	 * @param g2d The 2D graphics object
	 */
	private void displayLabels(final Graphics2D g2d) {
		for (final LabelContainer label : this.labels) {
			g2d.setColor(label.getColor());
			final Font font = label.getFont();
			// If no font set, don't use the previous one, use the default
			if (font == null) {
				g2d.setFont(new Font(Constants.DEFAULT_FONT_FAMILY, Font.PLAIN,
						Constants.DEFAULT_FONT_SIZE));
			}
			else {
				g2d.setFont(font);
			}
			final float labelY = (float) label.getY();
			// If Eucledian, scale(1,-1) the label coordinates manually
			final float y = this.isEucledian ? -labelY
					- (float) label.getEucledianYOffset() : labelY;
			g2d.drawString(label.getText(), (float) label.getX(), y);
		}
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2d = (Graphics2D) g;

		// Turn on the antialiasing
		this.turnOnAntialiasing(g2d);

		// Perform moving
		g2d.translate(this.translatex, this.translatey);

		// Perform zooming (if any)
		g2d.scale(this.zoom, this.zoom);

		// Draw the grid before anything else (so that it is in the background)
		// and before the Euclidean transform (if any) is applied (easier
		// coordinate calculations). The grid is drawn separately directly onto
		// the g2d object, as it needs to be dynamically redrawn if the window
		// size is changed.
		if (this.gridProperties != null) {
			this.drawGrid(g2d, this.getWidth(), this.getHeight());
		}

		// Apply the transform to the Eucledian coordinates
		if (this.isEucledian) {
			g2d.translate(this.width / 2, this.height / 2);
			g2d.scale(1, -1);
		}

		// Go through the list of objects and (re)draw them. Synchronized, as
		// some other thread might be adding new objects into the list
		synchronized (this.objects) {
			this.displayObjects(g2d);
		}

		// The scale(1,-1) to the Eucledian coordinates scales also the labels.
		// So revert it and do the scaling by hand using coordinate arithmetic.
		if (this.isEucledian) {
			g2d.scale(1, -1);
		}

		// Go through the list of labels and (re)draw them. Synchronized, as
		// some other thread might be adding new labels into the list
		synchronized (this.labels) {
			this.displayLabels(g2d);
		}
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
	 * Clears the canvas, but leaves the gird in place: deletes permanently all
	 * objects, labels and repaints the canvas.
	 */
	public void clearAllButGrid() {
		// Must be synchronized, paintComponent might be accessing objects
		synchronized (this.objects) {
			this.objects.clear();
		}
		// Must be synchronized, paintComponent might be accessing labels
		synchronized (this.labels) {
			this.labels.clear();
		}
		this.repaint();
	}

	/**
	 * Draws a default point with the given coordinates. The point is a black
	 * color filled circle with diameter 8. In case more customizable points are
	 * needed to be drawn, use the other <code>drawPoint</code> methods (see
	 * below).
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public void drawPoint(final double x, final double y) {
		this.drawPoint(x, y, 8, Color.BLACK);
	}

	/**
	 * Draws a filled point with the given coordinates, diameter and color.
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param diameter The diameter of the point
	 * @param color The color of the point
	 */
	public void drawPoint(final double x, final double y, final int diameter,
			final Color color) {
		final Line2D line = new Line2D.Double(x, y, x, y);
		final Stroke stroke = new BasicStroke(diameter, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND);
		final Object2DContainer object = new Object2DContainer(line, color,
				stroke, false);
		// Must be synchronized, paintComponent might be accessing objects
		synchronized (this.objects) {
			this.objects.add(object);
		}
		this.repaint();
	}

	/**
	 * Draws a default point with the given coordinates and a textual label. The
	 * point color is black, diameter is 8 pixels, the label color is black and
	 * the position of the label is NE.
	 * 
	 * @param x The x coordinate of the point
	 * @param y The y coordinate of the point
	 * @param labelText The text of the label
	 */
	public void drawPoint(final double x, final double y, final String labelText) {
		this.drawPoint(x, y, labelText, 8, Color.BLACK, Color.BLACK,
				LabelPosition.NORTHEAST);
	}

	/**
	 * Draws a filled point and a label next to it. The position of the label is
	 * automatically calculated using the point's coordinates, diameter and the
	 * given position parameter. The label will be drawn in Arial, 10pt.
	 * 
	 * @param x The x coordinate of the point
	 * @param y The y coordinate of the point
	 * @param labelText The text of the label
	 * @param diameter The diameter of the point
	 * @param pointColor The color of the point
	 * @param labelColor The color of the label
	 * @param labelPosition The position, use one of the values defined in the
	 *            enumeration {@link LabelPosition}.
	 */
	public void drawPoint(final double x, final double y,
			final String labelText, final int diameter, final Color pointColor,
			final Color labelColor, final LabelPosition labelPosition) {
		// Draw the point
		this.drawPoint(x, y, diameter, pointColor);
		// Determine distance of the label so it doesn't overlap with the point
		final Point2D.Double labelPos = this.getLabelPosition(x, y, diameter,
				labelPosition);
		// Calculate the Eucledian label offset in y direction. Makes sure after
		// the transform into the Eucledian coordinates the label is transformed
		// so that it respects the relative position to the point.
		final double labelEuclYOffset = 2 * (y - labelPos.y);
		this.drawPointLabel(labelPos.x, labelPos.y, labelText,
				Constants.DEFAULT_FONT, labelColor, labelEuclYOffset);
	}

	/**
	 * Draws a default line with the given starting and ending coordinates. The
	 * line is black and 2 pixels thick.
	 * 
	 * @param startx Start x coordinate
	 * @param starty Start y coordinate
	 * @param endx End x coordinate
	 * @param endy End y coordinate
	 */
	public void drawLine(final double startx, final double starty,
			final double endx, final double endy) {
		this.drawLine(startx, starty, endx, endy, 2, Color.BLACK);
	}

	/**
	 * Draws a straight line with a given thickness and color.
	 * 
	 * @param startx Start x coordinate
	 * @param starty Start y coordinate
	 * @param endx End x coordinate
	 * @param endy End y coordinate
	 * @param thickness Thickness in pixels
	 * @param color The color of the line
	 */
	public void drawLine(final double startx, final double starty,
			final double endx, final double endy, final float thickness,
			final Color color) {
		final Line2D line = new Line2D.Double(startx, starty, endx, endy);
		final Stroke stroke = new BasicStroke(thickness);
		final Object2DContainer object = new Object2DContainer(line, color,
				stroke, false);
		// Must be synchronized, paintComponent might be accessing objects
		synchronized (this.objects) {
			this.objects.add(object);
		}
		this.repaint();
	}

	/**
	 * Draws arbitrary shape, i.e. a class implementing the <code>Shape</code>
	 * interface.
	 * 
	 * @param shape The shape to be drawn
	 * @param color The color of the shape
	 * @param fill <code>true</code> if the shape should be filled,
	 *            <code>false</code> otherwise
	 */
	public void drawShape(final Shape shape, final Color color,
			final boolean fill) {
		final Stroke stroke = new BasicStroke(3);
		final Object2DContainer object = new Object2DContainer(shape, color,
				stroke, fill);
		// Must be synchronized, paintComponent might be accessing objects
		synchronized (this.objects) {
			this.objects.add(object);
		}
		this.repaint();
	}

	/**
	 * Draws a text label with black color and default environment font.
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param text The text of the label
	 */
	public void drawLabel(final double x, final double y, final String text) {
		this.drawPointLabel(x, y, text, Constants.DEFAULT_FONT, Color.BLACK, 0);
	}

	/**
	 * Draws a text label with the given properties.
	 * 
	 * @param x The x coordinate of the left bottom corner
	 * @param y The y coordinate of the left bottom corner
	 * @param text The text of the label
	 * @param font The font
	 * @param color The color of the label
	 */
	public void drawLabel(final double x, final double y, final String text,
			final Font font, final Color color) {
		this.drawPointLabel(x, y, text, font, color, 0);
	}

	/**
	 * Draws a text label with the given properties.
	 * 
	 * @param x The x coordinate of the left bottom corner
	 * @param y The y coordinate of the left bottom corner
	 * @param text The text of the label
	 * @param font The font
	 * @param color The color of the label
	 * @param eucledianYOffset The offset by which the label should be moved
	 *            when displayed in Eucledian coordinate system. This is zero
	 *            for labels which are on their own, and half of the y offset if
	 *            the label belongs to a point (e.g. for a point with radius 5,
	 *            the offset would be 5 / sqrt(2)).
	 */
	private void drawPointLabel(final double x, final double y,
			final String text, final Font font, final Color color,
			final double eucledianYOffset) {
		// Must be synchronized, paintComponent might be accessing labels
		synchronized (this.labels) {
			this.labels.add(new LabelContainer(text, x, y, color, font, true,
					eucledianYOffset));
		}
		this.repaint();
	}

	/**
	 * Draws a default grid. The line thickness is set to 0.1, the color is set
	 * to gray. If called multiple times, the original grid is replaced with the
	 * newer one.
	 * 
	 * @param columnSize The horizontal distance between grid lines
	 * @param rowSize The vertical distance between grid lines
	 */
	public void drawGrid(final double columnSize, final double rowSize) {
		this.drawGrid(Constants.DEFAULT_GRID_THICKNESS, columnSize, rowSize,
				Color.LIGHT_GRAY);
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
		this.gridProperties = new GridProperties(thickness, columnSize, rowSize,
				color);
		this.repaint();
	}

	/**
	 * By default, the canvas uses the standard computer graphics coordinate
	 * system, i.e. the origin (0,0) is in the left top corner and y increases
	 * downwards.
	 * 
	 * This method can change it so that Eucledian-like coordinates are used,
	 * i.e. the origin (0,0) is in the middle of the canvas and y increases
	 * upwards.
	 * 
	 * @param value If <code>true</code>, Eucledian-like coordinate system is
	 *            used. If <code>false</code>, the standard computer graphics
	 *            coordinate system is used.
	 */
	public void setEuclideanCoordinates(final boolean value) {
		this.isEucledian = value;
	}

	/**
	 * Zooms in/out the canvas. The zoom factor determines by how much the
	 * canvas shall be zoomed. The zoom has no effect on the grid.
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
	 * Translates the view by the given translation vector. The grid in the
	 * background (if any), remains aligned correctly with the origin.
	 * 
	 * @param dx The translation in x direction
	 * @param dy The translation in y direction
	 */
	public void translate(final double dx, final double dy) {
		this.translatex += dx;
		this.translatey += dy;
		this.repaint();
	}

	/**
	 * Resets the translation vector to (0,0), sets the zoom factor to 1 and
	 * repaints.
	 */
	public void resetView() {
		this.translatex = 0;
		this.translatey = 0;
		this.zoom = 1;
		this.repaint();
	}

	/**
	 * Resets the translation vector to (0,0) and repaints.
	 */
	public void resetTranslation() {
		this.translatex = 0;
		this.translatey = 0;
		this.repaint();
	}

	/**
	 * Resets the zoom factor to 1 and repaints.
	 */
	public void resetZoom() {
		this.zoom = 1;
		this.repaint();
	}
}
