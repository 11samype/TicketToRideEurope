import java.util.List;

public class Player {

	protected String name;
	protected List<TrainCar> trains;
	protected List<TrainCarCard> hand;
	protected List<DestinationTicketCard> destinations;
	protected int score ;


	public Player() {

	}

	public Player(String name) {
		this.name = name;
	}

	public void drawCard(CardDeck deck) {
		if (deck instanceof TrainCarDeck) {
			hand.add((TrainCarCard) deck.draw());
		} else if (deck instanceof DestinationCardDeck) {
			destinations.add((DestinationTicketCard) deck.draw());
		}
	}




}
