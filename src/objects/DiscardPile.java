package objects;
import java.util.ArrayList;
import java.util.List;


public class DiscardPile extends AbstractDeck {

	public void add(ICard card) {
		this.cards.add(card);
	}

	public List<ICard> pickup() {
		List<ICard> cardsToReturn = new ArrayList<ICard>();
		cardsToReturn.addAll(this.cards);
		this.cards.clear();
		return cardsToReturn;
	}
}
