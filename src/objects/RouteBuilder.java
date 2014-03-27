package objects;

import objects.interfaces.IRoute;

public class RouteBuilder {

	public static final String TRAIN = "train";
	public static final String TUNNEL = "tunnel";
	public static final String FERRY = "ferry";

	private Destination start;
	private Destination end;
	private final int length;
	private TrainColor color;
	private int locomotiveCount;

	public RouteBuilder(Destination start, Destination end, int length) {
		if (start.equals(end))
			throw new IllegalArgumentException(
					"A route cannot be made to the same destination");
		this.start = start;
		this.end = end;
		if (length <= 0)
			throw new IllegalArgumentException(
					"Length of a route must be positive");
		this.length = length;
	}

	public RouteBuilder withColor(TrainColor color) {
		this.color = color;
		return this;
	}

	public RouteBuilder withLocomotiveCount(int locoCount) {
		this.locomotiveCount = locoCount;
		return this;
	}

	public IRoute build(String type) {
		if (color == null)
			color = TrainColor.RAINBOW;
		if (type.equalsIgnoreCase(TRAIN)) {
			return new TrainRoute(start, end, color, length);
		} else if (type.equalsIgnoreCase(FERRY)) {
			return new FerryRoute(start, end, length, locomotiveCount);
		} else if (type.equalsIgnoreCase(TUNNEL)) {
			return new TunnelRoute(start, end, color, length);
		} else {
			throw new IllegalArgumentException("Incorrect type name");
		}
	}

	public RouteBuilder reverseDirection() {
		Destination temp = this.end;
		this.end = this.start;
		this.start = temp;
		return this;
	}
}
