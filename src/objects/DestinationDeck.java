package objects;

import java.util.ArrayList;
import java.util.List;

import objects.abstracts.AbstractDeck;

public class DestinationDeck extends AbstractDeck<DestinationCard> {

	@Override
	protected void addCardsToDeck() {

		List<DestinationCard> freshCards = new ArrayList<DestinationCard>();

		// TODO Get the destinations

		populate(freshCards);
	}

}
