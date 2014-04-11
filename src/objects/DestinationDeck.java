package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import objects.abstracts.AbstractDeck;
import utils.DestinationCardReader;

public class DestinationDeck extends AbstractDeck<DestinationCard> {

	public DestinationDeck() {
		super();
	}

	@Override
	protected void addCardsToDeck() {
		List<DestinationCard> freshCards = new ArrayList<DestinationCard>();

		DestinationCardReader reader = DestinationCardReader.getInstance();
		Set<DestinationRoute> destinations = reader.getRoutes();

		for (DestinationRoute route : destinations) {
			freshCards.add(new DestinationCard(route));
		}

		populate(freshCards);
	}

}
