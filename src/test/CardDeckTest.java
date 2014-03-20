package test;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import objects.TrainCarCard;
import objects.TrainCarDeck;

public class CardDeckTest {

	@Test
	public void testInitDeck() {
		TrainCarDeck deck = new TrainCarDeck();
		assertNotNull(deck);

	}

	@Test
	public void testShuffle() {
		TrainCarDeck deck = new TrainCarDeck();
		boolean shuffled = false;
		for(int i = 0; i < 12; i++) {
			TrainCarCard cardDrawn = (TrainCarCard) deck.draw();
			if(cardDrawn.getAwtColor() != Color.blue) {
				shuffled = true;
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

	@Test(expected=IndexOutOfBoundsException.class)
	public void testDrawIndexOutOfBoundsException() {
		TrainCarDeck deck = new TrainCarDeck();
		while(true) {
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
	public void testDeckSize() {
		TrainCarDeck deck = new TrainCarDeck();
		assertEquals(deck.size(), 110);
	}
}
