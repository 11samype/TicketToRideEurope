import static org.junit.Assert.*;

import java.awt.Point;

import gui.DrawableDestination;
import gui.DrawableDoubleRoute;

import org.junit.Test;

public class DrawableDoubleRouteTest {

	@Test
	public void testConstructor() {
		DrawableDestination start = new DrawableDestination("here", new Point());
		DrawableDestination end = new DrawableDestination("there", new Point());
		DrawableDoubleRoute route = new DrawableDoubleRoute(start, end);
		assertNotNull(route);
		
		DrawableDestination start2 = new DrawableDestination("here", new Point());
		DrawableDestination end2 = new DrawableDestination("there", new Point());
		DrawableDoubleRoute route2 = new DrawableDoubleRoute(start, end, 1);
		assertNotNull(route2);
		assertEquals(route2.getEnd(), end2);
	}
//	
//	@Test
//	public void testScore() {
//		
//		DrawableDestination start2 = new DrawableDestination("here", new Point());
//		DrawableDestination end2 = new DrawableDestination("there", new Point());
//		DrawableDoubleRoute route2 = new DrawableDoubleRoute(start, end, 1);
//		assertNotNull(route2);
//		assertEquals(route2.getEnd(), end2);
//	}

}
