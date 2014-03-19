package objects;

public class TrainRoute extends AbstractRoute {

	protected final TrainColor color;

	public TrainRoute(Destination start, Destination end) {
		super(start, end);
		this.color = TrainColor.RAINBOW;
	}

	public TrainRoute(Destination start, Destination end, TrainColor color) {
		super(start, end);
		this.color = color;
	}

}
