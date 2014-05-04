package objects;

import java.util.ArrayList;
import java.util.List;

import objects.abstracts.AbstractDeck;
import objects.interfaces.ICard;

public class DiscardPile<K extends ICard> extends AbstractDeck<K> {

	public DiscardPile() {
		addCardsToDeck(); // starts empty
	}

	@Override
	protected void addCardsToDeck() {
		populate(new ArrayList<K>());
	}

	public void add(K card) {
		this.cards.add(card);
	}

	public List<K> pickup() {
		List<K> cardsToReturn = new ArrayList<K>(this.cards);
		this.cards.clear();
		return cardsToReturn;
	}

}
