import static org.junit.Assert.assertEquals;
import objects.Destination;
import objects.DestinationRoute;

import org.junit.Assert;
import org.junit.Test;

public class DestinationRouteTest {

	@Test
	public void testToString() {
		DestinationRoute route = new DestinationRoute(new Destination("here"),
				new Destination("there"));
		assertEquals(route.toString(), "here-there (1)");
	}

	@Test
	public void testToObjectArray() {
		DestinationRoute route = new DestinationRoute(new Destination("here"),
				new Destination("there"));
		// the keys are missing from the resource bundle
		Object[] expected = new Object[] { "!here!", "!there!", 1 };
		Assert.assertArrayEquals(expected, route.toLocalizedArray());
	}

}
