package objects;

import java.awt.Color;

public class NullTrainCarCard extends TrainCarCard {

	public NullTrainCarCard() {
		super(null);
	}

	@Override
	public TrainColor getColor() {
		return null;
	}

	public Color getAwtColor() {
		return null;
	}

}
