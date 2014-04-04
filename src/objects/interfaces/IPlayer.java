package objects.interfaces;

import java.util.List;

import objects.DestinationCard;
import objects.DestinationDeck;
import objects.TrainCar;
import objects.TrainCarCard;
import objects.TrainCarDeck;
import objects.TrainCarHand;

public interface IPlayer {
	public void drawCardFromDeck(TrainCarDeck deck);

	public void drawCardFromDeck(DestinationDeck deck);

	public String getName();

	public int getScore();

	public IHand getHand();

	public List<DestinationCard> getDestinations();

	public List<TrainCar> getTrains();
}
