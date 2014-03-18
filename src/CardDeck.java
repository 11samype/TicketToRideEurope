import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class CardDeck {

	protected List<Card> cards = new ArrayList<Card>();

	public void shuffle() {
		Collections.shuffle(cards);
	}

	public Card draw() {
		return this.cards.remove(0);
	}

}
