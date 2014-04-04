package managers;

import objects.Player;
import objects.TrainCarCard;
import objects.TrainCarDeck;
import objects.TrainCarDeal;

/**
 * TODO Put here a description of what this class does.
 *
 * @author samynpd. Created Apr 3, 2014.
 */
public class CardManager {

	private TrainCarDeck deck;
	private TrainCarDeal deal;

	public CardManager() {
		this.deck = new TrainCarDeck();
		this.deal = new TrainCarDeal();

		fillDealFromDeck();
	}

	public void fillDealFromDeck() {
		while (!this.deal.isDealFull()) {
			this.deal.addCard(this.deck.draw());
		}
	}

	//
	// public TrainCarCard drawFromDeck() {
	// return this.deck.draw();
	// }

	public TrainCarCard drawFromDeal(int index) {
		TrainCarCard cardToReturn = this.deal.removeCard(index);
		fillDealFromDeck();
		return cardToReturn;
	}

	public boolean dealFull() {
		return this.deal.isDealFull();
	}

	public TrainCarCard getDealCard(int index) {
		return this.deal.getCard(index);
	}

	public int getSizeOfDeck() {
		return this.deck.size();
	}

	public TrainCarDeck getDeck() {
		return this.deck;
	}

	public void drawFromDeck(Player currentPlayer) {
		currentPlayer.drawCardFromDeck(this.deck);

	}
}
