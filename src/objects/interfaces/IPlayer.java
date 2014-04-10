package objects.interfaces;

import java.util.List;

import objects.DestinationCard;
import objects.DestinationDeck;
import objects.TrainCarDeck;

public interface IPlayer {
	public void drawCardFromDeck(TrainCarDeck deck);

	public void drawCardFromDeck(DestinationDeck deck);

	public String getName();

	public int getScore();

	public IHand<?> getHand();

	public List<DestinationCard> getDestinations();

	public int getNumTrains();

	public int getNumStations();
}
