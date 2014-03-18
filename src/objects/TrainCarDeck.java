package objects;
import java.awt.Color;

public class TrainCarDeck extends CardDeck {

	public TrainCarDeck() {
		addCardsToDeckBeforeShuffle();
		
		shuffle();
		
	}
	
	private void addCardsToDeckBeforeShuffle() {
		
		this.cards.clear();
		
		for (int i = 0; i < 12; i++) {
			this.cards.add(new TrainCarCard(Color.BLUE));
		}
		for (int i = 12; i < 24; i++) {
			this.cards.add(new TrainCarCard(Color.RED));
		}
		for (int i = 24; i < 36; i++) {
			this.cards.add(new TrainCarCard(Color.GREEN));
		}
		for (int i = 36; i < 48; i++) {
			this.cards.add(new TrainCarCard(Color.YELLOW));
		}
		for (int i = 48; i < 60; i++) {
			this.cards.add(new TrainCarCard(Color.PINK));
		}
		for (int i = 60; i < 72; i++) {
			this.cards.add(new TrainCarCard(Color.WHITE));
		}
		for (int i = 72; i < 84; i++) {
			this.cards.add(new TrainCarCard(Color.ORANGE));
		}
		for (int i = 84; i < 96; i++) {
			this.cards.add(new TrainCarCard(Color.BLACK));
		}
		for (int i = 96; i < 110; i++) {
			this.cards.add(new TrainCarCard(Color.CYAN)); //actually rainbow
		}
	}
}
