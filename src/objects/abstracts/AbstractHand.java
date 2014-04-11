package objects.abstracts;

import java.util.ArrayList;

import objects.TrainCarCard;
import objects.TrainColor;
import objects.interfaces.ICard;
import objects.interfaces.IHand;

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
	public int size() {
		return this.hand.size();
	}
	
	@Override
	public K getCard(int index) {
		
		return this.hand.get(index);
	}

}
