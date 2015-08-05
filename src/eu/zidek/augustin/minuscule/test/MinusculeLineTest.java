package eu.zidek.augustin.minuscule.test;

import java.awt.BasicStroke;
import java.awt.Color;

import eu.zidek.augustin.minuscule.Canvas;
import eu.zidek.augustin.minuscule.MBasicStroke;
import eu.zidek.augustin.minuscule.MLine;
import eu.zidek.augustin.minuscule.MinusculeWindow;

class MinusculeLineTest {

	/**
	 * MLine tests
	 * 
	 * @param args Ignored
	 * @throws InterruptedException Thrown if sleeping is interrupted
	 */
	public static void main(String[] args) throws InterruptedException {
		final MinusculeWindow window = new MinusculeWindow(700, 500);
		final Canvas c = window.getCanvas();
		c.drawGrid(50, 50);

		final MLine l = new MLine().start(100, 100).end(200, 200).draw(c);
		Thread.sleep(750);

		l.start(200, 100).end(300, 200).draw(c);
		Thread.sleep(750);

		l.start(300, 100).end(400, 200).color(Color.BLUE).draw(c);
		Thread.sleep(750);

		l.start(400, 100).end(500, 200).color(Color.BLUE).label("Hi").draw(c);
		Thread.sleep(750);

		l.start(500, 100).end(600, 200).color(Color.BLUE).label("Hi")
				.thickness(15F).draw(c);
		Thread.sleep(750);

		l.start(100, 300).end(100, 400).color(Color.BLUE).label("Hi", 0)
				.thickness(15F).draw(c);
		Thread.sleep(750);

		l.start(200, 300)
				.end(200, 400)
				.color(Color.BLUE)
				.label("Hi", 0)
				.thickness(15F)
				.stroke(new MBasicStroke(12F, BasicStroke.CAP_ROUND,
						BasicStroke.JOIN_BEVEL)).draw(c);
		Thread.sleep(750);

	}

}
