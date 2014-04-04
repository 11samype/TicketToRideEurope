package managers;
import java.util.ArrayList;

import objects.Player;
import objects.interfaces.IPlayer;


/**
 * TODO Put here a description of what this class does.
 *
 * @author samynpd.
 *         Created Apr 3, 2014.
 */
public class TurnManager {

	private ArrayList<Player> players = new ArrayList<Player>();
	private int currentPlayerIndex;
	
	public TurnManager(final ArrayList<Player> players) {
		this.players = players;
		this.currentPlayerIndex = 0;
	}
	
	public Player nextPlayer() {
		this.currentPlayerIndex = (this.currentPlayerIndex + 1) % numPlayers();
		return this.players.get(this.currentPlayerIndex);
		
	}
	
	public int numPlayers() {
		return this.players.size();
	}
	
	public Player getCurrentPlayer() {
		return this.players.get(this.currentPlayerIndex);
	}
	
	public int getCurrentPlayerIndex() {
		return this.currentPlayerIndex;
	}
	
	public Player getPLayer(int index) {
		return this.players.get(index);
	}
}
