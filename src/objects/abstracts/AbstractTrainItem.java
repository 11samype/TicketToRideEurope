package objects.abstracts;

import java.awt.Color;

import objects.TrainColor;

public abstract class AbstractTrainItem {

	private TrainColor color;

	public AbstractTrainItem(TrainColor color) {
		this.color = color;
	}

	public TrainColor getColor() {
		return color;
	}

	public Color getAwtColor() {
		return this.color.getAwtColor();
	}

}
