package objects;

import java.util.ArrayList;
import java.util.List;

import objects.interfaces.IPlayer;

public class Game {
	private List<IPlayer> players = new ArrayList<IPlayer>();
	private DestinationDeck destinationDeck;
	private TrainCarDeck trainCarDeck;
	private DiscardPile discardPile;

	public Game() {
		init();
	}

	public void init() {
		// TODO Clear the board, get number of players, deal the cards
		this.destinationDeck = new DestinationDeck();
		this.trainCarDeck = new TrainCarDeck();
		this.discardPile = new DiscardPile();
	}

	public void takeTurn(IPlayer player) {
		// TODO Have the player perform an action
	}

	public DestinationDeck getDestinationDeck() {
		return this.destinationDeck;
	}

	public TrainCarDeck getTrainCarDeck() {
		return this.trainCarDeck;
	}

	public DiscardPile getDiscardPile() {
		return this.discardPile;
	}

}
