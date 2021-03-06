
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import objects.TrainColor;

import org.junit.Test;

public class TrainColorTest {

	@Test
	public void testGetAwtColor() {
		assertFalse(Color.BLACK.equals(TrainColor.RAINBOW.getAwtColor()));
		assertFalse(Color.RED.equals(TrainColor.GREEN.getAwtColor()));
		assertTrue(Color.YELLOW.equals(TrainColor.YELLOW.getAwtColor()));
		assertTrue(Color.GRAY.equals(TrainColor.RAINBOW.getAwtColor()));

	}

	@Test
	public void testGetAllColors() {
		List<TrainColor> colors = TrainColor.getAllColors();
		assertNotNull(colors);
		int size = colors.size();
		assertEquals(9, size);
	}

	@Test
	public void testFromString() {
		List<Color> correct = Arrays.asList(Color.RED, Color.GREEN,
				Color.BLACK, Color.BLUE, Color.GRAY, Color.YELLOW,
				Color.ORANGE, Color.PINK, Color.WHITE);
		List<String> testStrings = Arrays.asList("Red", "Green", "Black",
				"Blue", "Gray", "Yellow", "Orange", "Pink", "White");

		// Capitalized
		for (int i = 0; i < correct.size(); i++) {
			assertEquals(correct.get(i),
					TrainColor.fromString(testStrings.get(i)).getAwtColor());
		}

		// Lower-case
		for (int i = 0; i < correct.size(); i++) {
			assertEquals(correct.get(i),
					TrainColor.fromString(testStrings.get(i).toLowerCase())
							.getAwtColor());
		}

		assertNull(TrainColor.fromString("Magenta"));

	}

}
