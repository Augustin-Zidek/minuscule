package eu.zidek.augustin.minuscule.test;

import java.awt.BasicStroke;

import eu.zidek.augustin.minuscule.Canvas;
import eu.zidek.augustin.minuscule.MBasicStroke;
import eu.zidek.augustin.minuscule.MLine;
import eu.zidek.augustin.minuscule.MPoint;
import eu.zidek.augustin.minuscule.MinusculeWindow;

class MinusculeFunctionTest {

	/**
	 * Function drawing tests
	 * 
	 * @param args Ignored
	 * @throws InterruptedException Thrown if sleeping is interrupted
	 */
	public static void main(String[] args) throws InterruptedException {
		final MinusculeWindow window = new MinusculeWindow(700, 500);
		final Canvas c = window.getCanvas();

		c.drawGrid(50, 50);
		c.setEuclideanCoordinates(true);

		final double start = System.currentTimeMillis();

		// Using points (extremely naive approach)
		for (double x = -350; x <= 350; x += 0.001) {
			final double y = 100 * Math.sin(x / 25) + 150;
			new MPoint().pos(x, y).diameter(0.5).draw(c);
		}

		// Using lines (naive approach)
		for (double x = -350; x <= 350; x += 1) {
			final double y1 = 100 * Math.cos(x / 25);
			final double x2 = x + 1;
			final double y2 = 100 * Math.cos(x2 / 25);
			new MLine()
					.start(x, y1)
					.end(x2, y2)
					.stroke(new MBasicStroke(1, BasicStroke.CAP_ROUND,
							BasicStroke.JOIN_ROUND)).draw(c);
		}

		System.out.println(System.currentTimeMillis() - start + " ms");

	}
}
