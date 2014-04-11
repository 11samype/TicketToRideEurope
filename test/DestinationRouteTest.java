import static org.junit.Assert.*;
import objects.Destination;
import objects.DestinationRoute;

import org.junit.Test;


public class DestinationRouteTest {

	@Test
	public void testToString() {
		DestinationRoute route =  new DestinationRoute(new Destination("here"), new Destination("there"));
		assertEquals(route.toString(), "here-there (1)");
	}

}
