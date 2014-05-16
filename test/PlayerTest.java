import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import objects.Destination;
import objects.DestinationCard;
import objects.DestinationDeck;
import objects.DestinationRoute;
import objects.GameState;
import objects.GameState.CardManager;
import objects.Player;
import objects.TrainCarCard;
import objects.TrainCarDeal;
import objects.TrainCarDeck;
import objects.TrainColor;
import objects.TrainRoute;
import objects.abstracts.AbstractPlayer;
import objects.interfaces.IRoute;

import org.junit.Before;
import org.junit.Test;

import utils.exceptions.DestinationAfterTrainException;
import utils.exceptions.DestinationHasStationException;
import utils.exceptions.NotEnoughCardsForRouteException;
import utils.exceptions.OutOfStationsException;

public class PlayerTest {

	private Queue<TrainColor> colors;

	@Before
	public void setup() {
		this.colors = GameState.availableColors;

	}

	@Test
	public void testNewPlayer() {
		Player p = new Player();
		assertNotNull(p);
		assertEquals("New Player", p.getName());
	}

	@Test
	public void testInitPlayerInfo() {

		TrainColor nextAvail = colors.peek();
		Player p = new Player("Conductor");
		assertEquals(nextAvail, p.getColor());

		assertEquals(0, p.getScore());
		assertNotNull(p.getHand());
		assertEquals(0, p.getHand().size());
		assertNotNull(p.getDestinationHand());
		assertEquals(0, p.getDestinationHand().size());
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

//	@Test
//	public void testDrawTwoFromTrainCarDeck() {
//		TrainCarDeck d = new TrainCarDeck();
//		int numberOfCards = d.size();
//
//		Player p = new Player();
//		for (int i = 0; i < 2; i++) {
//			p.drawCardFromDeck(d);
//		}
//
//		assertNotEquals(0, p.getHand().size());
//		assertEquals(numberOfCards - 2, d.size());
//		assertEquals(2, p.getHand().size());
//	}

	@Test
	public void testClaimRoute() {
		TrainCarDeck d = new TrainCarDeck();
		ArrayList<TrainCarCard> cardList = new ArrayList<TrainCarCard>();

		Player p = new Player();

		for (int i = 0; i < 9; i++) {
			cardList.add(new TrainCarCard(TrainColor.BLACK));
		}

		d.populate(cardList);

//		for (int i = 0; i < 9; i++) {
//			p.drawCardFromDeck(d);
//		}

		p.populateHand(cardList);
		
		assertEquals(p.getHand().size(), 9);

		TrainRoute routeToClaim = new TrainRoute(new Destination("here"),
				new Destination("there"), TrainColor.BLACK, 6);
		try {
			p.claimRoute(routeToClaim);
		} catch (NotEnoughCardsForRouteException e) {
			// nothing
		}

		assertEquals(p.getHand().size(), 3);
		assertSame(p.getRoutes().get(0), routeToClaim);

	}

	@Test(expected = NotEnoughCardsForRouteException.class)
	public void testClaimRouteFail() throws NotEnoughCardsForRouteException {
		TrainCarDeck d = new TrainCarDeck();
		ArrayList<TrainCarCard> cardList = new ArrayList<TrainCarCard>();

		Player p = new Player();

		for (int i = 0; i < 3; i++) {
			cardList.add(new TrainCarCard(TrainColor.BLACK));
		}

		d.populate(cardList);

//		for (int i = 0; i < 3; i++) {
//			p.drawCardFromDeck(d);
//		}
		
		p.populateHand(cardList);

		TrainRoute routeToClaim = new TrainRoute(new Destination("here"),
				new Destination("there"), TrainColor.BLACK, 6);
		try {
			p.claimRoute(routeToClaim); // throws
		} catch (NotEnoughCardsForRouteException e) {
			throw e;
		}

	}

	@Test
	public void testDealToPlayer() {
		Player p = new Player();

		CardManager m = GameState.getInstance().getCardManager();
		TrainCarDeal d = m.getDealCards();

		ArrayList<TrainCarCard> cardList = new ArrayList<TrainCarCard>();

		for (int i = 0; i < 5; i++) {
			cardList.add(d.getCardAtPosition(i));
			System.out.printf("%d %s\n", i, d.getCardAtPosition(i).getColor());
		}

		for (int i = 0; i < 1; i++) {
			p.drawCardFromDeal(m, i);
		}

		for (int i = 0; i < p.getHand().size(); i++) {
			p.getHand().getCard(i).getColor();
		}

		assertEquals(1, p.getHand().size());
		assertEquals(cardList.get(0), p.getHand().getCard(0));
		//assertEquals(cardList.get(1), p.getHand().getCard(1));
		//assertEquals(cardList.get(2), p.getHand().getCard(2));
	}

	@Test
	public void testDrawCardFromDestinationDeck() {

		DestinationDeck destDeck = new DestinationDeck();

		int initialSize = destDeck.size();

		Player player = new Player();

		try {
			player.drawCardFromDeck(destDeck);
			assertEquals(initialSize - 1, destDeck.size());

			assertEquals(1, player.getDestinationHand().size());

			player.drawCardFromDeck(destDeck);
			player.drawCardFromDeck(destDeck);
			player.drawCardFromDeck(destDeck);

		} catch (DestinationAfterTrainException e) {
			// shouldn't be caught in this test
		}

		assertEquals(initialSize - 4, destDeck.size());

		assertEquals(4, player.getDestinationHand().size());

	}

	@Test(expected=DestinationHasStationException.class)
	public void testPlaceStationOnDestination() throws DestinationHasStationException {

		Destination dest = new Destination("here");

		Player player = new Player();

		try {
			assertTrue(dest.buildStation(player));
			assertTrue(dest.hasStation());
			assertFalse(dest.buildStation(player)); // throws
		} catch (OutOfStationsException e) {
			// nothing
		} catch (DestinationHasStationException e) {
			throw e;
		}
	}

	@Test(expected = DestinationAfterTrainException.class)
	public void testCantDrawDestAfterTrain() throws DestinationAfterTrainException {
		DestinationDeck d = new DestinationDeck();
		ArrayList<DestinationCard> destList = new ArrayList<DestinationCard>();

		DestinationRoute route = new DestinationRoute(new Destination("start"),
				new Destination("end"));
		DestinationCard card = new DestinationCard(route);

		destList.add(card);

		d.populate(destList);

		TrainCarDeck c = new TrainCarDeck();
		ArrayList<TrainCarCard> cardList = new ArrayList<TrainCarCard>();

		TrainCarCard trainCard = new TrainCarCard(TrainColor.BLACK);
		cardList.add(trainCard);

		c.populate(cardList);

		Player p = new Player();

		p.drawCardFromDeck(c);
		
		try {
			p.drawCardFromDeck(d); // throws
		} catch (DestinationAfterTrainException e) {
			throw e;
		}

	}

	@Test
	public void testCanDrawTrainCar() {

		Player player = new Player();

		assertTrue(player.canDrawTrainCard());

		TrainCarDeck deck = new TrainCarDeck();

		player.drawCardFromDeck(deck);
		player.drawCardFromDeck(deck);

		assertFalse(player.canDrawTrainCard());

	}

	@Test
	public void testgetLastCardDrawn() {
		TrainCarDeck d = new TrainCarDeck();
		ArrayList<TrainCarCard> cardList = new ArrayList<TrainCarCard>();

		Player p = new Player();

		TrainCarCard card = new TrainCarCard(TrainColor.BLACK);
		cardList.add(card);

		d.populate(cardList);

		assertNull(p.getLastCardDrawn());

		p.drawCardFromDeck(d);

		assertEquals(p.getLastCardDrawn(), card);

	}
	
	@Test
	public void testGetCompletedDestinations() {
		Player p = new Player();
		
		List<TrainCarCard> cardList = new ArrayList<TrainCarCard>();
		
		for (int i = 0; i < 10; i++) {
			cardList.add(new TrainCarCard(TrainColor.BLACK));
		}
		
		p.populateHand(cardList);

		DestinationRoute route = new DestinationRoute(new Destination("start"),
				new Destination("end"));
		
		List<IRoute> routes = new ArrayList<IRoute>();
		routes.add(route);

		p.setRoutes(routes);
		
		List<DestinationRoute> destinations = p.getCompletedDestinations();
		
		assertEquals(1, destinations.size());
		
	}

}
