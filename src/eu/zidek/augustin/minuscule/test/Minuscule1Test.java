package eu.zidek.augustin.minuscule.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.CubicCurve2D;

import eu.zidek.augustin.minuscule.Canvas;
import eu.zidek.augustin.minuscule.LabelPosition;
import eu.zidek.augustin.minuscule.MinusculeWindow;

class Minuscule1Test {

	private static void movementTest(final Canvas c)
			throws InterruptedException {
		final double deg2rad = 2 * Math.PI / 360;
		final int circleDiameter = 300;

		int i = 0;
		c.drawGrid(20, 20);
		while (true) {
			final double x = Math.cos(i * deg2rad) * circleDiameter * 1.5;
			final double y = Math.sin(i * deg2rad) * circleDiameter;

			final int red = i * 255 / 360;
			final int green = 4 * i % 255;
			final int blue = 2 * i % 255;

			final Color pointColor = new Color(red, green, blue);
			final String coordLabel = "(" + Math.round(x) + "," + Math.round(y)
					+ ")";
			c.drawPoint(x, y, coordLabel, 16, pointColor, Color.BLACK,
					LabelPosition.NORTHEAST);
			c.drawPoint(0, 0, "ORIGIN");
			i = (i + 1) % 360;

			Thread.sleep(20);
			c.clearAllButGrid();
		}
	}

	private static void drawCircle(final Canvas c) {
		c.setEuclideanCoordinates(true);
		final double deg2rad = 2 * Math.PI / 360;
		final int circleDiameter = 300;

		c.drawGrid(40, 40);

		for (int i = 0; i < 360; i += 10) {
			final double x = Math.cos(i * deg2rad) * circleDiameter;
			final double y = Math.sin(i * deg2rad) * circleDiameter;

			final int red = i * 255 / 360;
			final int green = 4 * i % 255;
			final int blue = 2 * i % 255;

			final Color pointColor = new Color(red, green, blue);
			final String coordLabel = "(" + Math.round(x) + "," + Math.round(y)
					+ ")";
			c.drawPoint(x, y, coordLabel, 16, pointColor, Color.BLACK,
					LabelPosition.NORTHEAST);
		}

		c.drawLabel(40, 40, "LABEL at (40,40)");
		c.drawPoint(0, 0, "ORIGIN");
	}

	/**
	 * Testing and demonstrating purposes only.
	 * 
	 * @param args Ignored.
	 * @throws InterruptedException Bla, testing only.
	 */
	public static void main(String[] args) throws InterruptedException {
		final MinusculeWindow window = new MinusculeWindow(1210, 640,
				"Minuscule 1.0", Color.WHITE);
		final Canvas canvas = window.getCanvas();

		System.out.println("First test: start");

		// Draw a grid
		canvas.drawGrid(0.1F, 10, 10, Color.GRAY);

		// Draw an arbitrary Shape defined by Java built-in libraries
		final Color greenTrp = new Color(0, 255, 100, 100);
		canvas.drawShape(new CubicCurve2D.Double(10, 10, 2000, 500, 300, -500,
				500, 500), greenTrp, true);

		// Draw some lines and points
		for (int i = 10; i < 500; i += 10) {
			final int x1 = 20000 / (i + 15);
			final int y1 = 150 + i;
			final int x2 = 7 * i / 3;
			final int y2 = 100;

			canvas.drawPoint(x2, y2, String.valueOf(x1), 10, Color.BLUE,
					Color.BLACK, LabelPosition.NORTH);
			canvas.drawPoint(x1, y1, 10, Color.RED);

			canvas.drawLine(x1, y1, x2, y2, 2, Color.YELLOW);
		}

		// Draw String labels
		canvas.drawLabel(0, 50, "Hello", new Font("Arial", Font.BOLD, 20),
				Color.BLACK);
		canvas.drawLabel(200, 50, "World.");

		// Draw a rectangle
		canvas.drawShape(new Rectangle(700, 400, 100, 55), Color.ORANGE, true);
		canvas.drawShape(new Rectangle(700, 400, 100, 55), Color.GRAY, false);

		canvas.drawPoint(800, 500, "Special point with label", 100,
				Color.BLACK, Color.BLUE, LabelPosition.EAST);

		// Display for 5 seconds, then clear the canvas and continue with the
		// next demo
		Thread.sleep(2000);
		canvas.clearAll();

		System.out.println("First test: end");

		System.out.println("Circle test: start");
		drawCircle(canvas);
		Thread.sleep(20000);
		canvas.clearAll();
		System.out.println("Circle test: end");

		System.out.println("Movement test: start");
		movementTest(canvas);
	}

}
