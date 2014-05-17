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

	@Override
	public int getScore() {
		switch (this.length) {
		case 1:
		case 2:
			return this.length;
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
		return -1;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + length;
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	public boolean isSameAs(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AbstractRoute))
			return false;
		AbstractRoute other = (AbstractRoute) obj;
		return (this.length == other.length)
				&& (this.start != null && this.start.equals(other.start))
				&& (this.end != null && this.end.equals(other.end));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AbstractRoute))
			return false;
		AbstractRoute other = (AbstractRoute) obj;
		if (this.length != other.length)
			return false;
		if (this.start == null) {
			if (other.start != null)
				return false;
		} else if (!(this.start.equals(other.start) || this.start
				.equals(other.end)))
			return false;
		if (this.end == null) {
			if (other.end != null) // need to finish in train route test
				return false;
		} else if (!(this.end.equals(other.end) || this.end.equals(other.start)))
			return false;
		return true;

	}
}
