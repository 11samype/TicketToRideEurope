package objects;

import objects.abstracts.AbstractDeal;

/**
 * TODO Put here a description of what this class does.
 *
 * @author samynpd.
 *         Created Mar 28, 2014.
 */
public class TrainCarDeel extends AbstractDeal<TrainCarCard>{

	public TrainCarDeel() {
		super();
		
		//filler code
		
		for (int i = 0; i < 5; i++) {
			addCard(new TrainCarCard(TrainColor.BLACK));
			
		}
		
	}
	
}
