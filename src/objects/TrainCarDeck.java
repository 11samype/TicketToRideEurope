package objects;

import java.util.ArrayList;
import java.util.List;

import objects.abstracts.AbstractDeck;
import utils.GameState;

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

	public List<TrainCarCard> drawTopThree() {
		
		// for tunnels, must remember to placee cards into discard after use!!
		
		List<TrainCarCard> drawnCards = new ArrayList<TrainCarCard>();
		
		for (int i = 0; i < 3; i++) {
			if (this.cards.isEmpty()) {
				reFillFromDicard();
			}
			drawnCards.add(this.cards.remove(0));
		}
		
		return drawnCards;
	}

	public void reFillFromDicard() {
		populate(GameState.getCardManager().getDiscardPile().pickup());
		
	}

}
