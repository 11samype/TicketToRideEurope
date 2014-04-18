import static org.junit.Assert.*;

import java.awt.Point;

import gui.DrawableDestination;
import gui.DrawableDoubleRoute;
import gui.DrawableRoute;
import objects.TrainColor;

import org.junit.Test;


public class DrawableRouteTest {

	@Test
	public void testBigConstructor() {
		DrawableDestination start = new DrawableDestination("here", new Point());
		DrawableDestination end = new DrawableDestination("there", new Point());
		DrawableRoute route = new DrawableRoute(start, end, 4, TrainColor.BLACK);
		assertNotNull(route);
	}
	
	@Test
	public void testGetStart() {
		DrawableDestination start = new DrawableDestination("here", new Point());
		DrawableDestination end = new DrawableDestination("there", new Point());
		DrawableRoute route = new DrawableRoute(start, end, 4, TrainColor.BLACK);
		assertNotNull(route);
		
		assertEquals(route.getStart(), start);
	}

}
