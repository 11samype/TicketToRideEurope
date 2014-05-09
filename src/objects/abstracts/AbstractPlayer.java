package objects.abstracts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import objects.Destination;
import objects.DestinationCard;
import objects.DestinationDeck;
import objects.DestinationHand;
import objects.DestinationRoute;
import objects.GameState.CardManager;
import objects.TrainCarCard;
import objects.TrainCarDeck;
import objects.TrainCarHand;
import objects.TrainColor;
import objects.interfaces.ICard;
import objects.interfaces.IPlayer;
import objects.interfaces.IRoute;
import utils.GraphHelper;

public class AbstractPlayer implements IPlayer {

	public static final int MAX_NUM_STATIONS = 3;
	public static final int MAX_NUM_TRAINS = 45;

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
	
	public boolean canDrawTrainCard() {
		
		int numberOfRainbowInHand = 0;
		int numberOfRegularTrainsInHand = 0;
		
		List<TrainColor> trainColors = TrainColor.getAllColors();
		
		for (TrainColor color : trainColors) {
			if (color == TrainColor.RAINBOW) {
				numberOfRainbowInHand = this.hand.numInHand(color);
			} else {
				numberOfRegularTrainsInHand += this.hand.numInHand(color);
			}
		}
		
		int sum = (2 * numberOfRainbowInHand) + numberOfRegularTrainsInHand;
		
		return sum < 2;
	}
	
	public boolean canDrawDestination() {
		
		int numberOfCards = 0;
		
		List<TrainColor> trainColors = TrainColor.getAllColors();
		
		for (TrainColor color : trainColors) {
			numberOfCards += this.hand.numInHand(color);
		}
		
		return numberOfCards == 0;
	}
	
	@Override
	public void drawCardFromDeck(TrainCarDeck deck) {
		this.hand.addCard(deck.draw());
	}

	@Override
	public void drawCardFromDeck(DestinationDeck deck) {
		if (canDrawDestination()) {
			DestinationCard drawn = deck.draw();
			this.destinationHand.addCard(drawn);
			// this.score -= drawn.getScore();
		} else {
			
			throw new UnsupportedOperationException("Can't draw destination card after drawing a train card");
			
		}
	}

	@Override
	public void drawCardFromDeal(CardManager cardManager, int index) {
		TrainCarCard pickedCard = cardManager.drawDealCard(index);
		System.out.printf("Player %s Drew index %d; %s\n", this.toString(), index, pickedCard.getColor());
		this.hand.addCard(pickedCard);
	}

	public void claimRoute(IRoute route) throws UnsupportedOperationException {
		TrainColor routeColor = (route instanceof AbstractColorableRoute) ? ((AbstractColorableRoute) route)
				.getColor() : TrainColor.RAINBOW;

		int numberOfColorInHand = this.hand.numInHand(routeColor);
		int numberOfRainbowInHand = this.hand.numInHand(TrainColor.RAINBOW);
		int routeLength = route.getLength();
		if (numberOfColorInHand >= routeLength) {
			discardCardsOfColor(routeLength, routeColor);
			addRoute(route);
			placeTrains(routeLength);
			// addScoreForRoute(route);
		} else {
			throw new UnsupportedOperationException(
					"Not enough cards for route!\nYou have "
							+ numberOfColorInHand + " " + routeColor
							+ " but the route is worth " + routeLength);
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

	private void placeTrains(int numTrains) {
		this.numTrains -= numTrains;
	}

	public void discardCardsOfColor(int num, TrainColor color) {
		for (int i = 0; i < num; i++) {
			this.hand.removeCard(color);
		}
	}

	public boolean placeStationOnDestination(Destination dest) {
		if (this.numStations != 0) {
			this.numStations--;
			return true;
		}
		return false;

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
		return getHand().getCard(getHand().size() - 1);
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
