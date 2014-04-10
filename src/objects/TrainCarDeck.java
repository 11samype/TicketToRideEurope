package objects;

import java.util.ArrayList;
import java.util.List;

import objects.abstracts.AbstractDeck;

public class TrainCarDeck extends AbstractDeck<TrainCarCard> {

	public TrainCarDeck() {
		super();
	}

	@Override
	protected void addCardsToDeck() {

		List<TrainCarCard> freshCards = new ArrayList<TrainCarCard>();

		for (TrainColor color : TrainColor.getAllColors()) {
			for (int j = 0; j < 12; j++) {
				freshCards.add(new TrainCarCard(color));
			}
		}

		freshCards.add(new TrainCarCard(TrainColor.RAINBOW));
		freshCards.add(new TrainCarCard(TrainColor.RAINBOW));

		populate(freshCards);

	}

	@Override
	public int numInDeck(TrainCarCard card) {

		return this.numInDeck(card.getColor());

	}

	public int numInDeck(final TrainColor color) {

		int count = 0;

		for (int i = 0; i < this.cards.size(); i++) {
			if (this.cards.get(i).getColor().equals(color)) {
				count++;
			}
		}

		return count;
	}
}
