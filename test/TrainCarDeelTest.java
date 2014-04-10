
import objects.TrainCarCard;
import objects.TrainCarDeal;
import objects.TrainColor;

import org.junit.Test;

/**
 * TODO Put here a description of what this class does.
 * 
 * @author samynpd. Created Mar 28, 2014.
 */
public class TrainCarDeelTest {

	@Test(expected = UnsupportedOperationException.class)
	public void testDeelSize() {
		TrainCarDeal deel = new TrainCarDeal();

		while (true) {
			deel.addCard(new TrainCarCard(TrainColor.BLUE));
		}

	}

}
