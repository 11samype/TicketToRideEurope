package utils;

import java.util.ArrayList;
import java.util.List;

import objects.CardPackage;
import objects.TrainCarCard;
import objects.TrainColor;

public class CardTurnEndManager {

	List<CardPackage> cards;
	
	public CardTurnEndManager() {
		this.cards = new ArrayList<CardPackage>();
	}
	
	public void addCardForCurrentTurn(CardPackage card) {
		this.cards.add(card);
	}
	
	public boolean canDrawTrainCard() {
		
		int weightedCount = 0;
		
		if ((this.cards.size() == 1) && (this.cards.get(0).card().getColor() == TrainColor.RAINBOW) && (this.cards.get(0).source() == CardPackage.DECK)) {
			return true;
		} else {
			for (CardPackage card : this.cards) {
				if (card.card().getColor() == TrainColor.RAINBOW) {
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
