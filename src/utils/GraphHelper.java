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
}
