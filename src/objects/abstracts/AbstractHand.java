package objects.abstracts;

import java.util.ArrayList;

import objects.interfaces.ICard;
import objects.interfaces.IHand;

/**
 * TODO Put here a description of what this class does.
 * 
 * @author samynpd. Created Mar 28, 2014.
 * @param <K>
 */
public abstract class AbstractHand<K extends ICard> implements IHand<K> {

	protected ArrayList<K> hand = new ArrayList<K>();

	@Override
	public void removeCard(K card) {
		this.hand.remove(card);
	}

	@Override
	public void removeCard(int index) {
		this.hand.remove(index);
	}

	@Override
	public void addCard(K card) {
		this.hand.add(card);
	}

	@Override
	public void playCard(K card) {
		// TODO: Implement
	}

	@Override
	public void playCard(int index) {
		// TODO: Implement
	}
}
