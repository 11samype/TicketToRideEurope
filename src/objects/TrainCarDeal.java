package objects;

import objects.abstracts.AbstractDeal;

public class TrainCarDeal extends AbstractDeal<TrainCarCard> {

	public TrainCarDeal() {
		super();
	}

	@Override
	public TrainCarCard removeCardAtPosition(int index) {
		TrainCarCard getCard = this.deal.get(index);
		this.deal.set(index, new NullTrainCarCard());
		return getCard;
	}
}
