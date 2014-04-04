package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import managers.TurnManager;
import objects.DestinationCard;
import objects.DestinationDeck;
import objects.Player;
import objects.TrainCar;
import objects.TrainCarCard;
import objects.TrainCarDeck;
import objects.interfaces.IPlayer;

import org.junit.Test;

/**
 * TODO Put here a description of what this class does.
 *
 * @author samynpd.
 *         Created Apr 4, 2014.
 */
public class TurnManagerTest {

	@Test
	public void testNextPlayer() {
		ArrayList<Player> players = new ArrayList<Player>();
		
		for (int i = 0; i < 5; i++) {
			players.add(new Player());
		}
		
		TurnManager turnManager =  new TurnManager(players);
		
		assertEquals(turnManager.getCurrentPlayerIndex(), 0);
		
		turnManager.nextPlayer();
		
		assertEquals(turnManager.getCurrentPlayerIndex(), 1);
		
		turnManager.nextPlayer();
		
		assertEquals(turnManager.getCurrentPlayerIndex(), 2);
		
		turnManager.nextPlayer();
		
		assertEquals(turnManager.getCurrentPlayerIndex(), 3);
		
		turnManager.nextPlayer();
		
		assertEquals(turnManager.getCurrentPlayerIndex(), 4);
		
		turnManager.nextPlayer();
		
		assertEquals(turnManager.getCurrentPlayerIndex(), 0);
	}

}
