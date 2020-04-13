package eu.zidek.augustin.minuscule.demos;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.CubicCurve2D;

import eu.zidek.augustin.minuscule.Canvas;
import eu.zidek.augustin.minuscule.MLine;
import eu.zidek.augustin.minuscule.MPoint;
import eu.zidek.augustin.minuscule.MRectangle;
import eu.zidek.augustin.minuscule.MShape;
import eu.zidek.augustin.minuscule.MinusculeWindow;

/**
 * Class that runs the demos displayed on the web.
 * 
 * @author Augustin Zidek
 * 
 */
public class Minuscule2WebDemos {

	private void demo1Points(final Canvas canvas) {
		// Draw default point with coordinates (10, 10)
		new MPoint().x(10).y(10).draw(canvas);

		// Draw point with a label, placed next to the point
		new MPoint().x(30).y(40).label("Point with a label").draw(canvas);

		// Draw blue point with diameter = 14
		new MPoint().x(50).y(100).diameter(14).color(Color.BLUE).draw(canvas);

		// Draw red point with diameter = 10 and label at 225 degrees
		new MPoint().x(100).y(150).diameter(10).color(Color.RED)
				.label("P3", 225).draw(canvas);
	}

	private void demo2Lines(final Canvas canvas) {
		// Draw line from (50,10) to (350,150)
		new MLine().start(50, 10).end(350, 150).draw(canvas);

		// Draw line with given thickness and color
		new MLine().start(350, 40).end(40, 40).thickness(15)
				.color(Color.YELLOW).draw(canvas);

		// Draw semi-transparent green line
		new MLine().start(200, 10).end(200, 200).thickness(10)
				.color(new Color(0, 255, 0, 100)).draw(canvas);
	}

	private void demo3Shapes(final Canvas canvas) {
		// Draw a rectangle and fill it
		new MRectangle().pos(10, 10).dimensions(300, 150).color(Color.BLUE)
				.fill(true).draw(canvas);

		// Draw an unfilled rectangle
		new MRectangle().pos(100, 100).dimensions(200, 150).color(Color.GREEN)
				.fill(false).draw(canvas);

		// Draw a custom shape - a Bézier curve
		Shape bezier = new CubicCurve2D.Double(150, 200, 400, -100, 300, 200,
				400, 200);
		new MShape(bezier).color(new Color(255, 0, 0, 100)).fill(true)
				.draw(canvas);
	}

	private void demo4Grid(final Canvas canvas) {
		// Draw grid with the given column and row size
		canvas.drawGrid(20, 20);

		// Use Euclidean coordinates
		canvas.setEuclideanCoordinates(true);

		// Draw the origin with a label
		new MPoint().pos(0, 0).label("The origin").draw(canvas);
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

			// Calculate color of the point
			final Color pointColor = Color.getHSBColor(i / 360F, 1F, 1F);

			final String coordLabel = String.format("(%.0f, %.0f)", x, y);
			new MPoint().pos(x, y).label(coordLabel, 45).diameter(16)
					.color(pointColor).draw(canvas);
		}
		new MPoint().pos(0, 0).label("ORIGIN").draw(canvas);
	}

	private void demo6Animation(final Canvas canvas)
			throws InterruptedException {
		// Switch to Euclidean coordinate system
		canvas.setEuclideanCoordinates(true);
		// Constant to enable easy deg-->rad
		final double deg2rad = 2 * Math.PI / 360;

		canvas.drawGrid(40, 40);

		// Draw an ellipse with r1 = diameter, r2 = 2*diameter
		final int diameter = 100;

		new MPoint().pos(0, 0).label("ORIGIN").draw(canvas);

		// Animate the point
		final MPoint p = new MPoint();
		int angle = 0;
		while (true) {
			angle = (angle + 5) % 360;
			// Calculate the coordinates
			final double x = Math.cos(angle * deg2rad) * 2 * diameter;
			final double y = Math.sin(angle * deg2rad) * diameter;

			// Calculate color of the point
			final Color pointColor = Color.getHSBColor(angle / 360F, 1F, 1F);

			final String coordLabel = String.format("(%.0f, %.0f)", x, y);
			p.pos(x, y).label(coordLabel, 45).diameter(16).color(pointColor)
					.draw(canvas);
			// Display for 50 ms and then continue
			Thread.sleep(50);
		}
	}

	/**
	 * Demonstrating purposes only
	 * 
	 * @param args Ignored
	 * @throws InterruptedException If sleeping crashes
	 */
	public static void main(String[] args) throws InterruptedException {
		final Minuscule2WebDemos demos = new Minuscule2WebDemos();

		final MinusculeWindow window = new MinusculeWindow(500, 250);
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
		canvas.clearAll();
		demos.demo6Animation(canvas);
	}

}
