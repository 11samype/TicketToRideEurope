package objects;

import objects.abstracts.AbstractHand;

public class TrainCarHand extends AbstractHand<TrainCarCard> {

	public TrainCarHand() {
		super();
	}

	public int numInHand(TrainCarCard card) {
		return numInHand(card.getColor());
	}

	public int numInHand(final TrainColor color) {
		int count = 0;

		for (int i = 0; i < this.hand.size(); i++) {
			if (this.hand.get(i).getColor().equals(color)) {
				count++;
			}
		}

		return count;
	}

}
