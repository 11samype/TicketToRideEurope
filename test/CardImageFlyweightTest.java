import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.HashMap;

import gui.CardImageFlyweight;
import objects.TrainColor;

import org.junit.Test;


public class CardImageFlyweightTest {

	@Test
	public void testGetInstance() {
		assertNotNull(CardImageFlyweight.getInstance());
	}

	@Test
	public void testGetImageForColor() {
		CardImageFlyweight fly = CardImageFlyweight.getInstance();
		
		try {
			Field images = fly.getClass().getDeclaredField("imageMap");
			images.setAccessible(true);
			HashMap<TrainColor, BufferedImage> map = (HashMap<TrainColor, BufferedImage>) images.get(fly);
			assertTrue(map.isEmpty());
			assertFalse(map.containsKey(TrainColor.BLACK));
			fly.getImageForColor(TrainColor.BLACK);
			assertTrue(map.containsKey(TrainColor.BLACK));
			assertFalse(map.containsKey(TrainColor.GREEN));
			
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
