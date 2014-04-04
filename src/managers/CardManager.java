package managers;
import java.util.ArrayList;

import objects.Player;
import objects.TrainCarCard;
import objects.TrainCarDeck;
import objects.TrainCarDeel;
import objects.abstracts.AbstractCard;
import objects.abstracts.AbstractDeal;
import objects.abstracts.AbstractDeck;


/**
 * TODO Put here a description of what this class does.
 *
 * @author samynpd.
 *         Created Apr 3, 2014.
 */
public class CardManager {

	private TrainCarDeck deck;
	private TrainCarDeel deel;
	
	public CardManager() {
		this.deck = new TrainCarDeck();
		this.deel = new TrainCarDeel();
		
		fillDeelFromDeck();
	}
	
	public void fillDeelFromDeck() {
		while (!this.deel.full()) {
			this.deel.addCard(this.deck.draw());
		}
	}
	
	public TrainCarCard drawFromDeck() {
		return this.deck.draw();
	}
	
	public TrainCarCard drawFromDeel(int index) {
		TrainCarCard cardToReturn = this.deel.removeCard(index);
		fillDeelFromDeck();
		return cardToReturn;
	}
	
	public boolean deelFull() {
		return this.deel.full();
	}
	
	public TrainCarCard getDeelCard(int index) {
		return this.deel.getCard(index);
	}
	
	public int getSizeOfDeck() {
		return this.deck.size();
	}
	
	public TrainCarDeck getDeck() {
		return this.deck;
	}
}
