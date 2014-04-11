
import static org.junit.Assert.*;
import objects.TrainCarCard;
import objects.TrainCarDeal;
import objects.TrainColor;

import org.junit.Test;

public class TrainCarDealTest {

	@Test(expected = UnsupportedOperationException.class)
	public void testDeelSize() {
		TrainCarDeal deal = new TrainCarDeal();

		while (true) {
			deal.addCard(new TrainCarCard(TrainColor.BLUE));
		}

	}
	
	@Test
	public void testRemoveAtIndex() {
		TrainCarDeal deal = new TrainCarDeal();

		TrainCarCard black = new TrainCarCard(TrainColor.BLACK);
		TrainCarCard blue = new TrainCarCard(TrainColor.BLUE);
		TrainCarCard red = new TrainCarCard(TrainColor.RED);
		
		deal.addCard(black);
		deal.addCard(blue);
		deal.addCard(red);
		
		deal.removeCardAtPosition(1);
		
		assertEquals(black, deal.getCardAtPosition(0));
		assertEquals(red, deal.getCardAtPosition(1));

	}

}
