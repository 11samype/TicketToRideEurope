import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.geom.Point2D;

import gui.DrawableDestination;

import org.junit.Test;


public class DrawableDestinationTest {

	@Test
	public void testGetLocation() {
		Point point = new Point(1, 1);
		DrawableDestination drawableDest = new DrawableDestination("Place", point);
		
		assertEquals(point, drawableDest.getLocation());
	}
	
	@Test
	public void testGetCenter() {
		Point point = new Point(1, 1);
		DrawableDestination drawableDest = new DrawableDestination("Place", point);
		
		Point2D.Double point2D = new Point2D.Double(point.getX() + drawableDest.DOT_RADIUS, point.getY() + drawableDest.DOT_RADIUS);
		assertEquals(point2D, drawableDest.getCenter());
	}


}
