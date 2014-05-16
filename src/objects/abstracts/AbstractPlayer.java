package objects.abstracts;

import java.util.ArrayList;
import java.util.List;

import objects.Destination;
import objects.DestinationCard;
import objects.DestinationDeck;
import objects.DestinationHand;
import objects.DestinationRoute;
import objects.GameState;
import objects.GameState.CardManager;
import objects.TrainCarCard;
import objects.TrainCarDeck;
import objects.TrainCarHand;
import objects.TrainColor;
import objects.interfaces.ICard;
import objects.interfaces.IPlayer;
import objects.interfaces.IRoute;
import utils.GraphHelper;
import utils.exceptions.DestinationAfterTrainException;
import utils.exceptions.NotEnoughCardsForRouteException;
import utils.exceptions.OutOfStationsException;

public class AbstractPlayer implements IPlayer {

	public static final int MAX_NUM_STATIONS = 3;
	public static final int MAX_NUM_TRAINS = 45;

	protected int prevTurnCardSum = 0;
	protected int prevTurnNumCards = 0;

	protected final String name;
	protected int numTrains;
	protected int numStations;
	protected TrainCarHand hand = new TrainCarHand();
	protected DestinationHand destinationHand = new DestinationHand();
	protected List<IRoute> routes = new ArrayList<IRoute>();

	public AbstractPlayer() {
		this("New Player");
	}

	public AbstractPlayer(String name) {
		this.name = name;
		this.numTrains = MAX_NUM_TRAINS;
		this.numStations = MAX_NUM_STATIONS;
	}

	public List<DestinationRoute> getCompletedDestinations() {

		List<DestinationRoute> destList = new ArrayList<DestinationRoute>();

		for (IRoute route : this.routes) {
			if (route instanceof DestinationRoute) {
				destList.add((DestinationRoute) route);
			}
		}

		return destList;
	}

	public void setPrevTurnCardNum() {
		this.prevTurnNumCards = getHand().size();
	}

	public boolean canDrawTrainCard() {

		int numberOfRainbowInHand = 0;
		int numberOfRegularTrainsInHand = 0;

		List<TrainColor> trainColors = TrainColor.getAllColors();

		for (TrainColor color : trainColors) {
			if (color.equals(TrainColor.RAINBOW)) {
				numberOfRainbowInHand = this.hand.numInHand(color);
			} else {
				numberOfRegularTrainsInHand += this.hand.numInHand(color);
			}
		}

		int sum = ((2 * numberOfRainbowInHand) + numberOfRegularTrainsInHand)
				- this.prevTurnCardSum;

		if (sum < 2) {
			return true;
		} else {
			this.prevTurnCardSum = this.prevTurnCardSum + sum;

			return false;
		}

	}

	public boolean canDrawDestination() {

		int numberOfCards = getHand().size() - this.prevTurnNumCards;

		this.prevTurnNumCards = numberOfCards + this.prevTurnNumCards;

		return numberOfCards == 0;

	}

	@Override
	public void drawCardFromDeck(TrainCarDeck deck) {
		this.hand.addCard(deck.draw());

		// end turn if collected 2 trains (or one rainbow)
		if (!this.canDrawTrainCard()) {
			GameState.takeTurn();
		}
	}

	@Override
	public void drawCardFromDeck(DestinationDeck deck) throws DestinationAfterTrainException {
		if (canDrawDestination()) {
			DestinationCard drawn = deck.draw();
			this.destinationHand.addCard(drawn);
			
			// end turn when drawing destination
			GameState.takeTurn();
			// this.score -= drawn.getScore();
		} else {
			throw new DestinationAfterTrainException();

		}
	}

	@Override
	public void drawCardFromDeal(CardManager cardManager, int index) {
		TrainCarCard pickedCard = cardManager.drawDealCard(index);
		this.hand.addCard(pickedCard);

		// end turn if collected 2 trains (or one rainbow)
		if (!this.canDrawTrainCard()) {
			GameState.takeTurn();
		}
	}

	public void claimRoute(IRoute route) throws NotEnoughCardsForRouteException {
		TrainColor routeColor = (route instanceof AbstractColorableRoute) ? ((AbstractColorableRoute) route)
				.getColor() : TrainColor.RAINBOW;

		int numberOfColorInHand = this.hand.numInHand(routeColor);
		int numberOfRainbowInHand = this.hand.numInHand(TrainColor.RAINBOW);
		int routeLength = route.getLength();
		if (numberOfColorInHand >= routeLength) {
			discardCardsOfColor(routeLength, routeColor);
			addRoute(route);
			this.numTrains -= routeLength;
			// addScoreForRoute(route);
		} else {
			System.err.println(
					"Not enough cards for route!\nYou have "
							+ numberOfColorInHand + " " + routeColor
							+ " but the route is worth " + routeLength);
			throw new NotEnoughCardsForRouteException();
		}
	}

	private void addRoute(IRoute route) {
		this.routes.add(route);

		for (DestinationCard destCard : this.destinationHand.getCards()) {
			if (GraphHelper.hasPlayerCompletedDestinationRoute(this,
					destCard.getRoute())) {
				if (!this.routes.contains(destCard.getRoute()))
					this.routes.add(destCard.getRoute());
			}
		}
	}

	public void discardCardsOfColor(int num, TrainColor color) {
		for (int i = 0; i < num; i++) {
			this.hand.removeCard(color);
		}
	}

	public boolean placeStationOnDestination(Destination dest) throws OutOfStationsException {
		if (this.numStations <= 0) {
			throw new OutOfStationsException();
		}

		this.numStations--;
		return true;

	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getScore() {
		int score = 0;
		for (IRoute claimedRoute : this.routes) {
			score += claimedRoute.getScore();
		}

		for (DestinationCard destCard : this.destinationHand.getCards()) {
			if (!this.routes.contains(destCard.getRoute())) {
				score -= destCard.getScore();
			}
		}

		return score;
	}

	@Override
	public TrainCarHand getHand() {
		return this.hand;
	}

	@Override
	public ICard getLastCardDrawn() {
		int sizeOfHand = getHand().size();
		if (sizeOfHand == 0)
			return null;
		return getHand().getCard(sizeOfHand - 1);
	}

	@Override
	public List<DestinationCard> getDestinationHand() {
		return this.destinationHand.hand;
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
