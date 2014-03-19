package objects;

import java.awt.Color;

public class TrainCarCard extends AbstractCard {

	// locomotives?
	// maybe extend trainitem?

	private TrainColor color;

	public TrainCarCard(TrainColor color) {
		this.setColor(color);
	}

	public TrainColor getColor() {
		return color;
	}

	public Color getAwtColor() {
		return color.getAwtColor();
	}

	public void setColor(TrainColor color2) {
		this.color = color2;
	}

}
