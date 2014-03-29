package test;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import objects.TrainColor;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Categories.ExcludeCategory;

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
		assertFalse(size == 0);
		assertTrue(size > 0);
		assertEquals(9, size);
	}

	@Test
	public void testFromString() {
		List<Color> correct = Arrays.asList(Color.RED, Color.GREEN,
				Color.BLACK, Color.BLUE, Color.GRAY, Color.YELLOW,
				Color.ORANGE, Color.PINK, Color.WHITE);
		List<String> testStrings = Arrays.asList("Red", "Green", "Black", "Blue", "Gray", "Yellow", "Orange", "Pink", "White");

		// Capitalized
		for (int i = 0; i < correct.size(); i++) {
			assertEquals(correct.get(i), TrainColor.fromString(testStrings.get(i)).getAwtColor());
		}

		// Lower-case
		for (int i = 0; i < correct.size(); i++) {
			assertEquals(correct.get(i), TrainColor.fromString(testStrings.get(i).toLowerCase()).getAwtColor());
		}

		assertNull(TrainColor.fromString("Magenta"));

	}

}
