package objects;

import java.util.List;

import objects.abstracts.AbstractPlayer;
import utils.GameState;

public class Player extends AbstractPlayer {

	private TrainColor color;

	public Player() {
		super();
	}

	public Player(String name) {
		super(name);
		this.color = GameState.availableColors.poll();
	}

	public TrainColor getColor() {
		return this.color;
	}

	public void populateHand(List<TrainCarCard> cards) {
		
		// for testing purposes
		
		TrainCarHand newHand = new TrainCarHand();
		newHand.populate(cards);
		this.hand = newHand;
		
	}

	



}
