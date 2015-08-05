package eu.zidek.augustin.minuscule;

import java.awt.Color;

/**
 * Stores properties of the grid, as grid is not stored as normal object (i.e.
 * as a set of lines), but as separate object, to enable dynamic treatment
 * depending on the canvas size.
 * 
 * @author Augustin Zidek
 * 
 */
class GridProperties {
	private final float thickness;
	private final double colSize;
	private final double rowSize;
	private final Color color;

	/**
	 * @param thickness The thickness of the grid lines
	 * @param colSize The horizontal distance between grid lines
	 * @param rowSize The vertical distance between grid lines
	 * @param color The color of the grid
	 */
	GridProperties(float thickness, double colSize, double rowSize, Color color) {
		this.thickness = thickness;
		this.colSize = colSize;
		this.rowSize = rowSize;
		this.color = color;
	}

	/**
	 * @return the thickness
	 */
	float getThickness() {
		return this.thickness;
	}

	/**
	 * @return the colSize
	 */
	double getColSize() {
		return this.colSize;
	}

	/**
	 * @return the rowSize
	 */
	double getRowSize() {
		return this.rowSize;
	}

	/**
	 * @return the color
	 */
	Color getColor() {
		return this.color;
	}
}
