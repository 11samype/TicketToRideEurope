import static org.junit.Assert.*;
import objects.Destination;
import objects.DestinationCard;
import objects.DestinationRoute;

import org.junit.Test;


public class DestinationCardTest {

	@Test
	public void testGetRoute() {
		Destination start = new Destination("start");
		Destination end = new Destination("end");
		DestinationRoute dRoute = new DestinationRoute(start, end);
		
		DestinationCard destCard = new DestinationCard(dRoute);
		
		assertEquals(destCard.getRoute(), dRoute);
	}
	
	@Test
	public void testGetScore() {
		int points = 1;
		Destination start = new Destination("start");
		Destination end = new Destination("end");
		DestinationRoute dRoute = new DestinationRoute(start, end, points);
		
		DestinationCard destCard = new DestinationCard(dRoute);
		
		assertEquals(destCard.getScore(), points);
	}

}
