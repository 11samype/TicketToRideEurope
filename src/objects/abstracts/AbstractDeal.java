package objects.abstracts;

import java.util.ArrayList;

import objects.interfaces.ICard;
import objects.interfaces.IDeal;

/**
 * TODO Put here a description of what this class does.
 * 
 * @author samynpd. Created Mar 28, 2014.
 */
public abstract class AbstractDeal<K extends ICard> implements IDeal<K> {

	protected ArrayList<K> deal = new ArrayList<K>();

	@Override
	public void addCard(K card) {

		if (this.deal.size() >= 5) {
			throw new UnsupportedOperationException(
					"Deal can't have more than 5 cards!");
		}

		this.deal.add(card);

	}
	
	@Override
	public boolean full() {
		return (this.deal.size() == 5);
	}

	@Override
	public K removeCard(K card) {
		this.deal.remove(card);
		return card;
	}
	
	@Override
	public K removeCard(int index) {
		return this.deal.remove(index);
	}
	
	public K getCard(int index) {
		return this.deal.get(index);
	}

}
