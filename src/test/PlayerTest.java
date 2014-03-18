package test;

import static org.junit.Assert.*;
import objects.Player;
import objects.TrainCarDeck;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testInitPlayer() {
		Player p = new Player("Conductor");
		assertNotNull(p);
		assertEquals("Conductor", p.getName());
		assertEquals(0, p.getScore());
		assertEquals(0, p.getHand().size());
		assertEquals(0, p.getDestinations().size());
		assertEquals(0, p.getTrains().size());
	}

	@Test
	public void testDrawOneFromTrainCarDeck() {
		TrainCarDeck d = new TrainCarDeck();
		int numberOfCards = d.size();

		Player p = new Player();
		p.drawCard(d);

		assertEquals(numberOfCards - 1, d.size());
		assertEquals(1, p.getHand().size());
	}

	@Test
	public void testDrawTwoFromTrainCarDeck() {
		TrainCarDeck d = new TrainCarDeck();
		int numberOfCards = d.size();

		Player p = new Player();
		for (int i = 0; i < 2; i++) {
			p.drawCard(d);
		}

		assertEquals(numberOfCards - 2, d.size());
		assertEquals(2, p.getHand().size());
	}





}
