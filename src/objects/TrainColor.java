package objects;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public enum TrainColor {

	BLUE(Color.BLUE), RED(Color.RED), GREEN(Color.GREEN), YELLOW(Color.YELLOW), PINK(
			Color.PINK), WHITE(Color.WHITE), ORANGE(Color.ORANGE), BLACK(
			Color.BLACK), RAINBOW(Color.CYAN);

	private final Color awtColor;

	private TrainColor(Color awtColor) {
		this.awtColor = awtColor;
	}

	public Color getAwtColor() {
		return awtColor;
	}

	public static List<TrainColor> getAllColors() {
		return Arrays.asList(BLUE, RED, GREEN, YELLOW, PINK, WHITE, ORANGE,
				BLACK, RAINBOW);
	}

	public static Color fromString(String colorString) {
		for (TrainColor color : getAllColors()) {
			if (colorString.equalsIgnoreCase(color.toString()))
				return color.getAwtColor();
		}
		return null;
	}

}
