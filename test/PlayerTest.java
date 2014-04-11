
import static org.junit.Assert.*;

import java.util.ArrayList;

import objects.Destination;
import objects.DestinationCard;
import objects.DestinationDeck;
import objects.DestinationRoute;
import objects.Player;
import objects.TrainCarCard;
import objects.TrainCarDeal;
import objects.TrainCarDeck;
import objects.TrainColor;
import objects.TrainRoute;
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
	
	@Test
	public void testClaimRoute() {
		TrainCarDeck d = new TrainCarDeck();
		while (!d.isEmpty()) {
			d.draw();
		}
		
		ArrayList<TrainCarCard> cardList = new ArrayList<TrainCarCard>();

		Player p = new Player();

		for (int i = 0; i < 10; i++) {
			cardList.add(new TrainCarCard(TrainColor.BLACK));
		}
		
		d.populate(cardList);
		
		for (int i = 0; i < 9; i++) {
			p.drawCardFromDeck(d);
		}
		
		assertEquals(p.getHand().size(), 9);
		
		p.claimRoute(new TrainRoute(new Destination("here"), new Destination("there"), TrainColor.BLACK, 6));
		
		assertEquals(p.getHand().size(), 3);
		
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testClaimRouteFail() {
		TrainCarDeck d = new TrainCarDeck();
		while (!d.isEmpty()) {
			d.draw();
		}
		
		ArrayList<TrainCarCard> cardList = new ArrayList<TrainCarCard>();

		Player p = new Player();

		for (int i = 0; i < 10; i++) {
			cardList.add(new TrainCarCard(TrainColor.BLACK));
		}
		
		d.populate(cardList);
		
		for (int i = 0; i < 3; i++) {
			p.drawCardFromDeck(d);
		}
		
		p.claimRoute(new TrainRoute(new Destination("here"), new Destination("there"), TrainColor.BLACK, 6));
		
	}
	
	@Test
	public void testDealToPlayer() {
		TrainCarDeal d = new TrainCarDeal();
		
		ArrayList<TrainCarCard> cardList = new ArrayList<TrainCarCard>();

		Player p = new Player();

		for (int i = 0; i < 5; i++) {
			cardList.add(new TrainCarCard(TrainColor.BLACK));
		}
		
		for (TrainCarCard card : cardList) {
			d.addCard(card);
		}
		
		for (int i = 0; i < 3; i++) {
			p.drawCardFromDeal(d, d.getCardAtPosition(0));
		}
		
		assertEquals(p.getHand().size(), 3);
		assertEquals(p.getHand().getCard(0), cardList.get(0));
		assertEquals(p.getHand().getCard(1), cardList.get(1));
		assertEquals(p.getHand().getCard(2), cardList.get(2));
	}
	

}
