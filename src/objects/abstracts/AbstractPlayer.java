package objects.abstracts;

import java.util.ArrayList;
import java.util.List;

import objects.Destination;
import objects.DestinationCard;
import objects.DestinationDeck;
import objects.DestinationHand;
import objects.GameState;
import objects.TrainCarCard;
import objects.TrainCarDeck;
import objects.TrainCarHand;
import objects.TrainColor;
import objects.interfaces.IPlayer;
import objects.interfaces.IRoute;

public class AbstractPlayer implements IPlayer {

	public static final int MAX_NUM_STATIONS = 3;
	public static final int MAX_NUM_TRAINS = 45;

	protected final String name;
	protected int numTrains;
	protected int numStations;
	protected TrainCarHand hand = new TrainCarHand();
	protected DestinationHand destinations = new DestinationHand();
	protected int score;
	protected List<IRoute> routes = new ArrayList<IRoute>();

	public AbstractPlayer() {
		this("New Player");
	}

	public AbstractPlayer(String name) {
		this.name = name;
		this.numTrains = MAX_NUM_TRAINS;
		this.numStations = MAX_NUM_STATIONS;
	}

	@Override
	public void drawCardFromDeck(TrainCarDeck deck) {
		this.hand.addCard(deck.draw());
	}

	@Override
	public void drawCardFromDeck(DestinationDeck deck) {
		this.destinations.addCard(deck.draw());
	}

	@Override
	public void drawCardFromDeal(int index) {
		TrainCarCard pickedCard = GameState.getInstance().getCardManager()
				.getDealCards().removeCardAtPosition(index);
		System.out.printf("Drew index %d; %s\n", index, pickedCard.getColor());
		this.hand.addCard(pickedCard);
	}

	public void claimRoute(IRoute route) throws UnsupportedOperationException {
		TrainColor routeColor = (route instanceof AbstractColorableRoute) ? ((AbstractColorableRoute) route)
				.getColor() : TrainColor.RAINBOW;

		int numberOfColorInHand = this.hand.numInHand(routeColor);
		int numberOfRainbowInHand = this.hand.numInHand(TrainColor.RAINBOW);
		int routeLength =  route.getLength();
		if (numberOfColorInHand >= routeLength) {
			this.numTrains -= routeLength;
			this.routes.add(route);

			for (int i = 0; i < routeLength; i++) {
				this.hand.removeCard(routeColor);
			}
		} else {
			throw new UnsupportedOperationException(
					"Not enough cards for route!\nYou have "
			+ numberOfColorInHand + " " + routeColor +
			" but the route is worth " + routeLength);
		}
	}

	public boolean placeStationOnDestination(Destination dest) {
		return dest.buildStation();
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
		return this.destinations.hand;
	}

	@Override
	public int getNumTrains() {
		return this.numTrains;
	}

	@Override
	public int getNumStations() {
		return this.numStations;
	}

	@Override
	public List<IRoute> getRoutes() {
		return this.routes;
	}

}
