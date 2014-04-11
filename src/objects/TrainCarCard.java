package objects;

import java.awt.Color;

import objects.abstracts.AbstractCard;
import objects.interfaces.IColorable;

public class TrainCarCard extends AbstractCard<TrainColor> implements
		IColorable {

	public TrainCarCard(TrainColor color) {
		this.value = color;
	}

	@Override
	public TrainColor getColor() {
		return getValue();
	}

	@Override
	public Color getAwtColor() {
		return getValue().getAwtColor();
	}

}
