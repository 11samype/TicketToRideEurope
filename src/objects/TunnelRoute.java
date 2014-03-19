package objects;

public class TunnelRoute extends TrainRoute {

	public TunnelRoute(Destination start, Destination end) {
		super(start, end);
	}

	public TunnelRoute(Destination start, Destination end, TrainColor color) {
		super(start, end, color);
	}

}
