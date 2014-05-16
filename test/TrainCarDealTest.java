import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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

	@Test(expected = NullPointerException.class)
	public void testRemoveAtIndex() {
		TrainCarDeal deal = new TrainCarDeal();

		TrainCarCard black = new TrainCarCard(TrainColor.BLACK);
		TrainCarCard blue = new TrainCarCard(TrainColor.BLUE);
		TrainCarCard red = new TrainCarCard(TrainColor.RED);

		deal.addCard(black);
		deal.addCard(blue);
		deal.addCard(red);

		try {
			assertEquals(blue, deal.getCardAtPosition(1));
			deal.removeCardAtPosition(1);
			assertNull(deal.getCardAtPosition(1)); // throws
		} catch (NullPointerException e) {
			throw e;
		}
	}

	@Test(expected=NullPointerException.class)
	public void testRemoveCard() {
		TrainCarDeal deal = new TrainCarDeal();

		TrainCarCard black = new TrainCarCard(TrainColor.BLACK);
		TrainCarCard blue = new TrainCarCard(TrainColor.BLUE);
		TrainCarCard red = new TrainCarCard(TrainColor.RED);

		deal.addCard(black);
		deal.addCard(blue);
		deal.addCard(red);

		assertEquals(blue, deal.getCardAtPosition(1));

		deal.removeCard(blue);

		assertEquals(black, deal.getCardAtPosition(0));

		try {
			assertNull(deal.getCardAtPosition(1));
		} catch (NullPointerException e) {
			throw e;
		}

	}

	@Test
	public void testRemoveCardNotInDeal() {
		TrainCarDeal deal = new TrainCarDeal();

		TrainCarCard black = new TrainCarCard(TrainColor.BLACK);
		TrainCarCard blue = new TrainCarCard(TrainColor.BLUE);
		TrainCarCard red = new TrainCarCard(TrainColor.RED);

		deal.addCard(black);
		deal.addCard(blue);
		deal.addCard(red);

		TrainCarCard green = new TrainCarCard(TrainColor.GREEN);

		assertNull(deal.removeCard(green));

		assertEquals(black, deal.getCardAtPosition(0));
		// assertEquals(new NullTrainCarCard(), deal.getCardAtPosition(1));
		assertEquals(blue, deal.getCardAtPosition(1));
		assertEquals(red, deal.getCardAtPosition(2));

	}

}
