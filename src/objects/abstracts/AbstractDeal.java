package objects.abstracts;

import java.util.ArrayList;

import objects.interfaces.ICard;
import objects.interfaces.IDeal;

public abstract class AbstractDeal<K extends ICard> implements IDeal<K> {

	protected ArrayList<K> deal = new ArrayList<K>();
	protected static final int MAX_DEALT_CARDS = 5;

	@Override
	public void addCard(K card) {

		if (this.deal.size() >= MAX_DEALT_CARDS) {
			throw new UnsupportedOperationException(
					"Deal can't have more than " + MAX_DEALT_CARDS + " cards!");
		}

		this.deal.add(card);

	}

	@Override
	public K removeCard(K card) {
		if (this.deal.remove(card))
			return card;
		else
			return null;
	}

	public int getSize() {
		return this.deal.size();
	}

	@Override
	public K removeCardAtPosition(int index) {
		return this.deal.remove(index);
	}

	public K getCardAtPosition(int index) {
		return this.deal.get(index);
	}

	@Override
	public boolean isDealFull() {
		return (getSize() >= MAX_DEALT_CARDS);
	}
}
