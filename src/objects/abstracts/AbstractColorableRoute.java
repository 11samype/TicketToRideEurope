package objects.abstracts;

import java.awt.Color;

import objects.Destination;
import objects.TrainColor;
import objects.interfaces.IColorable;

public abstract class AbstractColorableRoute extends AbstractRoute implements
		IColorable {

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
