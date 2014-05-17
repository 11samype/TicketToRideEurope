package objects.interfaces;

import java.util.List;

import objects.DestinationCard;
import objects.DestinationDeck;
import objects.TrainCarDeck;
import utils.GameState.CardManager;
import utils.exceptions.DestinationAfterTrainException;
import utils.exceptions.NotEnoughCardsForRouteException;
import utils.exceptions.RouteOwnedException;

public interface IPlayer {
	public String getName();

	public int getScore();

	public void drawCardFromDeck(TrainCarDeck deck);

	public void drawCardFromDeck(DestinationDeck deck) throws DestinationAfterTrainException;

	public void drawCardFromDeal(CardManager cardManager, int index);

	public IHand<?> getHand();

	public int getNumTrains();

	public int getNumStations();

	public List<DestinationCard> getDestinationHand();

	public List<IRoute> getRoutes();

	public void claimRoute(IRoute route) throws NotEnoughCardsForRouteException, RouteOwnedException;

	public ICard getLastCardDrawn();
}
