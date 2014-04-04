package objects.abstracts;

import java.util.ArrayList;
import java.util.List;

import objects.DestinationCard;
import objects.DestinationDeck;
import objects.TrainCar;
import objects.TrainCarCard;
import objects.TrainCarDeck;
import objects.TrainCarHand;
import objects.interfaces.IHand;
import objects.interfaces.IPlayer;

public class AbstractPlayer implements IPlayer {

	protected final String name;
	protected List<TrainCar> trains = new ArrayList<TrainCar>();
	protected TrainCarHand hand = new TrainCarHand();
	protected List<DestinationCard> destinations = new ArrayList<DestinationCard>();
	protected int score;

	public AbstractPlayer() {
		this("New Player");
	}

	public AbstractPlayer(String name) {
		this.name = name;
	}

	@Override
	public void drawCardFromDeck(TrainCarDeck deck) {
		this.hand.addCard(deck.draw());
	}

	@Override
	public void drawCardFromDeck(DestinationDeck deck) {
		this.destinations.add(deck.draw());
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getScore() {
		return this.score;
	}

	@Override
	public TrainCarHand getHand() {
		return this.hand;
	}

	@Override
	public List<DestinationCard> getDestinations() {
		return this.destinations;
	}

	@Override
	public List<TrainCar> getTrains() {
		return this.trains;
	}

}
