package test;

import static org.junit.Assert.*;
import objects.Destination;
import objects.FerryRoute;
import objects.RouteBuilder;
import objects.TrainColor;
import objects.TrainRoute;
import objects.TunnelRoute;

import org.junit.Test;

public class RouteBuilderTest {

	Destination start = new Destination("Rome");
	Destination end = new Destination("Venice");

	@Test
	public void testInitRouteBuilder() {
		RouteBuilder b = new RouteBuilder(start, end, 1);
		assertNotNull(b);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSameDestinationException() {
		RouteBuilder bad = new RouteBuilder(end, end, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testZeroLengthException() {
		RouteBuilder bad = new RouteBuilder(start, end, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeLengthException() {
		RouteBuilder bad = new RouteBuilder(start, end, -2);
	}

	@Test
	public void testWithColor() {
		RouteBuilder b = new RouteBuilder(start, end, 1);
		b.withColor(TrainColor.BLUE);
		TrainRoute trainR = (TrainRoute) b.build(RouteBuilder.TRAIN);
		assertEquals(TrainColor.BLUE, trainR.getColor());
	}

	@Test
	public void testBuild() {
		Destination start = new Destination("Berlin");
		Destination end = new Destination("Frankfurt");
		RouteBuilder builder = new RouteBuilder(start, end, 2);

		assertTrue(builder.build(RouteBuilder.TRAIN) instanceof TrainRoute);

		assertTrue(builder.build(RouteBuilder.TUNNEL) instanceof TunnelRoute);
		assertFalse(builder.build(RouteBuilder.TUNNEL) instanceof FerryRoute);

		assertTrue(builder.build(RouteBuilder.FERRY) instanceof FerryRoute);
		assertFalse(builder.build(RouteBuilder.FERRY) instanceof TrainRoute);

	}

	@Test(expected=IllegalArgumentException.class)
	public void TestBuildException() {
		RouteBuilder builder = new RouteBuilder(start, end, 2);
		builder.build("airplane");
	}

	@Test
	public void testWithLocomotiveCount() {
		RouteBuilder builder = new RouteBuilder(start, end, 2);
		FerryRoute ferryR = (FerryRoute) builder.build(RouteBuilder.FERRY);
		assertEquals(0, ferryR.getLocomotiveCount());

		builder.withLocomotiveCount(2);
		ferryR = (FerryRoute) builder.build(RouteBuilder.FERRY);
		assertEquals(2, ferryR.getLocomotiveCount());
	}

	@Test
	public void testReverseDirection() {
		RouteBuilder builder = new RouteBuilder(start, end, 2);
		TrainRoute trainR = (TrainRoute) builder.build(RouteBuilder.TRAIN);

		builder.reverseDirection();
		TrainRoute trainR_reverse = (TrainRoute) builder.build(RouteBuilder.TRAIN);

		assertSame(trainR.getStart(), trainR_reverse.getEnd());
		assertSame(trainR.getEnd(), trainR_reverse.getStart());

	}

}
