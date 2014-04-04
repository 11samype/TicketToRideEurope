package objects.abstracts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import objects.interfaces.ICard;
import objects.interfaces.IDeck;

public abstract class AbstractDeck<K extends ICard> implements IDeck<K> {

	protected List<K> cards = new ArrayList<K>();

	public AbstractDeck() {
		addCardsToDeck();
	}

	@Override
	public void shuffle() {
		Collections.shuffle(this.cards);
	}

	@Override
	public void populate(List<K> cardList) {
		this.cards.clear();
		this.cards.addAll(cardList);
		shuffle();
	}

	@Override
	public K draw() {
		if (size() <= 0) {
			throw new IndexOutOfBoundsException("The deck is empty!");
		}
		return this.cards.remove(0);
	}

	@Override
	public List<K> getCards() {
		return this.cards;
	}

	@Override
	public int size() {
		return getCards().size();
	}

	@Override
	public boolean isEmpty() {
		return getCards().isEmpty();
	}
	
	@Override
	public int numInDeck(K card) {
		
		// May not work
		
		int count = 0;
		
		for (int i = 0; i < this.cards.size(); i++) {
			if (this.cards.get(i).equals(card)) {
				count++;
			}
		}
		
		return count;
		
	}

	/**
	 * Initialize the deck with cards
	 */
	protected abstract void addCardsToDeck();

}
