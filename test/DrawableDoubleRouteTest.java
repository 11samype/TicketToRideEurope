import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gui.drawables.DrawableDestination;
import gui.drawables.DrawableDoubleRoute;
import gui.drawables.DrawableRoute;

import java.awt.Point;

import objects.TrainRoute;

import org.junit.Test;

public class DrawableDoubleRouteTest {

	@Test
	public void testConstructor() {
		DrawableDestination start = new DrawableDestination("here", new Point());
		DrawableDestination end = new DrawableDestination("there", new Point());
		TrainRoute route = new TrainRoute(start, end, 1);
		DrawableRoute draw_route = new DrawableRoute(start, end, route);
		DrawableDoubleRoute doub_route = new DrawableDoubleRoute(draw_route, draw_route);
		assertNotNull(doub_route);
		
		assertEquals(doub_route.getEnd(), end);
	}

	@Test
	public void testScore() {

		DrawableDestination start = new DrawableDestination("here", new Point());
		DrawableDestination end = new DrawableDestination("there", new Point());
		TrainRoute route = new TrainRoute(start, end, 1);
		DrawableRoute draw_route = new DrawableRoute(start, end, route);
		DrawableDoubleRoute doub_route = new DrawableDoubleRoute(draw_route, draw_route);
		assertNotNull(doub_route);
		assertEquals(doub_route.getEnd(), end);
		assertEquals(1, doub_route.getScore());
	}

}
