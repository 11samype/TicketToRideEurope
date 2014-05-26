import static org.junit.Assert.*;
import gui.interfaces.IRefreshable;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import objects.Player;
import objects.TrainCarCard;
import objects.interfaces.IPlayer;

import org.junit.Test;

import utils.DestinationLocationReader;
import utils.GameState;
import utils.TrainRouteReader;
import utils.GameState.CardManager;

public class GameStateTest {
	
	private static final int DESTINATION_CARDS = 46;
	private static final int TRAINCAR_CARDS = 105;
	
	private class RefreshableObj implements IRefreshable {

		public boolean refreshed = false;
		
		public void reset() {
			this.refreshed = false;

		}

		@Override
		public void refresh() {
			this.refreshed = true;
			
		}
	}
	
	private RefreshableObj fakeRefresh = new RefreshableObj();
	
	@Test
	public void testInitGame() {
		
		GameState.getInstance();
		GameState game = GameState.withPlayers(new ArrayList<Player>());
		assertNotNull(game);
		assertTrue(GameState.getPlayers().isEmpty());

	}

	@Test
	public void testInitCardManager() {
		CardManager cardManager = GameState.getCardManager();
		int destinationsLeft = (DESTINATION_CARDS - 6) - 3*GameState.getPlayers().size();
		int traincardsLeft = TRAINCAR_CARDS - 4*GameState.getPlayers().size();
		assertEquals(destinationsLeft, cardManager.getDestinationDeck().size());
		assertEquals(traincardsLeft, cardManager.getTrainCarDeck().size());

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
		List<Player> players = new ArrayList<Player>();
		for (int i = 0; i < 5; i++) {
			players.add(new Player());
		}

		GameState.withPlayers(players);
		GameState.withGUI(fakeRefresh);

		assertFalse(fakeRefresh.refreshed);
		assertSame(GameState.getCurrentPlayer(), players.get(0));
		
		for (int i = 1; i < players.size(); i++) {
			GameState.takeTurn();
			assertTrue(fakeRefresh.refreshed);
			fakeRefresh.reset();
			assertSame(GameState.getCurrentPlayer(), players.get(i));
		}

		assertFalse(fakeRefresh.refreshed);
		GameState.takeTurn();
		assertTrue(fakeRefresh.refreshed);
		
		assertSame(GameState.getCurrentPlayer(), players.get(0));
	}

	@Test
	public void testgetPlayers() {
		List<Player> players = new ArrayList<Player>();
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
	
	@Test
	public void testLogGameData() {
		ByteArrayOutputStream log_baos = new ByteArrayOutputStream();
		ByteArrayOutputStream empty_baos = new ByteArrayOutputStream();
		PrintStream logStream = new PrintStream(log_baos);
		PrintStream blankStream = new PrintStream(empty_baos);
		PrintStream backUp = System.out;
		
		System.setOut(logStream);
		String printed = log_baos.toString();
		String str = empty_baos.toString();
		assertTrue(printed.isEmpty());
		assertTrue(str.isEmpty());

		try {
			GameState.initializeGameData(true);
			logStream.flush();
			
			printed = log_baos.toString();
			assertFalse(printed.isEmpty());
			
			// run again without logging
			System.setOut(blankStream);
			GameState.initializeGameData(false);
			blankStream.flush();
			printed = empty_baos.toString();
			assertTrue(printed.isEmpty());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.setOut(backUp);
			logStream.close();
		}
	}

}
