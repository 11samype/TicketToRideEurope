import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gui.drawables.DrawableDestination;
import gui.drawables.DrawableRoute;

import java.awt.Point;

import objects.TrainColor;
import objects.TunnelRoute;

import org.junit.Test;


public class DrawableRouteTest {

	@Test
	public void testConstructor() {
		DrawableDestination start = new DrawableDestination("here", new Point(0, 0));
		DrawableDestination end = new DrawableDestination("there", new Point(1, 1));
		TunnelRoute route = new TunnelRoute(start, end, TrainColor.BLACK, 1);
		
		DrawableRoute draw_route = new DrawableRoute(start, end, route);
		assertNotNull(draw_route);
	}
	
	@Test
	public void testGetStart() {
		DrawableDestination start = new DrawableDestination("here", new Point(0, 0));
		DrawableDestination end = new DrawableDestination("there", new Point(1, 1));
		TunnelRoute route = new TunnelRoute(start, end, TrainColor.BLACK, 1);
		DrawableRoute draw_route = new DrawableRoute(start, end, route);
		assertNotNull(draw_route);
		
		assertEquals(draw_route.getStart(), start);
	}

}
