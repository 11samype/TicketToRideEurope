package objects.abstracts;

import objects.Destination;
import objects.interfaces.IRoute;

public abstract class AbstractRoute implements IRoute {
	protected final Destination start;
	protected final Destination end;
	protected final int length;

	public AbstractRoute(Destination start, Destination end, int length) {
		this.start = start;
		this.end = end;
		if (length <= 0)
			throw new IllegalArgumentException(
					"The length of a route must be positive");
		this.length = length;
	}

	@Override
	public String toString() {
		return String.format("%s-%s", this.start, this.end);
	}

	@Override
	public Destination getStart() {
		return this.start;
	}

	@Override
	public Destination getEnd() {
		return this.end;
	}

	@Override
	public int getLength() {
		return this.length;
	}

	public int getScore() {
		switch (length) {
		case 1:
		case 2:
			return length;
		case 3:
			return 4;
		case 4:
			return 7;
		case 6:
			return 13;
		case 8:
			return 21;
		default:
			break;
		}
		return length;

	}
}
