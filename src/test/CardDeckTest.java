package test;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import objects.DiscardPile;
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
			if(cardDrawn.getColor() != Color.blue) {
				shuffled = true;
			}
		}
		
		assertTrue(shuffled);
	}
	
	@Test
	public void testDrawAndDiscard() {
		TrainCarDeck deck = new TrainCarDeck();
		DiscardPile discardPile = new DiscardPile();
		
		discardPile.discard(deck.draw());
		discardPile.discard(deck.draw());
		
		assertEquals(108, deck.size());
		assertEquals(2, discardPile.sizeOfDiscardPile());
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testDrawIndexOutOfBoundsException() {
		TrainCarDeck deck = new TrainCarDeck();
		while(true) {
			deck.draw();
		}
	}
	
}
