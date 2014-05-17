import static org.junit.Assert.assertEquals;
import gui.drawables.DrawableDestination;

import java.awt.Point;
import java.awt.geom.Point2D;

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

		Point2D.Double point2D = new Point2D.Double(point.getX() + DrawableDestination.DOT_RADIUS, point.getY() + DrawableDestination.DOT_RADIUS);
		assertEquals(point2D, drawableDest.getCenter());
	}


}
