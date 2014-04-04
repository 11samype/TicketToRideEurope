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

	private ArrayList<IPlayer> players = new ArrayList<IPlayer>();
	private int currentPlayerIndex;
	
	public TurnManager(final ArrayList<IPlayer> players) {
		this.players = players;
		this.currentPlayerIndex = 0;
	}
	
	public IPlayer nextPlayer() {
		this.currentPlayerIndex = (this.currentPlayerIndex + 1) % numPlayers();
		return this.players.get(this.currentPlayerIndex);
		
	}
	
	public int numPlayers() {
		return this.players.size();
	}
	
	public IPlayer getCurrentPlayer() {
		return this.players.get(this.currentPlayerIndex);
	}
	
	public int getCurrentPlayerIndex() {
		return this.currentPlayerIndex;
	}
}
