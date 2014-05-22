package objects.abstracts;

import gui.panels.MainPanel;

import java.util.ArrayList;
import java.util.List;

import objects.Destination;
import objects.DestinationCard;
import objects.DestinationDeck;
import objects.DestinationHand;
import objects.DestinationRoute;
import objects.DiscardPile;
import objects.TrainCarCard;
import objects.TrainCarDeck;
import objects.TrainCarHand;
import objects.TrainColor;
import objects.interfaces.ICard;
import objects.interfaces.IPlayer;
import objects.interfaces.IRoute;
import utils.GameState;
import utils.GraphHelper;
import utils.GameState.CardManager;
import utils.exceptions.DestinationAfterTrainException;
import utils.exceptions.DestinationHasStationException;
import utils.exceptions.NotEnoughCardsForRouteException;
import utils.exceptions.OutOfStationsException;
import utils.exceptions.RouteOwnedException;

public class AbstractPlayer implements IPlayer {

	public static final int MAX_NUM_STATIONS = 3;
	public static final int MAX_NUM_TRAINS = 45;

	protected int prevTurnCardSum;
	protected int prevTurnNumCards;

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

	public void setPrevTurnCardNum() {
		this.prevTurnNumCards = getHand().size();
	}

	public boolean canDrawTrainCard() {

		int[] cardSum = getTotalNumberOfCardsInHand();
				
		int numberOfRegularTrainsInHand = cardSum[0];
		int numberOfRainbowInHand = cardSum[1];
		
		int weightedSum = ((2 * numberOfRainbowInHand) + numberOfRegularTrainsInHand)
				- this.prevTurnCardSum;

		if (weightedSum < 2) {
			return true;
		} else {
			this.prevTurnCardSum = this.prevTurnCardSum + weightedSum;

			return false;
		}
	}
	
	public int[] getTotalNumberOfCardsInHand() {
		int[] nums = new int[2];

		List<TrainColor> trainColors = TrainColor.getAllColors();

		for (TrainColor color : trainColors) {
			if (color.equals(TrainColor.RAINBOW)) {
				nums[1] = this.hand.numInHand(TrainColor.RAINBOW);
			} else {
				nums[0] += this.hand.numInHand(color);
			}
		}
		return nums;
		
	}
	
	

	public boolean canDrawDestination() {

		int numberOfCards = getHand().size() - this.prevTurnNumCards;

		this.prevTurnNumCards = numberOfCards + this.prevTurnNumCards;

		return numberOfCards == 0;

	}

	@Override
	public void drawCardFromDeck(TrainCarDeck deck) {
		
		this.hand.addCard(deck.draw());
		
		if (deck.isEmpty()) {
			DiscardPile<TrainCarCard> discard = GameState.getCardManager().getDiscardPile();
			
			List<TrainCarCard> cards = new ArrayList<TrainCarCard>();
			
			while(!discard.isEmpty()) {
				cards.add((TrainCarCard)discard.draw());
			}
			
			deck.populate(cards);
		}
		
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

	@Override
	public void claimRoute(IRoute route) throws NotEnoughCardsForRouteException, RouteOwnedException {
		if (this.routes.contains(route))
			throw new RouteOwnedException();
		
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

	public boolean placeStationOnDestination(Destination dest) throws OutOfStationsException, DestinationHasStationException {
		if (this.numStations <= 0) {
			throw new OutOfStationsException();
		}
		if (dest.hasStation()) {
			throw new DestinationHasStationException();
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
		int score = numStations;
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

	public List<DestinationRoute> getCompletedDestinations() {
	
		List<DestinationRoute> destList = new ArrayList<DestinationRoute>();
	
		for (IRoute route : this.routes) {
			if (route instanceof DestinationRoute) {
				destList.add((DestinationRoute) route);
			}
		}
	
		return destList;
	}

}
