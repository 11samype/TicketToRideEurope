package objects;

import java.awt.Color;

import objects.abstracts.AbstractCard;
import objects.interfaces.ITrainItem;

public class TrainCarCard extends AbstractCard<TrainColor> implements
		ITrainItem {

	// locomotives?
	// maybe extend trainitem?

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
