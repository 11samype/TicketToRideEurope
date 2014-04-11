import static org.junit.Assert.*;
import objects.TrainCarCard;
import objects.TrainCarHand;
import objects.TrainColor;

import org.junit.Test;


/**
 * TODO Put here a description of what this class does.
 *
 * @author samynpd.
 *         Created Apr 11, 2014.
 */
public class TrainCarHandTest {

	@Test
	public void testNumInHand() {
		TrainCarHand hand = new TrainCarHand();
		
		hand.addCard(new TrainCarCard(TrainColor.BLACK));
		
		assertEquals(1, hand.numInHand(new TrainCarCard(TrainColor.BLACK)));
	}

}
