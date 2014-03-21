package objects.abstracts;

import objects.Destination;
import objects.interfaces.IRoute;

public abstract class AbstractRoute implements IRoute {
	protected final Destination start;
	protected final Destination end;

	public AbstractRoute(Destination start, Destination end) {
		this.start = start;
		this.end = end;
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
}
