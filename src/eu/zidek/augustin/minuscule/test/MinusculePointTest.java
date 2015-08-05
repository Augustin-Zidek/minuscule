package eu.zidek.augustin.minuscule.test;

import java.awt.Color;

import eu.zidek.augustin.minuscule.Canvas;
import eu.zidek.augustin.minuscule.MBasicStroke;
import eu.zidek.augustin.minuscule.MLabel;
import eu.zidek.augustin.minuscule.MPoint;
import eu.zidek.augustin.minuscule.MinusculeWindow;

class MinusculePointTest {

	/**
	 * MPoint tests
	 * 
	 * @param args Ignored
	 * @throws InterruptedException Thrown if sleeping is interrupted
	 */
	public static void main(String[] args) throws InterruptedException {
		final MinusculeWindow window = new MinusculeWindow(700, 500);
		final Canvas c = window.getCanvas();

		c.drawGrid(50, 50);

		final MPoint p = new MPoint().pos(100, 100).draw(c);
		Thread.sleep(750);

		p.pos(200, 100).color(Color.RED).draw(c);
		Thread.sleep(750);

		p.pos(300, 100).color(Color.RED).diameter(20).draw(c);
		Thread.sleep(750);

		p.pos(400, 100).color(Color.RED).diameter(20).fill(false).draw(c);
		Thread.sleep(750);

		p.pos(500, 100).color(Color.RED).diameter(20).fill(false).label("Hi")
				.draw(c);
		Thread.sleep(750);

		p.pos(600, 100).color(Color.RED).diameter(20).fill(false)
				.label("Hi", 90).draw(c);
		Thread.sleep(750);

		p.pos(100, 200).color(Color.RED).diameter(20).fill(false)
				.label("Hi", 90).stroke(new MBasicStroke(50)).draw(c);
		Thread.sleep(750);

		final MLabel label1 = new MLabel("Hi 2");
		p.pos(200, 200).color(Color.RED).diameter(20).fill(false).label(label1)
				.stroke(new MBasicStroke(50)).draw(c);
		Thread.sleep(750);

		final MLabel label2 = new MLabel("Hi 3").pos(35, 35);
		p.pos(300, 200).color(Color.RED).diameter(20).fill(false).label(label2)
				.stroke(new MBasicStroke(50)).draw(c);
		Thread.sleep(750);

		final MLabel label3 = (MLabel) new MLabel("Hi 4").pos(15, -15).stroke(
				new MBasicStroke(40));
		p.color(Color.RED).label(label3).diameter(20)
				.stroke(new MBasicStroke(10)).pos(400, 200).fill(false).draw(c);
		Thread.sleep(750);
		
		p.translate(100, 100).draw(c);

		Thread.sleep(5000);
		p.delete(c);

	}

}
