import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import objects.GameState;
import objects.Player;
import objects.GameState.CardManager;
import objects.GameState.TurnManager;
import objects.TrainCarCard;
import objects.TrainRoute;
import objects.interfaces.IPlayer;

import org.junit.Test;

import utils.DestinationLocationReader;
import utils.TrainRouteReader;

public class GameStateTest {

	@Test
	public void testInitGame() {
		GameState game = GameState.getInstance();
		game.init();
		CardManager cardManager = game.getCardManager();
		assertNotNull(game);
		assertEquals(46, cardManager.getDestinationDeck().size());
		assertEquals(105, cardManager.getTrainCarDeck().size());

		assertTrue(cardManager.getDealCards().isDealFull());
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
		GameState game = GameState.getInstance().withPlayers(players);

		assertSame(game.getCurrentPlayer(), players.get(0));

		for (int i = 1; i < players.size(); i++) {
			game.takeTurn();
			assertSame(game.getCurrentPlayer(), players.get(i));
		}

		game.takeTurn();
		assertSame(game.getCurrentPlayer(), players.get(0));
	}

	@Test
	public void testgetPlayers() {
		List<IPlayer> players = new ArrayList<IPlayer>();
		for (int i = 0; i < 5; i++) {
			players.add(new Player());
		}
		GameState game = GameState.getInstance().withPlayers(players);
		assertEquals(game.getPlayers(), players);
	}
	
	@Test
	public void testGetDealCard() {

		GameState game = GameState.getInstance();
		
		TrainCarCard dealCard = game.getCardManager().getDealCard(0);
		assertTrue(dealCard instanceof TrainCarCard);
		assertNotNull(dealCard);
	}

}
