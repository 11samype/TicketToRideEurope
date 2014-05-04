package objects;

public class Destination {
	protected String name;
	protected Player ownedBy;

	public Destination(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public boolean hasStation() {
		return ownedBy != null;
	}

	public boolean buildStation(Player player) {
		if (!this.hasStation()) {
			boolean built = player.placeStationOnDestination(this);
			if (built) {
				ownedBy = player;
				return true;
			}
			else
				throw new UnsupportedOperationException("Player has no stations left!");
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Destination))
			return false;
		Destination other = (Destination) obj;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		return true;
	}
}
