import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gui.drawables.DrawableDestination;
import gui.drawables.DrawableRoute;

import java.awt.Point;

import objects.TrainColor;

import org.junit.Test;


public class DrawableRouteTest {

	@Test
	public void testBigConstructor() {
		DrawableDestination start = new DrawableDestination("here", new Point(0, 0));
		DrawableDestination end = new DrawableDestination("there", new Point(1, 1));
		DrawableRoute route = new DrawableRoute(start, end, 1, TrainColor.BLACK);
		assertNotNull(route);
	}
	
	@Test
	public void testGetStart() {
		DrawableDestination start = new DrawableDestination("here", new Point(0, 0));
		DrawableDestination end = new DrawableDestination("there", new Point(1, 1));
		DrawableRoute route = new DrawableRoute(start, end, 1, TrainColor.BLACK);
		assertNotNull(route);
		
		assertEquals(route.getStart(), start);
	}

}
