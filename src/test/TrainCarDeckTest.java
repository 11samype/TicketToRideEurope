package test;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.HashSet;

import org.junit.Test;

import objects.TrainCarCard;
import objects.TrainCarDeck;

public class TrainCarDeckTest {

	@Test
	public void testInitDeck() {
		TrainCarDeck deck = new TrainCarDeck();
		assertNotNull(deck);

	}

	@Test
	public void testShuffle() {
		TrainCarDeck deck = new TrainCarDeck();
		boolean shuffled = false;
		for (int i = 0; i < 12; i++) {
			TrainCarCard cardDrawn = deck.draw();
			if (cardDrawn.getAwtColor() != Color.blue) {
				shuffled = true;
			}
		}

		assertTrue(shuffled);
	}

	@Test
	public void testUniqueCardsInDeck() {
		TrainCarDeck deck = new TrainCarDeck();
		boolean unique = true;
		HashSet<TrainCarCard> seen = new HashSet<TrainCarCard>();

		while (!deck.isEmpty()) {
			TrainCarCard cardDrawn = deck.draw();
			if (seen.contains(cardDrawn)) {
				unique = false;
			} else {
				seen.add(cardDrawn);
			}
		}

		assertTrue(unique);
	}

	@Test
	public void testDraw() {
		TrainCarDeck deck = new TrainCarDeck();
		int size = deck.size();

		deck.draw();
		deck.draw();

		assertEquals(size - 2, deck.size());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testOverDrawException() {
		TrainCarDeck deck = new TrainCarDeck();
		while (true) {
			deck.draw();
		}
	}

	@Test
	public void testPopulateDeck() {
		TrainCarDeck deck1 = new TrainCarDeck();
		deck1.draw();
		TrainCarDeck deck2 = new TrainCarDeck();

		deck2.populate(deck1.getCards());

		assertEquals(deck1.size(), deck2.size());
	}

	@Test
	public void testInitDeckSize() {
		TrainCarDeck deck = new TrainCarDeck();
		assertEquals(deck.size(), 110);
	}
}
