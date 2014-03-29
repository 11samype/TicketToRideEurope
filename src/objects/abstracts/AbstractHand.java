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

	public void removeCard(K card) {
		this.hand.remove(card);
	}

	public void removeCard(int index) {
		this.hand.remove(index);
	}

	public void addCard(K card) {
		this.hand.add(card);
	}

	public void playCard(K card) {
		// TODO: Implement
	}

	public void playCard(int index) {
		// TODO: Implement
	}
}
