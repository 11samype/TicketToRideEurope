package objects;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

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
