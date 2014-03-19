package objects;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public class TrainCarDeck extends AbstractDeck {

	public TrainCarDeck() {

		addCardsToDeckBeforeShuffle();

		shuffle();

	}

	private void addCardsToDeckBeforeShuffle() {

		this.cards.clear();

		for (Color color : TrainColor.getAllColors()) {
			for (int j = 0; j < 12; j++) {
				this.cards.add(new TrainCarCard(color));
			}
		}

		this.cards.add(new TrainCarCard(TrainColor.RAINBOW));
		this.cards.add(new TrainCarCard(TrainColor.RAINBOW));

	}
}
