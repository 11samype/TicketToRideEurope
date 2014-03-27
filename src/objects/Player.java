package objects;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import objects.abstracts.AbstractPlayer;

public class Player extends AbstractPlayer {

	private TrainColor color;

	public Player() {
		super();
	}

	public Player(String name) {
		super(name);
		Random rGen = new Random();
		List<TrainColor> arr = TrainColor.getAllColors();
		this.color = arr.get(rGen.nextInt(arr.size()));
	}

	public Color getColor() {
		return this.color.getAwtColor();
	}

}
