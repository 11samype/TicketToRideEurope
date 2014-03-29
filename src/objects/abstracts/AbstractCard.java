package objects.abstracts;

import objects.interfaces.ICard;

public abstract class AbstractCard<V> implements ICard {
	protected V value;

	protected V getValue() {
		return value;
	}

}
