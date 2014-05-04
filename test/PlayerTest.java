
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Queue;

import objects.Destination;
import objects.DestinationDeck;
import objects.GameState;
import objects.GameState.CardManager;
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
	public void testClaimRoute() {
		TrainCarDeck d = new TrainCarDeck();
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

		TrainRoute routeToClaim = new TrainRoute(new Destination("here"), new Destination("there"), TrainColor.BLACK, 6);
		p.claimRoute(routeToClaim);

		assertEquals(p.getHand().size(), 3);
		assertSame(p.getRoutes().get(0), routeToClaim);

	}

	@Test(expected = UnsupportedOperationException.class)
	public void testClaimRouteFail() {
		TrainCarDeck d = new TrainCarDeck();
		ArrayList<TrainCarCard> cardList = new ArrayList<TrainCarCard>();

		Player p = new Player();

		for (int i = 0; i < 10; i++) {
			cardList.add(new TrainCarCard(TrainColor.BLACK));
		}

		d.populate(cardList);

		for (int i = 0; i < 3; i++) {
			p.drawCardFromDeck(d);
		}

		TrainRoute routeToClaim = new TrainRoute(new Destination("here"), new Destination("there"), TrainColor.BLACK, 6);
		p.claimRoute(routeToClaim);

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

		for (int i = 0; i < 3; i++) {
			p.drawCardFromDeal(m, i);
		}

		for (int i = 0; i < p.getHand().size(); i++) {
			System.out.println(p.getHand().getCard(i).getColor());
		}

		assertEquals(3, p.getHand().size());
		assertEquals(cardList.get(0), p.getHand().getCard(0));
		assertEquals(cardList.get(1), p.getHand().getCard(1));
		assertEquals(cardList.get(2), p.getHand().getCard(2));
	}

	@Test
	public void testDrawCardFromDestinationDeck() {

		DestinationDeck destDeck = new DestinationDeck();

		int initialSize = destDeck.size();

		Player player = new Player();

		player.drawCardFromDeck(destDeck);

		assertEquals(initialSize - 1, destDeck.size());

		assertEquals(1, player.getDestinationHand().size());

		player.drawCardFromDeck(destDeck);
		player.drawCardFromDeck(destDeck);
		player.drawCardFromDeck(destDeck);

		assertEquals(initialSize - 4, destDeck.size());

		assertEquals(4, player.getDestinationHand().size());

	}

	@Test
	public void testPlaceStationOnDestination() {

		Destination dest = new Destination("here");

		Player player = new Player();

		dest.buildStation(player);

		assertTrue(dest.hasStation());
		assertFalse(dest.buildStation(player));

	}


}
