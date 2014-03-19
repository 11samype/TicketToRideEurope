package objects;

public class DestinationRoute extends AbstractRoute {
	private int weight;

	public DestinationRoute(Destination start, Destination end) {
		super(start, end);
		this.weight = 1;
	}

	public DestinationRoute(Destination start, Destination end, int weight) {
		super(start, end);
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return String.format("%s (%d)", super.toString(), this.weight);
	}
}
