package test;

import static org.junit.Assert.*;

import java.awt.Color;

import objects.DiscardPile;
import objects.TrainCarCard;

import org.junit.Test;

public class DiscardPileTest {

	@Test
	public void testDiscard() {
		DiscardPile discardPile = new DiscardPile();
		
		discardPile.discard(new TrainCarCard(Color.BLUE));
		discardPile.discard(new TrainCarCard(Color.BLUE));
		
		assertEquals(2, discardPile.size());
	}
	
	@Test
	public void testEmptyDiscard() {
		DiscardPile discardPile = new DiscardPile();
		
		discardPile.discard(new TrainCarCard(Color.BLUE));
		discardPile.discard(new TrainCarCard(Color.BLUE));
		discardPile.discard(new TrainCarCard(Color.BLUE));
		discardPile.discard(new TrainCarCard(Color.BLUE));
		
		assertEquals(4, discardPile.size());
		
		discardPile.empty();
		
		assertEquals(0, discardPile.size());
	}

}
