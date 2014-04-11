package objects;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import objects.abstracts.AbstractPlayer;

public class Player extends AbstractPlayer {

	private TrainColor color;

	public Player() {
		this("New Player");
	}

	public Player(String name) {
		super(name);
		Random rGen = new Random();

		List<TrainColor> arr = Arrays.asList(TrainColor.BLUE, TrainColor.RED, TrainColor.GREEN, TrainColor.WHITE, TrainColor.YELLOW);

		this.color = arr.get(rGen.nextInt(arr.size()));
	}

	public Color getColor() {
		return this.color.getAwtColor();
	}

}
