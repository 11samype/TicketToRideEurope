package utils;

import gui.DrawableDestination;

import java.util.HashMap;
import java.util.List;

import objects.Destination;
import objects.DestinationRoute;
import objects.abstracts.AbstractPlayer;
import objects.interfaces.IRoute;

public final class GraphHelper {

	public static final HashMap<Destination, List<IRoute>> ROUTE_LOOKUP = TrainRouteReader
			.getInstance().getGraph();
	public static final HashMap<String, DrawableDestination> DEST_LOC_LOOKUP = DestinationLocationReader
			.getInstance().getDestinations();


	public static boolean hasPlayerCompletedDestinationRoute(
			AbstractPlayer player, DestinationRoute route) {
		return false;

		// TODO Depth-first search routes
				// DestinationSearchNode start = new
				// DestinationSearchNode(destRoute.getStart());
				// Destination end = destRoute.getEnd();
				//
				// Stack<DestinationSearchNode> stack = new
				// Stack<DestinationSearchNode>();
				// // search from destination start
				// stack.push(start);
				// start.visit();
				//
				// // try to find destination end by searching through players connected
				// routes
				// while (!stack.isEmpty()) {
				// DestinationSearchNode searchFrom = stack.peek();
				// List<IRoute> routesFromStart =
				// ROUTE_LOOKUP.get(searchFrom.getDestination());
				// for (IRoute possibleRoute : routesFromStart) {
				// if (this.routes.contains(possibleRoute)) {
				// // here we know the player can reach another city
				// DestinationSearchNode next = new
				// DestinationSearchNode(possibleRoute.getEnd());
				// next.visit();
				// stack.push(next);
				// }
				//
				// }
				// // return true; // somewhere
				// }
				//
				//
				// return false;
	}

	public static boolean areConnected(Destination start, Destination end) {
		return getRouteBetween(start, end) != null;
	}

	public static IRoute getRouteBetween(Destination start, Destination end) {
		List<IRoute> routes = ROUTE_LOOKUP.get(start);
		if (routes != null) {
			for (int i = 0; i < routes.size(); i++) {
				if (routes.get(i).getEnd().equals(end)) {
					return routes.get(i);
				}
			}
		}
		return null;
	}

	public class DestinationSearchNode {
		private boolean visited = false;
		private final Destination dest;

		public DestinationSearchNode(Destination dest) {
			this.dest = dest;
		}

		private boolean wasVisited() {
			return visited;
		}

		private void visit() {
			this.visited = true;
		}

		public Destination getDestination() {
			return dest;
		}
	}
}
