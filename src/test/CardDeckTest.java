package test;

public class CardDeckTest {

	@Test
	public void testInitDeck() {
		CardDeck deck = new CardDeck();
		
		assertNotNull(deck);
	}
	
}
