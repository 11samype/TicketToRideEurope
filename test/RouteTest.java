import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.awt.Color;

import objects.Destination;
import objects.DestinationRoute;
import objects.FerryRoute;
import objects.Player;
import objects.TrainColor;
import objects.TrainRoute;
import objects.TunnelRoute;

import org.junit.Before;
import org.junit.Test;

public class RouteTest {

	private Destination start = new Destination("Barcelona");
	private Destination end = new Destination("Madrid");

	private DestinationRoute destR;
	private FerryRoute ferryR;
	private TrainRoute trainRC;
	private TunnelRoute tunnelR;
	private TunnelRoute tunnelRC;
	private DestinationRoute destR_score;
	private TrainRoute trainR;

	@Before
	public void setup() {
		destR = new DestinationRoute(start, end);
		ferryR = new FerryRoute(start, end, 2, 1);
		trainRC = new TrainRoute(start, end, TrainColor.BLUE, 3);
		tunnelR = new TunnelRoute(start, end, 4);
		tunnelRC = new TunnelRoute(end, start, TrainColor.GREEN, 6);
		destR_score = new DestinationRoute(start, end, 8);
		trainR = new TrainRoute(start, end, 10);
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
		assertEquals(Color.GREEN, tunnelRC.getAwtColor());
	}

	@Test
	public void testGetLength() {
		assertEquals(3, trainRC.getLength());

	}

	@Test
	public void testGetRouteScore() {
		assertEquals(2, ferryR.getScore());
		assertEquals(4, trainRC.getScore());
		assertEquals(7, tunnelR.getScore());
		assertEquals(13, tunnelRC.getScore());
		TrainRoute maxLength  = new TrainRoute(start, end, 8);
		assertEquals(21, maxLength.getScore());

		assertEquals(-1, trainR.getScore());
	}

	@Test
	public void testGetDestinationRouteScore() {
		assertEquals(1, destR.getScore());
		assertEquals(8, destR_score.getScore());
	}

	@Test
	public void testRouteNotEquals() {
		assertNotEquals(trainR, null);
		assertNotEquals(tunnelRC, ferryR); // different lengths
		assertNotEquals(destR, new Player());

		TrainRoute nullEnd = new TrainRoute(start, null, 2);
		TrainRoute nullStart = new TrainRoute(null, end, 2);
		assertNotEquals(nullStart, nullEnd);
	}

	@Test
	public void testRouteEquals() {
		assertEquals(trainR, trainR);


	}

}
