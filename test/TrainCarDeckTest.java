import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;

import objects.TrainCarCard;
import objects.TrainCarDeck;

import org.junit.Test;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

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
	}
	
	
}
