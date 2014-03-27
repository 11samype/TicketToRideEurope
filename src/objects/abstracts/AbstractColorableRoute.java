package objects.abstracts;

import java.awt.Color;

import objects.Destination;
import objects.TrainColor;
import objects.interfaces.ITrainItem;

public abstract class AbstractColorableRoute extends AbstractRoute implements ITrainItem {

	protected final TrainColor color;

	public AbstractColorableRoute(Destination start, Destination end, int length) {
		super(start, end, length);
		this.color = TrainColor.RAINBOW;
	}

	public AbstractColorableRoute(Destination start, Destination end,
			TrainColor color, int length) {
		super(start, end, length);
		this.color = color;
	}


	@Override
	public TrainColor getColor() {
		return this.color;
	}

	@Override
	public Color getAwtColor() {
		return getColor().getAwtColor();
	}

}
