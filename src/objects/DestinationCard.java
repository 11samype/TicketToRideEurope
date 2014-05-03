package objects;

import objects.abstracts.AbstractCard;

public class DestinationCard extends AbstractCard<DestinationRoute> {
	public DestinationCard(DestinationRoute route) {
		this.value = route;
	}

	public DestinationRoute getRoute() {
		return getValue();
	}

	public int getScore() {
		return getRoute().getScore();
	}

}
