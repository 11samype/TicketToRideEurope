package objects.abstracts;

import java.util.ArrayList;

import objects.NullTrainCarCard;
import objects.interfaces.ICard;
import objects.interfaces.IDeal;

public abstract class AbstractDeal<K extends ICard> implements IDeal<K> {

	protected ArrayList<K> deal = new ArrayList<K>();
	protected static final int MAX_DEALT_CARDS = 5;

	@Override
	public void addCard(K card) {
		if (this.deal.size() > MAX_DEALT_CARDS) {
			throw new UnsupportedOperationException(
					"Deal can't have more than " + MAX_DEALT_CARDS + " cards!");
		}
		
		for (int i = 0; i < this.deal.size(); i++) {
			if (this.deal.get(i) == null) {
				this.deal.set(i, card);
				return;
			}
		}


	}

	public int getSize() {
		int num = 0;
		for (int i = 0; i < this.deal.size(); i++) {
			if (deal.get(i) != null) {
				num++;
			} 
		}
		
		return num;
	}

	public K getCardAtPosition(int index) {
		return this.deal.get(index);
	}

	@Override
	public K removeCardAtPosition(int index) {
		K getCard = this.deal.get(index);
		this.deal.set(index, null);
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
	public boolean isDealFull() {
		return (getSize() >= MAX_DEALT_CARDS);
	}
}
