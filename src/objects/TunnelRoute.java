package objects;

import objects.abstracts.AbstractColorableRoute;

public class TunnelRoute extends AbstractColorableRoute {

	public TunnelRoute(Destination start, Destination end, int length) {
		super(start, end, length);
	}

	public TunnelRoute(Destination start, Destination end, TrainColor color,
			int length) {
		super(start, end, color, length);
	}

}
