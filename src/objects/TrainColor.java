package objects;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public class TrainColor {

	public static Color BLUE = Color.BLUE;
	public static Color RED = Color.RED;
	public static Color GREEN = Color.GREEN;
	public static Color YELLOW = Color.YELLOW;
	public static Color PINK = Color.PINK;
	public static Color WHITE = Color.WHITE;
	public static Color ORANGE = Color.ORANGE;
	public static Color BLACK = Color.BLACK;
	public static Color RAINBOW = Color.CYAN;

	public static List<Color> getAllColors() {
		return Arrays.asList(BLUE, RED, GREEN, YELLOW, PINK, WHITE, ORANGE, BLACK, RAINBOW);
	}

}
