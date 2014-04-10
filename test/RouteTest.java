

import static org.junit.Assert.*;
import objects.Destination;
import objects.DestinationRoute;
import objects.FerryRoute;
import objects.TrainColor;
import objects.TrainRoute;
import objects.TunnelRoute;

import org.junit.Before;
import org.junit.Test;

public class RouteTest {

	private Destination start = new Destination("Barcelona");
	private Destination end = new Destination("Madrid");

	private TrainRoute trainR;
	private TrainRoute trainRC;
	private TunnelRoute tunnelR;
	private TunnelRoute tunnelRC;
	private FerryRoute ferryR;
	private DestinationRoute destR;
	private DestinationRoute destR_score;

	@Before
	public void setup() {
		trainR = new TrainRoute(start, end, 4);
		trainRC = new TrainRoute(start, end, TrainColor.BLUE, 3);
		tunnelR = new TunnelRoute(start, end, 2);
		tunnelRC = new TunnelRoute(end, start, TrainColor.GREEN, 2);
		ferryR = new FerryRoute(start, end, 1, 2);
		destR = new DestinationRoute(start, end);
		destR_score = new DestinationRoute(start, end, 5);
	}

	@Test
	public void testInitRoute() {
		assertNotNull(trainR);
		assertNotNull(trainRC);
		assertNotNull(tunnelR);
		assertNotNull(tunnelRC);
		assertNotNull(ferryR);
		assertNotNull(destR);
		assertNotNull(destR_score);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testZeroLengthException() {
		TrainRoute bad = new TrainRoute(start, end, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeLengthException() {
		TunnelRoute bad = new TunnelRoute(start, end, -2);
	}

	@Test
	public void testToString() {
		assertEquals("Barcelona-Madrid", trainR.toString());
	}

	@Test
	public void testGetStart() {
		assertNotNull(ferryR.getStart());
		assertEquals(start, trainRC.getStart());
		assertSame(end, tunnelRC.getStart());
	}

	@Test
	public void testGetEnd() {
		assertNotNull(trainR.getEnd());
		assertEquals(end, ferryR.getEnd());
		assertSame(end, tunnelR.getEnd());
	}

	@Test
	public void testNonInitGetColor() {
		assertNotNull(trainR.getColor());
		assertEquals(TrainColor.RAINBOW, trainR.getColor());
	}

	@Test
	public void testInitGetColor() {
		assertNotNull(tunnelRC.getColor());
		assertEquals(TrainColor.GREEN, tunnelRC.getColor());
	}

	@Test
	public void testGetLength() {
		assertTrue(trainRC.getLength() > 0);
		assertEquals(3, trainRC.getLength());

		assertTrue(destR.getScore() > 0);
		assertTrue(destR_score.getScore() > 0);
		assertEquals(1, destR.getScore());
		assertEquals(5, destR_score.getScore());

	}

}
