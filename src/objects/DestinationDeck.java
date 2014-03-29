package objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import objects.abstracts.AbstractDeck;
import utils.DestinationReader;

public class DestinationDeck extends AbstractDeck<DestinationCard> {

	public DestinationDeck() {
		super();
	}

	@Override
	protected void addCardsToDeck() {
		List<DestinationCard> freshCards = new ArrayList<DestinationCard>();

		DestinationReader destReader = new DestinationReader();
		destReader.run();
		Set<DestinationRoute> destinations = destReader.getRoutes();

		for (DestinationRoute route : destinations) {
			freshCards.add(new DestinationCard(route));
		}

		populate(freshCards);
	}

}
