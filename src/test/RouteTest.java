package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import objects.Destination;
import objects.FerryRoute;
import objects.RouteBuilder;
import objects.TrainRoute;
import objects.TunnelRoute;

import org.junit.Before;
import org.junit.Test;

public class RouteTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRoute() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStart() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEnd() {
		fail("Not yet implemented");
	}

	@Test
	public void testRouteBuilder() {
		Destination start = new Destination("Berlin");
		Destination end = new Destination("Frankfurt");
		RouteBuilder builder = new RouteBuilder(start, end, 2);

		assertTrue(builder.build(RouteBuilder.TRAIN) instanceof TrainRoute);

		assertTrue(builder.build(RouteBuilder.TUNNEL) instanceof TunnelRoute);

		assertTrue(builder.build(RouteBuilder.FERRY) instanceof FerryRoute);

	}

}
