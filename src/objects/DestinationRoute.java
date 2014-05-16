package objects;

import java.util.ResourceBundle;

import objects.abstracts.AbstractRoute;
import utils.MessageHelper;

public class DestinationRoute extends AbstractRoute {

	public DestinationRoute(Destination start, Destination end) {
		super(start, end, 1);
	}

	public DestinationRoute(Destination start, Destination end, int score) {
		super(start, end, score);
	}

	@Override
	public int getScore() {
		return this.length;
	}

	@Override
	public String toString() {
		return String.format("%s (%d)", super.toString(), getScore());
	}

	public Object[] toLocalizedArray() {
		ResourceBundle cities = MessageHelper.getCityNames();
		String startName = MessageHelper.getStringFromBundle(cities, getStart().getName());
		String endName = MessageHelper.getStringFromBundle(cities, getEnd().getName());
		return new Object[] { startName, endName, getScore() };
	}

}
