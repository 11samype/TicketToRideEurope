package objects;

import objects.abstracts.AbstractColorableRoute;

public class TrainRoute extends AbstractColorableRoute {


	public TrainRoute(Destination start, Destination end, int length) {
		super(start, end, length);
	}

	public TrainRoute(Destination start, Destination end, TrainColor color, int length) {
		super(start, end, color, length);
	}

}
