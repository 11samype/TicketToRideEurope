package objects;

import objects.abstracts.AbstractDeal;

public class TrainCarDeal extends AbstractDeal<TrainCarCard> {

	public TrainCarDeal() {
		super();

		for (int i = 0; i < MAX_DEALT_CARDS; i++) {
			this.deal.add(null);
		}
		
	}

//	@Override
//	public TrainCarCard removeCardAtPosition(int index) {
//		TrainCarCard getCard = this.deal.get(index);
//		this.deal.set(index, new NullTrainCarCard());
//		return getCard;
//	}
}
