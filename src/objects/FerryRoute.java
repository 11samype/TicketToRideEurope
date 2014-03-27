package objects;

import objects.abstracts.AbstractRoute;

public class FerryRoute extends AbstractRoute {

	private int locomotiveCount;

	public FerryRoute(Destination start, Destination end, int length,
			int locomotiveCount) {
		super(start, end, length);
		this.locomotiveCount = locomotiveCount;
	}

	public int getLocomotiveCount() {
		return locomotiveCount;
	}

}
