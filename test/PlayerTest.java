
import static org.junit.Assert.*;
import objects.DestinationCard;
import objects.DestinationDeck;
import objects.DestinationRoute;
import objects.Player;
import objects.TrainCarDeck;
import objects.abstracts.AbstractPlayer;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testPlayer() {
		Player p = new Player();
		assertNotNull(p);
		assertEquals("New Player", p.getName());
	}
	
	@Test
	public void testInitPlayer() {
		Player p = new Player("Conductor");
		assertNotNull(p);
		assertEquals("Conductor", p.getName());
		assertEquals(0, p.getScore());
		assertEquals(0, p.getHand().size());
		assertEquals(0, p.getDestinations().size());
		assertEquals(AbstractPlayer.MAX_NUM_TRAINS, p.getNumTrains());
		assertEquals(AbstractPlayer.MAX_NUM_STATIONS, p.getNumStations());
	}

	@Test
	public void testDrawOneFromTrainCarDeck() {
		TrainCarDeck d = new TrainCarDeck();
		int numberOfCards = d.size();

		Player p = new Player();
		p.drawCardFromDeck(d);

		assertNotEquals(0, p.getHand().size());
		assertEquals(numberOfCards - 1, d.size());
		assertEquals(1, p.getHand().size());
	}

	@Test
	public void testDrawTwoFromTrainCarDeck() {
		TrainCarDeck d = new TrainCarDeck();
		int numberOfCards = d.size();

		Player p = new Player();
		for (int i = 0; i < 2; i++) {
			p.drawCardFromDeck(d);
		}

		assertNotEquals(0, p.getHand().size());
		assertEquals(numberOfCards - 2, d.size());
		assertEquals(2, p.getHand().size());
	}
	
	@Test
	public void testGetDestinationsInJTableFormat() {
		DestinationDeck deck = new DestinationDeck();
		
		Player p = new Player();
		
		p.drawCardFromDeck(deck);
		
		DestinationCard dest = p.getDestinations().get(0);
		String start = dest.getRoute().getStart().toString();
		String end = dest.getRoute().getEnd().toString();
		int points = dest.getRoute().getScore();
		
		Object[][] expected = { { start , end , points} };
		
		assertEquals(p.getDestinationsInJTableFormat(), expected);
		
	}

}
