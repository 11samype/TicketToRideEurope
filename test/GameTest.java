

import static org.junit.Assert.*;
import objects.Game;

import org.junit.Test;

public class GameTest {

	@Test
	public void testInitGame() {
		Game game = new Game();
		assertNotNull(game);
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(46, game.getDestinationDeck().size());
		assertEquals(110, game.getTrainCarDeck().size());

	}

}
