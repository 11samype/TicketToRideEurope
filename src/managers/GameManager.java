package managers;

import objects.TrainCarCard;


/**
 * TODO Put here a description of what this class does.
 *
 * @author samynpd.
 *         Created Apr 3, 2014.
 */
public class GameManager {

	private TurnManager turnManager;
	private CardManager cardManager;
	
	public GameManager(CardManager cardManager, TurnManager turnManager) {
		this.cardManager = cardManager;
		this.turnManager = turnManager;
	}
	
	public TrainCarCard getDeelCard(int index) {
		return this.cardManager.getDeelCard(index);
	}
	
	public int getSizeOfDeck() {
		return this.cardManager.getSizeOfDeck();
	}
	
	public TrainCarCard drawFromDeck() {
		return this.cardManager.drawFromDeck();
	}
	
}
