import java.util.ArrayList;
import java.util.List;


public class DiscardPile {

	protected List<Card> cards = new ArrayList<Card>();
	
	public DiscardPile() {
		
	}
	
	public void discard(Card card) {
		this.cards.add(card);
	}
	
	public List<Card> emptyDiscardPile() {
		
		List<Card> cardsToReturn = new ArrayList<Card>();
		cardsToReturn.addAll(this.cards);
		this.cards.clear();
		
		return cardsToReturn;
	}
}
