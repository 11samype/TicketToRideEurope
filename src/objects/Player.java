package objects;

import java.util.ArrayList;
import java.util.List;

public class Player {

	protected String name;
	protected List<TrainCar> trains = new ArrayList<TrainCar>();
	protected List<TrainCarCard> hand = new ArrayList<TrainCarCard>();
	protected List<DestinationTicketCard> destinations = new ArrayList<DestinationTicketCard>();
	protected int score;

	public Player(String name) {
		this.name = name;
	}

	public Player() {
		this("");
	}

	public void drawCard(CardDeck deck) {
		if (deck instanceof TrainCarDeck) {
			hand.add((TrainCarCard) deck.draw());
		} else if (deck instanceof DestinationCardDeck) {
			destinations.add((DestinationTicketCard) deck.draw());
		}
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public List<TrainCarCard> getHand() {
		return hand;
	}

	public List<DestinationTicketCard> getDestinations() {
		return destinations;
	}

	public List<TrainCar> getTrains() {
		return trains;
	}

}
