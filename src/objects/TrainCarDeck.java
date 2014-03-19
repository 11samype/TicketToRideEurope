package objects;

public class TrainCarDeck extends AbstractDeck {

	public TrainCarDeck() {

		addCardsToDeckBeforeShuffle();

		shuffle();

	}

	private void addCardsToDeckBeforeShuffle() {

		this.cards.clear();

		for (TrainColor color : TrainColor.getAllColors()) {
			for (int j = 0; j < 12; j++) {
				this.cards.add(new TrainCarCard(color));
			}
		}

		this.cards.add(new TrainCarCard(TrainColor.RAINBOW));
		this.cards.add(new TrainCarCard(TrainColor.RAINBOW));

	}
}
