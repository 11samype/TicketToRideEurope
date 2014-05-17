package utils;

import gui.drawables.DrawableDestination;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import objects.Destination;
import objects.DestinationRoute;
import objects.interfaces.IPlayer;
import objects.interfaces.IRoute;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.Multigraph;
import org.jgrapht.graph.WeightedMultigraph;

public final class GraphHelper {

	public static final HashMap<Destination, List<IRoute>> ROUTE_LOOKUP = TrainRouteReader
			.getInstance().getGraph();
	public static final HashMap<String, DrawableDestination> DEST_LOC_LOOKUP = DestinationLocationReader
			.getInstance().getDestinations();

	public static boolean areAdjacent(Destination start, Destination end) {
			return getFullGraph().containsEdge(start, end);
	//		return getRouteBetween(start, end) != null;
		}

	public static IRoute getAdjecentRouteBetween(Destination start, Destination end) {
			return getFullGraph().getEdge(start, end);
		}

	public static boolean hasPlayerCompletedDestinationRoute(IPlayer player, DestinationRoute destRoute) {
		return pathExistsFrom(getPlayerGraph(player), destRoute.getStart(), destRoute.getEnd());
	}

	public static boolean pathExistsFrom(Destination start, Destination end) {
		return pathExistsFrom(getFullGraph(), start, end);
	}

	public static <V extends Destination, E extends IRoute> boolean pathExistsFrom(UndirectedGraph<V, E> graph, V start, V end) {
		ConnectivityInspector<V, E> connectionInspector = new ConnectivityInspector<V, E>(graph);
//		System.out.println("**Connection Test**\n"+start.getName() + "<->" + end.getName());
		return connectionInspector.pathExists(start, end);
	}

	public static Multigraph<Destination, IRoute> getPlayerGraph(IPlayer player) {
		Multigraph<Destination, IRoute> playerGraph = getDestinationGraph();
		for (IRoute route : player.getRoutes()) {
				playerGraph.addEdge(route.getStart(), route.getEnd(), route);
//				playerGraph.setEdgeWeight(route, route.getLength());
		}
		return playerGraph;
	}

	private static WeightedMultigraph<Destination, IRoute> _graphInstance;

	public static WeightedMultigraph<Destination, IRoute> getFullGraph() {
		if (_graphInstance == null) {
			_graphInstance = getDestinationGraph();
			for (Iterator<Destination> iterator = _graphInstance.vertexSet().iterator(); iterator.hasNext();) {
				Destination start = iterator.next();
				for (IRoute routeFromStart : ROUTE_LOOKUP.get(start)) {
					Destination end = routeFromStart.getEnd();
					if (!(_graphInstance.containsEdge(start, end) && _graphInstance.containsEdge(end, start))) {
						_graphInstance.addEdge(start, end, routeFromStart);
//						_graphInstance.setEdgeWeight(routeFromDest, routeFromDest.getLength());
					} else {
						// TODO: Handle double routes
//						System.out.println(routeFromStart);
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
