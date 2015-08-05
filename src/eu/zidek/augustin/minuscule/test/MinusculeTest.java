package eu.zidek.augustin.minuscule.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.CubicCurve2D;

import eu.zidek.augustin.minuscule.Canvas;
import eu.zidek.augustin.minuscule.MLabel;
import eu.zidek.augustin.minuscule.MLine;
import eu.zidek.augustin.minuscule.MPoint;
import eu.zidek.augustin.minuscule.MPolygon;
import eu.zidek.augustin.minuscule.MRectangle;
import eu.zidek.augustin.minuscule.MShape;
import eu.zidek.augustin.minuscule.MinusculeWindow;

class MinusculeTest {
	final static double deg2rad = 2 * Math.PI / 360;

	private static void firstTest(final Canvas canvas)
			throws InterruptedException {
		// Draw a grid
		canvas.drawGrid(0.1F, 10, 10, Color.GRAY);

		// Draw a Bézier curve
		final Color greenTrp = new Color(0, 255, 100, 100);
		new MShape(new CubicCurve2D.Double(10, 10, 2000, 500, 300, -500, 500,
				500)).color(greenTrp).fill(true).draw(canvas);

		// Draw a polygon
		final MPoint p1 = new MPoint().x(600).y(400);
		final MPoint p2 = new MPoint().x(700).y(400);
		final MPoint p3 = new MPoint().x(650).y(600);
		final MPoint p4 = new MPoint().x(700).y(500);
		new MPolygon().addVertex(p1).addVertex(p2).addVertex(p3).addVertex(p4)
				.fill(true).label("Polygon").draw(canvas);

		// Draw lines and points
		for (int i = 10; i < 500; i += 12) {
			final int x1 = 20000 / (i + 15);
			final int y1 = 150 + i;
			final int x2 = 7 * i / 3;
			final int y2 = 100;

			new MPoint().x(x2).y(y2).color(Color.BLUE).diameter(10)
					.label(String.valueOf(x2), 90).draw(canvas);

			new MPoint().x(x1).y(y1).color(Color.RED).diameter(10).fill(false)
					.draw(canvas);

			new MLine().start(x1, y1).end(x2, y2).color(Color.YELLOW)
					.draw(canvas);
		}

		// Draw String labels
		new MLabel("A Minuscule demo").pos(0, 50)
				.font(new Font("Arial", Font.BOLD, 20)).color(Color.BLACK)
				.draw(canvas);
		new MLabel("Hello World.").pos(200, 50).draw(canvas);

		// Draw two rectangles
		new MRectangle().pos(800, 300).dimensions(200, 100).color(Color.ORANGE)
				.fill(true).draw(canvas);
		new MRectangle().pos(800, 300).dimensions(200, 100).color(Color.GRAY)
				.fill(false).draw(canvas);

		final MPoint bigPoint = new MPoint().pos(800, 500).color(Color.RED)
				.radius(50).draw(canvas);

		// Draw a huge point with a lot of huge labels
		for (int angleDeg = 0; angleDeg < 360; angleDeg++) {
			final MLabel label = new MLabel("(Label for big point)")
					.fontSize(30).color(Color.BLUE)
					.positionSetByParent(true, angleDeg).draw(canvas);
			bigPoint.label(label);

			Thread.sleep(3);
		}
	}

	private static void staticCircleTest(final Canvas c) {
		final int circleDiameter = 300;

		// Draw grid
		c.drawGrid(20, 20);

		// Set Euclidean coordinates
		c.setEuclideanCoordinates(true);

		for (int i = 0; i < 360; i += 10) {
			final double x = Math.cos(i * deg2rad) * circleDiameter;
			final double y = Math.sin(i * deg2rad) * circleDiameter;

			// Calculate color of the point
			final Color pointColor = Color.getHSBColor(i / 360F, 1F, 1F);

			final String coordLabel = String.format("(%.0f, %.0f)", x, y);
			new MPoint().pos(x, y).label(coordLabel, 45).diameter(16)
					.color(pointColor).draw(c);
		}

		// Testing label and the origin
		new MLabel("A LABEL at (40,40)").pos(40, 40).draw(c);
		new MPoint().pos(0, 0).label("ORIGIN").draw(c);
	}

	private static void circleMovementTest(final Canvas c)
			throws InterruptedException {
		final int radius = 300;

		// Draw grid
		c.drawGrid(20, 20);

		// Draw the origin
		new MPoint().pos(0, 0).label("ORIGIN").draw(c);

		// The moving point
		final MPoint p1 = new MPoint();
		final MPoint p2 = new MPoint();

		int i = 0;
		while (true) {
			final double x1 = Math.cos(i * deg2rad) * radius * 1.5;
			final double y1 = Math.sin(i * deg2rad) * radius;

			final double x2 = Math.cos(2 * i * deg2rad) * radius * 0.8;
			final double y2 = Math.sin(2 * i * deg2rad) * radius * 0.5;

			final Color p1Color = Color.getHSBColor(i / 360F, 1F, 1F);
			final String label1 = String.format("(%.0f, %.0f)", x1, y1);
			p1.pos(x1, y1).diameter(16).color(p1Color).label(label1).draw(c);

			final Color p2Color = Color.getHSBColor(2 * i / 360F, 1F, 1F);
			final String label2 = String.format("(%.0f, %.0f)", x2, y2);
			p2.pos(x2, y2).diameter(16).color(p2Color).label(label2).draw(c);

			i = (i + 1) % 360;

			Thread.sleep(25);
		}
	}

	/**
	 * Testing and demonstrating purposes only.
	 * 
	 * @param args Ignored.
	 * @throws InterruptedException Thrown if problem with thread sleeping
	 */
	public static void main(String[] args) throws InterruptedException {
		final MinusculeWindow window = new MinusculeWindow(1210, 640,
				"Minuscule 2.0", Color.WHITE);
		final Canvas canvas = window.getCanvas();

		// TEST SCREEN 1
		firstTest(canvas);
		Thread.sleep(1_000);
		canvas.clearAll();

		// TEST SCREEN 2
		staticCircleTest(canvas);
		Thread.sleep(10_000);
		canvas.clearAll();

		// TEST SCREEN 3
		circleMovementTest(canvas);
	}
}
