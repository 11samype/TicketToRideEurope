package test;

import static org.junit.Assert.*;

import java.awt.Color;

import objects.DiscardPile;
import objects.TrainCarCard;
import objects.TrainColor;

import org.junit.Test;

public class DiscardPileTest {

	@Test
	public void testDiscard() {
		DiscardPile discardPile = new DiscardPile();

		discardPile.add(new TrainCarCard(TrainColor.BLUE));
		discardPile.add(new TrainCarCard(TrainColor.BLUE));

		assertEquals(2, discardPile.size());
	}

	@Test
	public void testEmptyDiscard() {
		DiscardPile discardPile = new DiscardPile();

		discardPile.add(new TrainCarCard(TrainColor.BLUE));
		discardPile.add(new TrainCarCard(TrainColor.BLUE));
		discardPile.add(new TrainCarCard(TrainColor.BLUE));
		discardPile.add(new TrainCarCard(TrainColor.BLUE));

		assertEquals(4, discardPile.size());

		discardPile.pickup();

		assertEquals(0, discardPile.size());
	}

}
