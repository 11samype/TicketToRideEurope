
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import objects.DiscardPile;
import objects.TrainCarCard;
import objects.TrainColor;

import org.junit.Test;

public class DiscardPileTest {

	@Test
	public void testInitDiscardPile() {
		DiscardPile<?> discardPile = new DiscardPile<>();
		assertNotNull(discardPile);
	}

	@Test
	public void testInitIsEmpty() {
		DiscardPile<?> discardPile = new DiscardPile<>();
		assertTrue(discardPile.isEmpty());
	}

	@Test
	public void testDiscard() {
		DiscardPile<TrainCarCard> discardPile = new DiscardPile<TrainCarCard>();

		discardPile.add(new TrainCarCard(TrainColor.BLUE));
		discardPile.add(new TrainCarCard(TrainColor.BLUE));

		assertEquals(2, discardPile.size());
	}

	@Test
	public void testPickupDiscard() {
		DiscardPile<TrainCarCard> discardPile = new DiscardPile<TrainCarCard>();

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
