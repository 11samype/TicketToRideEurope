

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import objects.Destination;
import objects.Player;
import objects.interfaces.IRoute;

import org.jgrapht.graph.Multigraph;
import org.jgrapht.graph.WeightedMultigraph;
import org.junit.Before;
import org.junit.Test;

import utils.GraphHelper;

public class GraphHelperTest {

	private int numDestinations;
	private int numRoutes;

	@Before
	public void setUp() throws Exception {
		numDestinations = GraphHelper.DEST_LOC_LOOKUP.size();
		numRoutes = 101 - (22 / 2);
	}

	@Test
	public void testAreConnected() {
		Destination madrid = new Destination("Madrid");
		Destination rome = new Destination("Roma");
		List<IRoute> routesFromMadrid = GraphHelper.ROUTE_LOOKUP.get(madrid);
		for (IRoute route : routesFromMadrid) {
			assertTrue(GraphHelper.areAdjacent(madrid, route.getEnd()));
		}

		assertFalse(GraphHelper.areAdjacent(madrid, rome));
	}

	@Test
	public void testGetDirectRouteBetween() {
		Destination madrid = new Destination("Madrid");
		Destination rome = new Destination("Roma");
		List<IRoute> routesFromMadrid = GraphHelper.ROUTE_LOOKUP.get(madrid);
		List<IRoute> test = new ArrayList<IRoute>();
		for (IRoute route : routesFromMadrid) {
			IRoute connection = GraphHelper.getAdjecentRouteBetween(madrid, route.getEnd());
			test.add(connection);
		}
		assertEquals(routesFromMadrid, test);

		assertNull(GraphHelper.getAdjecentRouteBetween(madrid, rome));
	}

	@Test
	public void testHasPlayerCompletedDestinationRoute() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDestinationGraph() {
		WeightedMultigraph<Destination, IRoute> graph = GraphHelper.getDestinationGraph();
		assertTrue(graph.edgeSet().isEmpty());
		assertEquals(numDestinations, graph.vertexSet().size());
	}

	@Test
	public void testGetPlayerGraph() {
		Player p = new Player();
		Multigraph<Destination, IRoute> graph = GraphHelper.getPlayerGraph(p);
		assertTrue(graph.edgeSet().isEmpty());

		fail("Not yet fully implemented");
	}

	@Test
	public void testGetFullGraph() {
		WeightedMultigraph<Destination, IRoute> graph = GraphHelper.getFullGraph();
		assertEquals(numDestinations, graph.vertexSet().size());
		assertEquals(numRoutes, graph.edgeSet().size());
	}


}
