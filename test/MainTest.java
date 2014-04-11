import static org.junit.Assert.*;
import objects.GameState;
import objects.GameState.CardManager;

import org.junit.Test;

public class MainTest {

	@Test
	public void testPrepareGameData() {
		
		Main.main(null);
		
		GameState game = GameState.getInstance();
		CardManager cardManager = game.getCardManager();
		assertNotNull(game);
		assertEquals(46, cardManager.getDestinationDeck().size());
		assertEquals(105, cardManager.getTrainCarDeck().size());

		assertTrue(cardManager.getDealCards().isDealFull());
		assertEquals(5, cardManager.getDealCards().getSize());

		assertEquals(0, cardManager.getDiscardPile().size());
	}

}
