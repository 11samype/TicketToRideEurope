package objects;

import objects.abstracts.AbstractDeal;

/**
 * TODO Put here a description of what this class does.
 *
 * @author samynpd. Created Mar 28, 2014.
 */
public class TrainCarDeal extends AbstractDeal<TrainCarCard> {

	public TrainCarDeal() {
		super();

	}

	@Override
	public boolean isDealFull() {
		return (this.deal.size() == 5);
	}

}
