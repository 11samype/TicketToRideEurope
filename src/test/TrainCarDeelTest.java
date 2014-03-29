package test;

import objects.TrainCarCard;
import objects.TrainCarDeel;
import objects.TrainColor;

import org.junit.Test;

/**
 * TODO Put here a description of what this class does.
 * 
 * @author samynpd. Created Mar 28, 2014.
 */
public class TrainCarDeelTest {

	@Test(expected = UnsupportedOperationException.class)
	public void DeelSizetest() {
		TrainCarDeel deel = new TrainCarDeel();

		while (true) {
			deel.addCard(new TrainCarCard(TrainColor.BLUE));
		}

	}

}
