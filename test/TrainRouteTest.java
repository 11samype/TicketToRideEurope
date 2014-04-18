import static org.junit.Assert.*;
import objects.Destination;
import objects.TrainRoute;

import org.junit.Test;


public class TrainRouteTest {

	@Test
	public void testEquals() {
		int length = 5;
		Destination start1 = new Destination("start1");
		Destination end1 = new Destination("end1");
		TrainRoute route1 = new TrainRoute(start1, end1, length);
		
		Destination start2 = new Destination("start2");
		Destination end2 = new Destination("end2");
		TrainRoute route2 = new TrainRoute(start2, end2, length);
		
		assertFalse(route1.equals(route2));
		
		Destination start = new Destination("start");
		TrainRoute nullRoute = new TrainRoute(start, null, length);
		
		assertFalse(nullRoute.equals(route2));
	}

}
