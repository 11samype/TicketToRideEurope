package objects;

import java.util.ArrayList;
import java.util.List;

import objects.abstracts.AbstractDeck;

public class TrainCarDeck extends AbstractDeck<TrainCarCard> {

	public TrainCarDeck() {
		addCardsToDeck();
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
}
