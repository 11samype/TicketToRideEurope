package objects;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public class TrainCarDeck extends CardDeck {

	public TrainCarDeck() {
		List<Color> colors = Arrays.asList(Color.BLUE, Color.RED, Color.GREEN,
				Color.YELLOW, Color.PINK, Color.WHITE, Color.ORANGE,
				Color.BLACK, Color.CYAN);

		for (Color color : colors) {
			for (int j = 0; j < 12; j++) {
				this.cards.add(new TrainCarCard(color));
			}
		}
		this.cards.add(new TrainCarCard(Color.CYAN)); // actually rainbow)
		this.cards.add(new TrainCarCard(Color.CYAN));

		shuffle();
	}
}
