package objects.interfaces;

import java.util.List;

import objects.DestinationCard;
import objects.DestinationDeck;
import objects.TrainCar;
import objects.TrainCarCard;
import objects.TrainCarDeck;

public interface IPlayer {
	public void drawCardFromDeck(TrainCarDeck deck);

	public void drawCardFromDeck(DestinationDeck deck);

	public String getName();

	public int getScore();

	public List<TrainCarCard> getHand();

	public List<DestinationCard> getDestinations();

	public List<TrainCar> getTrains();
}
