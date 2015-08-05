package eu.zidek.augustin.minuscule.demo;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.CubicCurve2D;

import eu.zidek.augustin.minuscule.*;

/**
 * Class that runs the demos displayed on the web.
 * 
 * @author Augustin Zidek
 * 
 */
public class Minuscule1WebDemos {

	private void demo1Points(final Canvas canvas) {
		// Draw default point with coordinates (10, 10)
		canvas.drawPoint(10, 10);

		// Draw point with a label, placed next to the point
		canvas.drawPoint(30, 40, "Point with a label");

		// Draw blue point with diameter = 14
		canvas.drawPoint(50, 100, 14, Color.BLUE);

		// Method for drawing points which gives you the most control
		canvas.drawPoint(100, 150, "P3", 10, Color.RED, Color.GREEN,
				LabelPosition.NORTH);
	}

	private void demo2Lines(final Canvas canvas) {
		// Draw line from (50,10) to (350,150)
		canvas.drawLine(50, 10, 350, 150);

		// Draw line with given thickness and color
		canvas.drawLine(350, 40, 40, 40, 15, Color.YELLOW);

		// Draw semi-transparent green line
		canvas.drawLine(200, 10, 200, 200, 10, new Color(0, 255, 0, 100));
	}

	private void demo3Shapes(final Canvas canvas) {
		// Draw a custom shape - rectangle and fill it
		final Shape rectangle1 = new Rectangle(10, 10, 300, 150);
		canvas.drawShape(rectangle1, Color.BLUE, true);

		// Draw an unfilled rectangle
		final Shape rectangle2 = new Rectangle(100, 100, 200, 150);
		canvas.drawShape(rectangle2, Color.GREEN, false);

		// Draw a Bezier curve
		Shape bezier = new CubicCurve2D.Double(50, 50, 400, -10, 300, 100, 400,
				250);
		canvas.drawShape(bezier, new Color(255, 0, 0, 100), true);
	}

	private void demo4Grid(final Canvas canvas) {
		// Draw grid with the given column and row size
		canvas.drawGrid(20, 20);

		// One can also use Euclidean coordinates
		canvas.setEuclideanCoordinates(true);

		// Draw the origin with a label
		canvas.drawPoint(0, 0, "The origin");
	}

	private void demo5Varia(final Canvas canvas) {
		// Switch to Euclidean coordinate system
		canvas.setEuclideanCoordinates(true);
		// Constant to enable easy deg-->rad
		final double deg2rad = 2 * Math.PI / 360;

		canvas.drawGrid(40, 40);

		// Draw an ellipse with r1 = diameter, r2 = 2*diameter
		final int diameter = 100;
		for (int i = 0; i < 360; i += 20) {
			// Calculate the coordinates
			final double x = Math.cos(i * deg2rad) * 2 * diameter;
			final double y = Math.sin(i * deg2rad) * diameter;

			// Calculate the color
			final int red = i * 255 / 360;
			final int green = 4 * i % 255;
			final int blue = 2 * i % 255;
			final Color pointColor = new Color(red, green, blue);

			final String label = "(" + Math.round(x) + "," + Math.round(y)
					+ ")";
			canvas.drawPoint(x, y, label, 16, pointColor, Color.BLACK,
					LabelPosition.NORTHEAST);
		}

		canvas.drawPoint(0, 0, "ORIGIN");
	}

	/**
	 * Demonstrating purposes only
	 * 
	 * @param args Ignored
	 */
	public static void main(String[] args) {
		final Minuscule1WebDemos demos = new Minuscule1WebDemos();

		final MinusculeWindow window = new MinusculeWindow(300, 300);
		final Canvas canvas = window.getCanvas();

		// Comment and un-comment these to show different demos
		demos.demo1Points(canvas);
		canvas.clearAll();
		demos.demo2Lines(canvas);
		canvas.clearAll();
		demos.demo3Shapes(canvas);
		canvas.clearAll();
		demos.demo4Grid(canvas);
		canvas.clearAll();
		demos.demo5Varia(canvas);
	}

}
