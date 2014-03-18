package objects;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class CardDeck {

	protected List<Card> cards = new ArrayList<Card>();
	
	public CardDeck() {
		
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	public void populateDeck(List<Card> cardList) {
		this.cards.addAll(cardList);
		shuffle();
	}
	
	public Card draw() {
		return this.cards.remove(0);
	}
	
	public List<Card> listOfCardsInOrder() {
		return this.cards;
	}

}
