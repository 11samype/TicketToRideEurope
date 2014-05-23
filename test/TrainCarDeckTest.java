import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import objects.TrainCarCard;
import objects.TrainCarDeck;
import objects.TrainColor;

import org.junit.Test;

import utils.GameState;

public class TrainCarDeckTest {

	@Test
	public void testInitDeck() {
		TrainCarDeck deck = new TrainCarDeck();
		assertNotNull(deck);
		assertEquals(110, deck.size());
	}

	@Test
	public void testShuffle() {
		TrainCarDeck deck = new TrainCarDeck();
		boolean shuffled = false;
		for (int i = 0; i < 12; i++) {
			TrainCarCard cardDrawn = deck.draw();
			if (cardDrawn.getAwtColor() != Color.blue) {
				shuffled = true;
			} else {
				continue;
			}
		}

		assertTrue(shuffled);
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
	public void drawTopThreeTest() {
		TrainCarDeck deck = new TrainCarDeck();
		int originalDeckSize = deck.size();
		ArrayList<TrainCarCard> cards = new ArrayList<TrainCarCard>();
		cards = (ArrayList<TrainCarCard>) deck.drawTopThree();
		
		assertEquals(originalDeckSize - 3, deck.size());
		assertEquals(3, cards.size());
		
		while (deck.size() >= 3) {
			deck.draw();
		}
		assertEquals(2, deck.size());
		originalDeckSize = deck.size();
		
		List<TrainCarCard> discarded = new ArrayList<TrainCarCard>();
		for (int i = 0; i < 5; i++) {
			discarded.add(new TrainCarCard(TrainColor.RED));
		}
		GameState.getCardManager().getDiscardPile().populate(discarded);
		cards = (ArrayList<TrainCarCard>) deck.drawTopThree();
		assertEquals(0, GameState.getCardManager().getDiscardPile().size());
		assertEquals(3, cards.size());
		assertEquals(originalDeckSize + discarded.size() - 3, deck.size());
		
	}
	
	
}
