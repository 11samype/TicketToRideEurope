package objects.abstracts;

import java.util.ArrayList;

import objects.interfaces.ICard;
import objects.interfaces.IDeal;

public abstract class AbstractDeal<K extends ICard> implements IDeal<K> {

	public static final int MAX_CARDS = 5;
	protected ArrayList<K> deal = new ArrayList<K>(MAX_CARDS);
	protected int size = 0;

	public AbstractDeal() {
		for (int i = 0; i < MAX_CARDS; i++) {
			this.deal.add(null);
		}
	}

	@Override
	public void addCard(K card) {
		if (getSize() >= MAX_CARDS) {
			throw new UnsupportedOperationException(
					"Deal can't have more than " + MAX_CARDS + " cards!");
		}

		for (int i = 0; i < this.deal.size(); i++) {
			if (this.deal.get(i) == null) {
				this.deal.set(i, card);
				this.size++;
				return;
			}
		}


	}

	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public K getCardAtPosition(int index) {
		return this.deal.get(index);
	}

	@Override
	public K removeCardAtPosition(int index) {
		K getCard = this.deal.get(index);
		this.deal.set(index, null);
		this.size--;
		return getCard;
	}

	@Override
	public K removeCard(K card) {
		int index = this.deal.indexOf(card);
		if (index != -1) {
			return removeCardAtPosition(index);
		}
		else
			return null;
	}

	@Override
	public boolean isFull() {
		return getSize() >= MAX_CARDS;
	}
}
