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
		
		List<TrainCarCard> cards = new ArrayList<TrainCarCard>();
		
		for (int i = 0; i < 3; i++) {
			if (this.cards.isEmpty()) {
				reFillFromDicard();
			}
			cards.add(this.cards.remove(0));
		}
		
		return cards;
	}
	
	public void reFillFromDicard() {
		DiscardPile<TrainCarCard> discard = GameState.getCardManager().getDiscardPile();
		
		List<TrainCarCard> cardsList = new ArrayList<TrainCarCard>();
		
		while(!discard.isEmpty()) {
			cardsList.add((TrainCarCard)discard.draw());
		}
		
		populate(cards);
	}

}
