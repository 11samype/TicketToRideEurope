import static org.junit.Assert.*;
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
		
		assertNotNull(destCard1);
		assertNotEquals(destCard1, new TrainCarDeck()); // not same object
		assertNotEquals(destCard2, destCard1); // this.value == null, other.value != null
		assertSame(destCard1, destCard1); // this == other
		
		DestinationCard destCard3 = new DestinationCard(dRoute);
		assertEquals(destCard1, destCard3); // this.value == other.value
		
		assertEquals(destCard2, new DestinationCard(null)); 
	
	}

}
