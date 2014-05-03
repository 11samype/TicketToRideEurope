package objects.abstracts;

import gui.DrawableDestination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import objects.Destination;
import objects.DestinationCard;
import objects.DestinationDeck;
import objects.DestinationHand;
import objects.DestinationRoute;
import objects.GameState;
import objects.TrainCarCard;
import objects.TrainCarDeck;
import objects.TrainCarHand;
import objects.TrainColor;
import objects.interfaces.IPlayer;
import objects.interfaces.IRoute;
import utils.DestinationLocationReader;
import utils.GraphHelper;
import utils.TrainRouteReader;

public class AbstractPlayer implements IPlayer {

	public static final int MAX_NUM_STATIONS = 3;
	public static final int MAX_NUM_TRAINS = 45;

	protected final String name;
	protected int numTrains;
	protected int numStations;
	private int score;
	protected TrainCarHand hand = new TrainCarHand();
	protected DestinationHand destinationRoutes = new DestinationHand();
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
		DestinationCard drawn = deck.draw();
		this.destinationRoutes.addCard(drawn);
		this.score -= drawn.getScore();
	}

	@Override
	public void drawCardFromDeal(int index) {
		TrainCarCard pickedCard = GameState.getInstance().getCardManager()
				.drawDealCard(index);
		System.out.printf("Drew index %d; %s\n", index, pickedCard.getColor());
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
			this.routes.add(route);
			placeTrains(routeLength);
			addScoreForRoute(route);
		} else {
			throw new UnsupportedOperationException(
					"Not enough cards for route!\nYou have "
							+ numberOfColorInHand + " " + routeColor
							+ " but the route is worth " + routeLength);
		}
	}

	private void placeTrains(int numTrains) {
		this.numTrains -= numTrains;
	}

	private void addScoreForRoute(IRoute route) {
		// add score for any type other than destination
		if (!(route instanceof DestinationRoute))
			this.score += route.getScore();

		// check to see if adding this route completed a destination card
		for (DestinationCard destCard : this.destinationRoutes.getCards()) {
			if (GraphHelper.hasPlayerCompletedDestinationRoute(this,
					destCard.getRoute())) {
				this.score += destCard.getScore();
			}
		}
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
		// int score = 0;
		//
		// for (DestinationCard destinationCard: destinationRoutes.hand) {
		// DestinationRoute route = destinationCard.getRoute();
		// if (this.hasCompleted(route)) {
		// score += route.getScore();
		// } else {
		// score -= route.getScore();
		// }
		// }
		//
		// for (IRoute claimedRoute : this.routes) {
		// score += claimedRoute.getScore();
		// }
		return score;
	}

	public boolean hasCompleted(DestinationRoute destRoute) {
		return GraphHelper.hasPlayerCompletedDestinationRoute(this, destRoute);

	}

	@Override
	public TrainCarHand getHand() {
		return this.hand;
	}

	@Override
	public List<DestinationCard> getDestinations() {
		return this.destinationRoutes.hand;
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
