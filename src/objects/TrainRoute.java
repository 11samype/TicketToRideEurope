package objects;

import objects.abstracts.AbstractColorableRoute;

public class TrainRoute extends AbstractColorableRoute {

	public TrainRoute(Destination start, Destination end) {
		super(start, end);
	}

	public TrainRoute(Destination start, Destination end, TrainColor color) {
		super(start, end, color);
	}

}
