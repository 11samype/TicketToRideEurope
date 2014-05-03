package utils;

import objects.Destination;

public class DestinationSearchNode {
	private boolean visited = false;
	private final Destination dest;

	public DestinationSearchNode(Destination dest) {
		this.dest = dest;
	}

	public boolean wasVisited() {
		return visited;
	}

	public void visit() {
		this.visited = true;
	}

	public Destination getDestination() {
		return dest;
	}
}