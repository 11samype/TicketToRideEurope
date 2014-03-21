package objects;

import objects.abstracts.AbstractRoute;

public class DestinationRoute extends AbstractRoute {
	private int score;

	public DestinationRoute(Destination start, Destination end) {
		super(start, end);
		this.score = 1;
	}

	public DestinationRoute(Destination start, Destination end, int weight) {
		super(start, end);
		this.score = weight;
	}

	public int getScore() {
		return score;
	}

	@Override
	public String toString() {
		return String.format("%s (%d)", super.toString(), this.score);
	}
}
