package eu.zidek.augustin.minuscule;

import java.awt.Font;

/**
 * Holds all constants for the Minuscule Graphics Library project.
 * 
 * @author Augustin Zidek
 * 
 */
class Constants {
	static final String DEFAULT_FONT_FAMILY = "Arial";
	static final int DEFAULT_FONT_SIZE = 10;
	static final Font DEFAULT_FONT = new Font(DEFAULT_FONT_FAMILY, Font.PLAIN,
			DEFAULT_FONT_SIZE);
	static final Font INFO_FONT = new Font(DEFAULT_FONT_FAMILY, Font.PLAIN, 12);
	static final String DEFAULT_WINDOW_TITLE = "Minuscule Canvas";
	static final float DEFAULT_GRID_THICKNESS = 0.3F;
	static final int ZOOM_1_VALUE = 25;
	static final String AUTHOR = "� Augustin Zidek, Minuscule 1.0";
	static final String INFO_PATH = "http://www.augustin.zidek.eu/minuscule/info.html";
	static final Object INFO_NO_WEB = "Minuscule Java Graphics Library v 1.0. More details at www.augustin.zidek.eu/minuscule";

}
