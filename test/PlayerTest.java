import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import objects.Destination;
import objects.DestinationCard;
import objects.DestinationDeck;
import objects.DestinationRoute;
import objects.FerryRoute;
import objects.Player;
import objects.TrainCarCard;
import objects.TrainCarDeal;
import objects.TrainCarDeck;
import objects.TrainColor;
import objects.TrainRoute;
import objects.abstracts.AbstractPlayer;
import objects.interfaces.IPlayer;
import objects.interfaces.IRoute;

import org.junit.Before;
import org.junit.Test;

import utils.GameState;
import utils.GameState.CardManager;
import utils.exceptions.DestinationAfterTrainException;
import utils.exceptions.DestinationHasStationException;
import utils.exceptions.NotEnoughCardsForRouteException;
import utils.exceptions.OutOfStationsException;
import utils.exceptions.RouteAfterTrainException;
import utils.exceptions.RouteOwnedException;

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

		assertEquals(AbstractPlayer.MAX_NUM_STATIONS, p.getScore());
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
	public void testClaimRouteSuccess() {
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
 
		p.getHand().getCards().addAll(cardList);
		
		assertEquals(p.getHand().size(), 9);

		TrainRoute routeToClaim = new TrainRoute(new Destination("here"),
				new Destination("there"), TrainColor.BLACK, 6);
		try {
			p.claimRoute(routeToClaim);
			assertEquals(p.getHand().size(), 3);
			assertSame(p.getRoutes().get(0), routeToClaim);
		} catch (NotEnoughCardsForRouteException e) {
			// nothing
		} catch (RouteOwnedException e) {
			// nothing
		} catch (RouteAfterTrainException e) {
			// nothing
		}

	}

	@Test(expected = NotEnoughCardsForRouteException.class)
	public void testNotEnoughCardsForRoute() throws NotEnoughCardsForRouteException {
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
		
		p.getHand().getCards().addAll(cardList);

		TrainRoute routeToClaim = new TrainRoute(new Destination("here"),
				new Destination("there"), TrainColor.BLACK, 6);
		try {
			p.claimRoute(routeToClaim); // throws
		} catch (NotEnoughCardsForRouteException e) {
			throw e;
		} catch (RouteOwnedException e) {
			// nothing
		} catch (RouteAfterTrainException e) {
			// nothing
		}

	}

	@Test
	public void testDealToPlayer() {
		List<IPlayer> players = new ArrayList<IPlayer>();
		players.add(new Player());
		GameState.withPlayers(players);
		Player p = GameState.getCurrentPlayer();

		CardManager m = GameState.getCardManager();
		TrainCarDeal d = m.getDealCards();

		ArrayList<TrainCarCard> cardList = new ArrayList<TrainCarCard>();

		for (int i = 0; i < 5; i++) {
			cardList.add(d.getCardAtPosition(i));
//			System.out.printf("%d %s\n", i, d.getCardAtPosition(i).getColor());
		}

		p.drawCardFromDeal(m, 0);
		

		for (int i = 0; i < p.getHand().size(); i++) {
			p.getHand().getCard(i).getColor();
		}

		assertEquals(5, p.getHand().size());
		assertEquals(cardList.get(0), p.getLastCardDrawn());
		//assertEquals(cardList.get(1), p.getHand().getCard(1));
		//assertEquals(cardList.get(2), p.getHand().getCard(2));
	}

	@Test
	public void testDrawCardFromDestinationDeck() {

		DestinationDeck destDeck = new DestinationDeck();

		int initialSize = destDeck.size();

		List<IPlayer> players = new ArrayList<IPlayer>();
		players.add(new Player());
		GameState.withPlayers(players);
		Player player = GameState.getCurrentPlayer();
		

		try {
			player.drawCardFromDeck(destDeck);
			assertEquals(initialSize - 1, destDeck.size());

			assertEquals(5, player.getDestinationHand().size());

			player.drawCardFromDeck(destDeck);
			player.drawCardFromDeck(destDeck);
			player.drawCardFromDeck(destDeck);
			
			assertEquals(8, player.getDestinationHand().size());
			assertEquals(initialSize - 4, destDeck.size());

		} catch (DestinationAfterTrainException e) {
			// shouldn't be caught in this test
		}

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

//		TrainCarDeck deck = new TrainCarDeck();
//
//		player.drawCardFromDeck(deck);
//		player.drawCardFromDeck(deck);
//
//		assertFalse(player.canDrawTrainCard());

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
		
		p.getHand().getCards().addAll(cardList);

		DestinationRoute route = new DestinationRoute(new Destination("start"),
				new Destination("end"));
		
		List<IRoute> routes = new ArrayList<IRoute>();
		routes.add(route);

		p.getRoutes().addAll(routes);
		
		List<DestinationRoute> destinations = p.getCompletedDestinations();
		
		assertEquals(1, destinations.size());
		
	}
	
	@Test
	public void testHasEnoughCardsForFerry() {
		Player p = new Player();
		
		FerryRoute ferry = new FerryRoute(new Destination("here"), new Destination("there"), 4, 1);
		
		assertFalse(p.hasEnoughCardsForFerry(ferry));
		
		List<TrainCarCard> cards = new ArrayList<TrainCarCard>();
		
		for (int i = 0; i < 4; i++) {
			cards.add(new TrainCarCard(TrainColor.RAINBOW));
		}
		
		p.populateHand(cards);
		
		assertTrue(p.hasEnoughCardsForFerry(ferry));
		
	}
	
	@Test
	public void testListColorsToString() {
		Player p = new Player();
		TrainColor[] trainColors = {TrainColor.BLACK, TrainColor.BLUE, TrainColor.RAINBOW};
		
		String[] trainColorStrings = p.listColorsToString(trainColors);
		String[] expected = {"BLACK", "BLUE", "RAINBOW"};
		
		assertArrayEquals(expected, trainColorStrings);
	}
	
	@Test
	public void testRouteColorMatched() {
		
		Player p = new Player();
		
		List<TrainCarCard> threeCards = new ArrayList<TrainCarCard>();
		
		threeCards.add(new TrainCarCard(TrainColor.BLACK));
		threeCards.add(new TrainCarCard(TrainColor.BLUE));
		threeCards.add(new TrainCarCard(TrainColor.RAINBOW));
		
		assertEquals(0, p.routeColorMatched(TrainColor.GREEN, threeCards));
		assertEquals(1, p.routeColorMatched(TrainColor.RAINBOW, threeCards));
	}
	
	@Test
	public void testGetTunnelChoices() {
		Player p = new Player();
		List<TrainCarCard> cards = new ArrayList<TrainCarCard>();
		for (int i = 0; i < 4; i++) {
			cards.add(new TrainCarCard(TrainColor.BLACK));
		}
		p.populateHand(cards);
		
		String[] expected = {"CANCEL", "BLACK"};
		assertEquals(expected, p.getTunnelChoices(TrainColor.BLACK, 1));
		
		for (int i = 0; i < 4; i++) {
			cards.add(new TrainCarCard(TrainColor.RAINBOW));
		}
		p.populateHand(cards);
		
		String[] expected1 = {"CANCEL", "RAINBOW", "RAINBOW"};
		assertEquals(expected1, p.getTunnelChoices(TrainColor.RAINBOW, 1));
	}
	
	@Test
	public void testHasEnoughCardsForTunnelRoute() {
		Player p = new Player();
		List<TrainCarCard> cards = new ArrayList<TrainCarCard>();
		for (int i = 0; i < 4; i++) {
			cards.add(new TrainCarCard(TrainColor.BLACK));
		}
		p.populateHand(cards);
		
		DestinationRoute route = new DestinationRoute(new Destination("here"), new Destination("there"));
		
		assertTrue(p.hasEnoughCardsForTunnelRoute(route, TrainColor.BLACK));
		assertFalse(p.hasEnoughCardsForTunnelRoute(route, TrainColor.BLUE));
	}
	
	@Test
	public void testGetFerryCardChoices() {
		Player p = new Player();
		List<TrainCarCard> cards = new ArrayList<TrainCarCard>();
		for (int i = 0; i < 4; i++) {
			cards.add(new TrainCarCard(TrainColor.BLACK));
		}
		p.populateHand(cards);
		
		TrainColor[] expected = {TrainColor.BLACK};
		
		assertEquals(expected, p.getFerryCardChoices(1));
	}

}
