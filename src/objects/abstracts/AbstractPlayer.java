package objects.abstracts;

import java.util.ArrayList;
import java.util.List;

import objects.DestinationCard;
import objects.DestinationDeck;
import objects.TrainCar;
import objects.TrainCarCard;
import objects.TrainCarDeck;
import objects.interfaces.IPlayer;

public class AbstractPlayer implements IPlayer {

	protected final String name;
	protected List<TrainCar> trains = new ArrayList<TrainCar>();
	protected List<TrainCarCard> hand = new ArrayList<TrainCarCard>();
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
		hand.add(deck.draw());
	}

	@Override
	public void drawCardFromDeck(DestinationDeck deck) {
		destinations.add(deck.draw());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public List<TrainCarCard> getHand() {
		return hand;
	}

	@Override
	public List<DestinationCard> getDestinations() {
		return destinations;
	}

	@Override
	public List<TrainCar> getTrains() {
		return trains;
	}

}
