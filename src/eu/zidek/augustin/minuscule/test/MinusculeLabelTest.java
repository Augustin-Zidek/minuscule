package eu.zidek.augustin.minuscule.test;

import java.awt.Color;

import eu.zidek.augustin.minuscule.Canvas;
import eu.zidek.augustin.minuscule.MBoundingBox;
import eu.zidek.augustin.minuscule.MLabel;
import eu.zidek.augustin.minuscule.MRectangle;
import eu.zidek.augustin.minuscule.MinusculeWindow;

class MinusculeLabelTest {

	/**
	 * MLine tests
	 * 
	 * @param args Ignored
	 * @throws InterruptedException Thrown if sleeping is interrupted
	 */
	public static void main(String[] args) throws InterruptedException {
		final MinusculeWindow window = new MinusculeWindow(800, 600);
		final Canvas c = window.getCanvas();
		c.drawGrid(50, 50);

		final MLabel l = new MLabel("Hello").pos(100, 100).draw(c);
		Thread.sleep(750);

		l.pos(200, 100).color(Color.GREEN).draw(c);
		Thread.sleep(750);

		l.pos(300, 100).color(Color.GREEN).label("Label's label", 45).draw(c);
		Thread.sleep(750);

		final MLabel l2 = new MLabel("Label");
		new MLabel("Hello world in Minucsule").pos(300, 300).label(l2).draw(c);

		// Auto placement test
		for (double i = 0; i <= 360; i += 0.1) {
			l2.positionSetByParent(true, i).draw(c);
			final MBoundingBox lbb = l2.getBoundingRectangle();
			final MRectangle rect = new MRectangle().pos(lbb.x, lbb.y)
					.dimensions(lbb.width, lbb.height).draw(c);
			Thread.sleep(1);
			rect.delete(c);
		}
	}
}
