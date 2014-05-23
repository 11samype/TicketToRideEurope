package utils;

import java.util.ArrayList;
import java.util.List;

import objects.TrainCarCard;
import objects.TrainColor;

public class CardTurnEndManager {

	List<TrainCarCard> cards;
	
	public CardTurnEndManager() {
		this.cards = new ArrayList<TrainCarCard>();
	}
	
	public void addCardForCurrentTurn(TrainCarCard card) {
		this.cards.add(card);
	}
	
	public boolean canDrawTrainCard() {
		
		int weightedCount = 0;
		
		if ((this.cards.size() == 1) && (this.cards.get(0).getColor() == TrainColor.RAINBOW)) {
			return true;
		} else {
			for (TrainCarCard card : this.cards) {
				if (card.getColor() == TrainColor.RAINBOW) {
					weightedCount += 2;
				} else {
					weightedCount++;
				}
			}
			
			return weightedCount < 2;
		}
	}
	
	public boolean canDrawDestinationCard() {
		return this.cards.isEmpty();
	}
	
	public void endTurn() {
		this.cards.clear();
	}
	
	
}
