package objects;

import objects.abstracts.AbstractPlayer;

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

	



}
