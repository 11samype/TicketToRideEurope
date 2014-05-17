package objects;

import java.util.Iterator;

import objects.abstracts.AbstractHand;
import utils.GameState;

public class TrainCarHand extends AbstractHand<TrainCarCard> {

	public TrainCarHand() {
		super();
	}

	public int numInHand(TrainCarCard card) {
		return numInHand(card.getColor());
	}

	public int numInHand(TrainColor color) {
		int count = 0;

		for (TrainCarCard card : this.hand) {
			if (card != null && card.getColor().equals(color)) {
				count++;
			}
		}

		return count;
	}

	public void removeCard(TrainColor color) {
		for (Iterator<TrainCarCard> iter = this.hand.iterator(); iter.hasNext(); )
		 {
			TrainCarCard card = iter.next();
			if (card.getColor().equals(color)) {
				GameState.getCardManager().discard(card);
				iter.remove();
				break;
			}
		}
	}

}
