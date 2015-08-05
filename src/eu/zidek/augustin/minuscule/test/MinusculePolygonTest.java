package eu.zidek.augustin.minuscule.test;

import java.awt.Color;

import eu.zidek.augustin.minuscule.Canvas;
import eu.zidek.augustin.minuscule.MPoint;
import eu.zidek.augustin.minuscule.MPolygon;
import eu.zidek.augustin.minuscule.MinusculeWindow;

class MinusculePolygonTest {

	/**
	 * MPolygon tests
	 * 
	 * @param args Ignored
	 * @throws InterruptedException Thrown if sleeping is interrupted
	 */
	public static void main(String[] args) throws InterruptedException {
		final MinusculeWindow window = new MinusculeWindow(700, 500);
		final Canvas c = window.getCanvas();
		c.drawGrid(50, 50);

		final MPolygon p = new MPolygon().addVertex(100, 100).draw(c);
		Thread.sleep(750);

		p.addVertex(200, 100).draw(c);
		Thread.sleep(750);

		p.addVertex(200, 200).draw(c);
		Thread.sleep(750);

		p.addVertex(
				new MPoint().pos(100, 200).color(Color.GREEN).label("p4", 45))
				.draw(c);
		Thread.sleep(750);

		p.addVertex(100, 150).fill(true).draw(c);
		Thread.sleep(750);
		
		p.addVertex(50, 150).draw(c);
		Thread.sleep(750);
		
		p.color(Color.BLUE).draw(c);
		Thread.sleep(750);
		
		p.fillColor(new Color(0x64_FF_FF_00, true)).draw(c);
		Thread.sleep(750);
		
		p.deleteVertex(0).draw(c);
		Thread.sleep(750);
		
		p.deleteVertex(100, 150).draw(c);
		Thread.sleep(750);
		

	}
}
