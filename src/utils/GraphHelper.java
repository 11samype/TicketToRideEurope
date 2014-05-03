package utils;

import gui.DrawableDestination;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.jgrapht.DirectedGraph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.Multigraph;
import org.jgrapht.graph.WeightedMultigraph;

import objects.Destination;
import objects.DestinationRoute;
import objects.abstracts.AbstractPlayer;
import objects.interfaces.IPlayer;
import objects.interfaces.IRoute;

public final class GraphHelper {

	public static final HashMap<Destination, List<IRoute>> ROUTE_LOOKUP = TrainRouteReader
			.getInstance().getGraph();
	public static final HashMap<String, DrawableDestination> DEST_LOC_LOOKUP = DestinationLocationReader
			.getInstance().getDestinations();

	public static boolean areConnected(Destination start, Destination end) {
			return getFullGraph().containsEdge(start, end);
	//		return getRouteBetween(start, end) != null;
		}

	public static IRoute getRouteBetween(Destination start, Destination end) {
			return getFullGraph().getEdge(start, end);

	//		List<IRoute> routes = ROUTE_LOOKUP.get(start);
	//		if (routes != null) {
	//			for (int i = 0; i < routes.size(); i++) {
	//				if (routes.get(i).getEnd().equals(end)) {
	//					return routes.get(i);
	//				}
	//			}
	//		}
	//
	//		return null;
		}

	public static boolean hasPlayerCompletedDestinationRoute(IPlayer player, DestinationRoute destRoute) {
		Multigraph<Destination, IRoute> playerGraph = getPlayerGraph(player);
		ConnectivityInspector<Destination, IRoute> connectionInspector = new ConnectivityInspector<Destination, IRoute>(playerGraph);
		return connectionInspector.pathExists(destRoute.getStart(), destRoute.getEnd());
	}

	public static Multigraph<Destination, IRoute> getPlayerGraph(IPlayer player) {
		Multigraph<Destination, IRoute> playerGraph = getDestinationGraph();
		for (IRoute route : player.getRoutes()) {
			if (!playerGraph.containsEdge(route)) {
				playerGraph.addEdge(route.getStart(), route.getEnd(), route);
//				playerGraph.setEdgeWeight(route, route.getLength());
			}
		}
		return playerGraph;
	}

	private static WeightedMultigraph<Destination, IRoute> _graphInstance;

	public static WeightedMultigraph<Destination, IRoute> getFullGraph() {
		if (_graphInstance == null) {
			_graphInstance = getDestinationGraph();
			for (Iterator<Destination> iterator = _graphInstance.vertexSet().iterator(); iterator.hasNext();) {
				Destination destVertex = iterator.next();
				for (IRoute routeFromDest : ROUTE_LOOKUP.get(destVertex)) {
					if (!_graphInstance.containsEdge(routeFromDest)) {
						_graphInstance.addEdge(destVertex, routeFromDest.getEnd(), routeFromDest);
//						_graphInstance.setEdgeWeight(routeFromDest, routeFromDest.getLength());
					}
				}
			}
		}

		return _graphInstance;
	}

	public static WeightedMultigraph<Destination, IRoute> getDestinationGraph() {
		WeightedMultigraph<Destination, IRoute> graph = new WeightedMultigraph<Destination, IRoute>(IRoute.class);
		for (Iterator<String> iterator = DEST_LOC_LOOKUP.keySet().iterator(); iterator.hasNext();) {
			Destination destVertex = DEST_LOC_LOOKUP.get(iterator.next());
			graph.addVertex(destVertex);
		}
		return graph;
	}

}
