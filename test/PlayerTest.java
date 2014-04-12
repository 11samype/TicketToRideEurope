
import static org.junit.Assert.*;

import java.util.ArrayList;

import objects.Destination;
import objects.DestinationCard;
import objects.DestinationDeck;
import objects.DestinationRoute;
import objects.GameState;
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
		assertNotNull(p.getHand());
		assertEquals(0, p.getHand().size());
		assertNotNull(p.getDestinations());
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

	@Test(expected = UnsupportedOperationException.class)
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
		Player p = new Player();

		TrainCarDeal d = GameState.getInstance().getCardManager().getDealCards();

		ArrayList<TrainCarCard> cardList = new ArrayList<TrainCarCard>();

		for (int i = 0; i < 5; i++) {
			cardList.add(d.getCardAtPosition(i));
			System.out.printf("%d %s\n", i, d.getCardAtPosition(i).getColor());
		}

		for (int i = 0; i < 3; i++) {
			p.drawCardFromDeal(i);
		}

		for (int i = 0; i < p.getHand().size(); i++) {
			System.out.println(p.getHand().getCard(i).getColor());
		}

		assertEquals(3, p.getHand().size());
		assertEquals(cardList.get(0), p.getHand().getCard(0));
		assertEquals(cardList.get(1), p.getHand().getCard(1));
		assertEquals(cardList.get(2), p.getHand().getCard(2));
	}


}
