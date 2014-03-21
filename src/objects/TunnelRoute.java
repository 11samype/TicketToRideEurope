package objects;

import objects.abstracts.AbstractColorableRoute;

public class TunnelRoute extends AbstractColorableRoute {

	public TunnelRoute(Destination start, Destination end) {
		super(start, end);
	}

	public TunnelRoute(Destination start, Destination end, TrainColor color) {
		super(start, end, color);
	}

}
