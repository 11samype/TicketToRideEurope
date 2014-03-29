package objects;

import objects.abstracts.AbstractRoute;

public class DestinationRoute extends AbstractRoute {

	public DestinationRoute(Destination start, Destination end) {
		super(start, end, 1);
	}

	public DestinationRoute(Destination start, Destination end, int score) {
		super(start, end, score);
	}

	public int getScore() {
		return this.length;
	}

	@Override
	public String toString() {
		return String.format("%s (%d)", super.toString(), getScore());
	}

}
