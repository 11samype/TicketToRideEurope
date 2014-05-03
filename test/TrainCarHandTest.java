import static org.junit.Assert.assertEquals;
import objects.TrainCarCard;
import objects.TrainCarHand;
import objects.TrainColor;

import org.junit.Test;

public class TrainCarHandTest {

	@Test
	public void testNumInHand() {
		TrainCarHand hand = new TrainCarHand();
		
		hand.addCard(new TrainCarCard(TrainColor.BLACK));
		
		assertEquals(1, hand.numInHand(new TrainCarCard(TrainColor.BLACK)));
	}
	
	@Test
	public void testRemoveCard() {
		TrainCarHand hand = new TrainCarHand();
		
		TrainCarCard black = new TrainCarCard(TrainColor.BLACK);
		TrainCarCard blue = new TrainCarCard(TrainColor.BLUE);
		TrainCarCard red = new TrainCarCard(TrainColor.RED);
		
		hand.addCard(black);
		hand.addCard(blue);
		hand.addCard(red);
		
		assertEquals(3, hand.size());
		
		hand.removeCard(0);
		
		assertEquals(2, hand.size());
		assertEquals(hand.getCard(0), blue);
		assertEquals(hand.getCard(1), red);
		
		hand.removeCard(blue);
		assertEquals(hand.getCard(0), red);
	}
	
	@Test
	public void testRemoveCardWithColor() {
		TrainCarHand hand = new TrainCarHand();
		
		TrainCarCard black = new TrainCarCard(TrainColor.BLACK);
		TrainCarCard blue = new TrainCarCard(TrainColor.BLUE);
		TrainCarCard red = new TrainCarCard(TrainColor.RED);
		
		hand.addCard(black);
		hand.addCard(blue);
		hand.addCard(red);
		
		assertEquals(3, hand.size());
		
		hand.removeCard(TrainColor.BLACK);
		
		assertEquals(2, hand.size());
		assertEquals(hand.getCard(0), blue);
		assertEquals(hand.getCard(1), red);
		
		hand.removeCard(TrainColor.BLUE);
		assertEquals(hand.getCard(0), red);
	}

}
