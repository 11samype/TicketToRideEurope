

import static org.junit.Assert.*;

import java.util.List;

import objects.DiscardPile;
import objects.TrainCarCard;
import objects.TrainColor;

import org.junit.Test;

public class DiscardPileTest {

	@Test
	public void testInitDiscardPile() {
		DiscardPile discardPile = new DiscardPile();
		assertNotNull(discardPile);
	}

	@Test
	public void testInitIsEmpty() {
		DiscardPile discardPile = new DiscardPile();
		assertTrue(discardPile.isEmpty());
	}

	@Test
	public void testDiscard() {
		DiscardPile discardPile = new DiscardPile();

		discardPile.add(new TrainCarCard(TrainColor.BLUE));
		discardPile.add(new TrainCarCard(TrainColor.BLUE));

		assertEquals(2, discardPile.size());
	}

	@Test
	public void testPickupDiscard() {
		DiscardPile discardPile = new DiscardPile();

		discardPile.add(new TrainCarCard(TrainColor.BLUE));
		discardPile.add(new TrainCarCard(TrainColor.GREEN));
		discardPile.add(new TrainCarCard(TrainColor.YELLOW));
		discardPile.add(new TrainCarCard(TrainColor.RAINBOW));

		assertEquals(4, discardPile.size());

		List<TrainCarCard> pickedUp = discardPile.pickup();

		assertEquals(0, discardPile.size());
		assertEquals(4, pickedUp.size());
	}

}
