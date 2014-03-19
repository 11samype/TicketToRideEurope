package objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractDeck implements IDeck {

	protected List<ICard> cards = new ArrayList<ICard>();

	@Override
	public void shuffle() {
		Collections.shuffle(cards);
	}

	@Override
	public void populate(List<ICard> cardList) {
		this.cards.clear();
		this.cards.addAll(cardList);
		shuffle();
	}

	@Override
	public ICard draw() {
		if (size() <= 0)
			throw new IndexOutOfBoundsException("The deck is empty!");
		return this.cards.remove(0);
	}

	@Override
	public List<ICard> getCards() {
		return this.cards;
	}

	@Override
	public int size() {
		return this.cards.size();
	}

	@Override
	public boolean isEmpty() {
		return this.cards.isEmpty();
	}

}
