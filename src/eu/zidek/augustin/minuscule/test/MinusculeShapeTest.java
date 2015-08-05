package eu.zidek.augustin.minuscule.test;

import java.awt.geom.Ellipse2D;

import eu.zidek.augustin.minuscule.Canvas;
import eu.zidek.augustin.minuscule.MShape;
import eu.zidek.augustin.minuscule.MinusculeWindow;

class MinusculeShapeTest {

	/**
	 * MShape tests
	 * 
	 * @param args Ignored
	 * @throws InterruptedException Thrown if sleeping is interrupted
	 */
	public static void main(String[] args) throws InterruptedException {
		final MinusculeWindow window = new MinusculeWindow(700, 500);
		final Canvas c = window.getCanvas();
		c.drawGrid(50, 50);

		final MShape shape1 = new MShape(
				new Ellipse2D.Double(100, 100, 200, 50)).draw(c);
		Thread.sleep(750);

		shape1.translate(100, 100).fill(true).draw(c);
	}

}
