import java.awt.Color;


public class TrainCarDeck extends CardDeck {

	private Card[] cards;

	public TrainCarDeck() {
		
		this.cards = new Card[240];
		
		for (int i = 0; i < 45; i++) {
			this.cards[i] = new TrainCarCard(Color.BLUE);
		}
		
		shuffle();
	}
}
