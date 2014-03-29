package objects;

import java.util.ArrayList;
import java.util.List;

import objects.abstracts.AbstractDeck;

public class DiscardPile extends AbstractDeck<TrainCarCard> {

	public DiscardPile() {
		addCardsToDeck(); // starts empty
	}

	public void add(TrainCarCard card) {
		this.cards.add(card);
	}

	public List<TrainCarCard> pickup() {
		List<TrainCarCard> cardsToReturn = new ArrayList<TrainCarCard>(
				this.cards);
		this.cards.clear();
		return cardsToReturn;
	}

	@Override
	protected void addCardsToDeck() {
		populate(new ArrayList<TrainCarCard>());
	}
}
