package eu.zidek.augustin.minuscule;

import java.awt.Color;
import java.awt.Font;

/**
 * Holds all constants for the Minuscule Graphics Library project.
 * 
 * @author Augustin Zidek
 * 
 */
class Constants {
	// Fonts
	static final String DEFAULT_FONT_FAMILY = "Arial";
	static final int DEFAULT_FONT_SIZE = 10;
	static final Font DEFAULT_FONT = new Font(DEFAULT_FONT_FAMILY, Font.PLAIN,
			DEFAULT_FONT_SIZE);
	static final Font INFO_FONT = new Font(DEFAULT_FONT_FAMILY, Font.PLAIN, 12);
	static final boolean DEFAULT_FONT_FILL = true;

	// Strokes
	static final MStroke DEFAULT_STROKE = new MBasicStroke(3);
	static final MStroke DEFAULT_LABEL_STROKE = new MBasicStroke();

	// Point
	static final double DEFAULT_POINT_RADIUS = 4;
	static final Color DEFAULT_POINT_COLOR = Color.BLACK;
	static final boolean DEFAULT_POINT_FILL = true;
	static final MStroke DEFAULT_POINT_STROKE = new MBasicStroke(1);
	static final double DEFAULT_POINT_LABEL_POSITION = 45;
	static final int DEFAULT_POINT_LAYER = 1;
	static final double DEFAULT_POINT_X_COORDINATE = 0;
	static final double DEFAULT_POINT_Y_COORDINATE = 0;
	static final boolean DEFAULT_POINT_ZOOM_INDIFFERENCE = false;

	// Line
	static final float DEFAULT_LINE_THICKNESS = 1.0F;
	static final Color DEFAULT_LINE_COLOR = Color.BLACK;
	static final boolean DEFAULT_LINE_FILL = false;
	static final MStroke DEFAULT_LINE_STROKE = new MBasicStroke(
			DEFAULT_LINE_THICKNESS);
	static final int DEFAULT_LINE_LAYER = 0;
	static final boolean DEFAULT_LINE_ZOOM_INDIFFERENCE = false;

	// Polygon
	static final int DEFAULT_POLYGON_FILL_ALPHA = 100;
	static final Color DEFAULT_POLYGON_COLOR = Color.BLACK;
	static final Color DEFAULT_POLYGON_FILL_COLOR = new Color(
			0x000000 + (Constants.DEFAULT_POLYGON_FILL_ALPHA << 24), true);;
	static final boolean DEFAULT_POLYGON_FILL = false;
	static final MStroke DEFAULT_POLYGON_STROKE = new MBasicStroke(1);
	static final int DEFAULT_POLYGON_LAYER = 0;
	static final boolean DEFAULT_POLYGON_ZOOM_INDIFFERENCE = false;

	// Label
	static final Color DEFAULT_LABEL_COLOR = Color.BLACK;
	static final boolean DEFAULT_LABEL_IS_UNMOVABLE = false;
	static final boolean DEFAULT_LABEL_FILL = true;
	static final double DEFAULT_LABEL2LABEL_SPACING = 1;
	static final int DEFAULT_LABEL_LAYER = 2;
	static final boolean DEFAULT_LABEL_ZOOM_INDIFFERENCE = false;

	// Rectangle
	static final Color DEFAULT_RECTANGLE_COLOR = Color.BLACK;
	static final boolean DEFAULT_RECTANGLE_FILL = false;
	static final MStroke DEFAULT_RECTANGLE_STROKE = new MBasicStroke(2);
	static final int DEFAULT_RECTANGLE_LAYER = 0;
	static final int DEFAULT_RECTANGLE_LABEL_POSITION = 0;
	static final double DEFAULT_RECTANGLE_X_COORDINATE = 0;
	static final double DEFAULT_RECTANGLE_Y_COORDINATE = 0;
	static final double DEFAULT_RECTANGLE_WIDTH = 0;
	static final double DEFAULT_RECTANGLE_HEIGHT = 0;
	static final boolean DEFAULT_RECTANGLE_ZOOM_INDIFFERENCE = false;

	// Shape
	static final Color DEFAULT_SHAPE_COLOR = Color.BLACK;
	static final boolean DEFAULT_SHAPE_FILL = false;
	static final MStroke DEFAULT_SHAPE_STROKE = new MBasicStroke(1);
	static final int DEFAULT_SHAPE_LAYER = 0;
	static final boolean DEFAULT_SHAPE_ZOOM_INDIFFERENCE = false;

	// Canvas
	static final String ERROR_MESSAGE_NO_PAINTER = "ERROR: No painter has been found for the geometric object ";
	static final long DEFAULT_REPAINT_INTERVAL_MS = 100;
	static final double DEFAULT_CANVAS_ZOOM = 1;
	static final String DEFAULT_SCREENSHOT_FILE_FORMAT = "png";
	static final double DEFAULT_ZOOM_IN_FACTOR = 1.1;
	static final double DEFAULT_ZOOM_OUT_FACTOR = 0.9;

	// Window
	static final String DEFAULT_WINDOW_TITLE = "Minuscule Canvas";
	static final float DEFAULT_GRID_THICKNESS = 0.3F;
	static final int ZOOM_1_VALUE = 25;
	static final String AUTHOR = "\u00a9 Augustin Zidek, Minuscule 2.0";
	static final String INFO_PATH = "http://www.augustin.zidek.eu/minuscule/info-20.html";
	static final Object INFO_NO_WEB = "Minuscule Java Graphics Library v 2.0. More details at www.augustin.zidek.eu/minuscule";
	static final String ERROR_MESSAGE_SAVE_IMAGE = "There was an error saving the image to the disk. Do you have sufficent access right?";
	static final String TLT_ZOOM_LABEL = "Click the - and + to adjust the zoom.";
	static final String TLT_ZOOM_VALUE = "Click to reset to 100%.";
	static final String BTN_ZOOM_IN = "<html><font face='Courier New' size='4'>\u2212</font></html>";
	static final String BTN_ZOOM_OUT = "<html><font face='Courier New' size='4'>\u002B</font></html>";

	// PopUp Menu
	static final String POPUP_MENU_RESET_ZOOM = "Reset zoom";
	static final String POPUP_MENU_RESET_TRANSLANSLATION = "Reset view";
	static final String POPUP_MENU_RESET_BOTH = "Reset zoom and view";

	// Math
	static final double DEG2RAD = 2 * Math.PI / 360D;
	static final double RAD2DEG = 360D / (2 * Math.PI);
	static final double SQRT2 = Math.sqrt(2D);

}
