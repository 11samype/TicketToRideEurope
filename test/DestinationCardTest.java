import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import objects.Destination;
import objects.DestinationCard;
import objects.DestinationRoute;
import objects.TrainCarDeck;

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
	
	@Test
	public void testHashCode() {
		Destination start = new Destination("start");
		Destination end = new Destination("end");
		DestinationRoute dRoute = new DestinationRoute(start, end);
		
		DestinationCard destCard1 = new DestinationCard(dRoute);
		DestinationCard destCard2 = new DestinationCard(dRoute);
		
		assertEquals(destCard1.hashCode(), destCard2.hashCode());
	}
	
	@Test
	public void testEquals() {
		
		Destination start = new Destination("start");
		Destination end = new Destination("end");
		DestinationRoute dRoute = new DestinationRoute(start, end);
		
		DestinationCard destCard1 = new DestinationCard(dRoute);
		DestinationCard destCard2 = new DestinationCard(null);
		
		assertFalse(destCard1.equals(null));
		assertFalse(destCard1.equals(new TrainCarDeck()));
		assertFalse(destCard2.equals(destCard1));
	}

}
