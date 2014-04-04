package test;
import static org.junit.Assert.*;
import managers.CardManager;

import org.junit.Test;


/**
 * TODO Put here a description of what this class does.
 *
 * @author samynpd.
 *         Created Apr 3, 2014.
 */
public class CardManagerTest {

	@Test
	public void testFillDeelFromDeck() {
		CardManager cardManager = new CardManager();
		cardManager.fillDealFromDeck();
		assertTrue(cardManager.dealFull());
	}

}
