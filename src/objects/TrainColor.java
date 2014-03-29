package objects;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public enum TrainColor {

	BLUE(Color.BLUE), RED(Color.RED), GREEN(Color.GREEN), YELLOW(Color.YELLOW), PINK(
			Color.PINK), WHITE(Color.WHITE), ORANGE(Color.ORANGE), BLACK(
			Color.BLACK), RAINBOW(Color.GRAY, "GRAY");

	private final Color awtColor;
	private String awtName;

	private TrainColor(Color awtColor) {
		this(awtColor, null);
	}

	private TrainColor(Color awtColor, String name) {
		this.awtColor = awtColor;
		this.awtName = name;
	}

	public Color getAwtColor() {
		return this.awtColor;
	}

	@Override
	public String toString() {
		return this.awtName != null ? this.awtName : super.toString();
	}

	public static List<TrainColor> getAllColors() {
		return Arrays.asList(BLUE, RED, GREEN, YELLOW, PINK, WHITE, ORANGE,
				BLACK, RAINBOW);
	}

	public static TrainColor fromString(String colorString) {
		for (TrainColor color : getAllColors()) {
			if (colorString.equalsIgnoreCase(color.toString()))
				return color;
		}
		return null;
	}

}
