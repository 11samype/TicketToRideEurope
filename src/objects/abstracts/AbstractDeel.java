package objects.abstracts;

import java.util.ArrayList;

import objects.interfaces.ICard;
import objects.interfaces.IDeel;

/**
 * TODO Put here a description of what this class does.
 *
 * @author samynpd.
 *         Created Mar 28, 2014.
 */
public abstract class AbstractDeel<K extends ICard> implements IDeel<K> {

	protected ArrayList<K> deel = new ArrayList<K>();

	public void addCard(K card) {
		
		if(this.deel.size() >= 5) {
			throw new UnsupportedOperationException("Deel can't have more than 5 cards!");
		}
		
		this.deel.add(card);
		
	}
	
	public void removeCard(K card) {
		this.deel.remove(card);
	}

}
