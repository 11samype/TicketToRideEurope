import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import objects.GameState;
import objects.GameState.CardManager;
import objects.Player;
import objects.TrainCarCard;
import objects.interfaces.IPlayer;

import org.junit.Test;

import utils.DestinationLocationReader;
import utils.TrainRouteReader;

public class GameStateTest {

	@Test
	public void testInitGame() {
		GameState game = GameState.getInstance();
		assertNotNull(game);

	}

	@Test
	public void testInitCardManager() {
		CardManager cardManager = GameState.getCardManager();
		assertEquals(46, cardManager.getDestinationDeck().size());
		assertEquals(105, cardManager.getTrainCarDeck().size());

		assertTrue(cardManager.getDealCards().isFull());
		assertEquals(5, cardManager.getDealCards().getSize());

		assertEquals(0, cardManager.getDiscardPile().size());
	}

	@Test
	public void testDestinationReader() {
		TrainRouteReader routeReader = TrainRouteReader.getInstance();
		int numberOfConnectedCities = routeReader.getGraph().size();

		DestinationLocationReader destReader = DestinationLocationReader.getInstance();
		int numberOfCities = destReader.getDestinations().size();

		assertEquals(47, numberOfCities);
		assertEquals(numberOfCities, numberOfConnectedCities );
	}

	@Test
	public void testNextPlayer() {
		List<IPlayer> players = new ArrayList<IPlayer>();
		for (int i = 0; i < 5; i++) {
			players.add(new Player());
		}

		GameState.withPlayers(players);

		assertSame(GameState.getCurrentPlayer(), players.get(0));

		for (int i = 1; i < players.size(); i++) {
			GameState.takeTurn();
			assertSame(GameState.getCurrentPlayer(), players.get(i));
		}

		GameState.takeTurn();
		assertSame(GameState.getCurrentPlayer(), players.get(0));
	}

	@Test
	public void testgetPlayers() {
		List<IPlayer> players = new ArrayList<IPlayer>();
		for (int i = 0; i < 5; i++) {
			players.add(new Player());
		}
		GameState.withPlayers(players);
		assertEquals(GameState.getPlayers(), players);
	}

	@Test
	public void testGetDealCard() {
		TrainCarCard dealCard = GameState.getCardManager().getDealCard(0);
		assertNotNull(dealCard);
	}

}
