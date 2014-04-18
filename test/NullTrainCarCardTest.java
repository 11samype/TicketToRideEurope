import static org.junit.Assert.*;
import objects.NullTrainCarCard;

import org.junit.Test;


public class NullTrainCarCardTest {

	@Test
	public void testGetColor() {
		NullTrainCarCard nullCard = new NullTrainCarCard();
		
		assertNull(nullCard.getColor());
		assertNull(nullCard.getAwtColor());
	}

}
