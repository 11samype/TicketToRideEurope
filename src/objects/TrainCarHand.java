package objects;

import objects.abstracts.AbstractHand;

public class TrainCarHand extends AbstractHand<TrainCarCard> {

	public TrainCarHand() {
		super();
	}
	
	@Override
	public int numInHand(TrainCarCard card) {
		
		int count = 0;
		
		for (int i = 0; i < this.hand.size(); i++) {
			if (this.hand.get(i).getColor().equals(card.getColor())) {
				count++;
			}
		}
		
		return count;
		
	}
	
	@Override
	public int numInHand(final TrainColor color) {
		
		int count = 0;
		
		for (int i = 0; i < this.hand.size(); i++) {
			if (this.hand.get(i).getColor().equals(color)) {
				count++;
			}
		}
		
		return count;
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @return
	 */
	public Object size() {
		// TODO Auto-generated method stub.
		return this.hand.size();
	}

}
