package objects.abstracts;

import gui.drawables.DrawableRoute;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import objects.CardPackage;
import objects.Destination;
import objects.DestinationCard;
import objects.DestinationDeck;
import objects.DestinationHand;
import objects.DestinationRoute;
import objects.FerryRoute;
import objects.TrainCarCard;
import objects.TrainCarDeck;
import objects.TrainCarHand;
import objects.TrainColor;
import objects.TunnelRoute;
import objects.interfaces.ICard;
import objects.interfaces.IPlayer;
import objects.interfaces.IRoute;
import utils.CardTurnEndManager;
import utils.GameState;
import utils.GraphHelper;
import utils.GameState.CardManager;
import utils.exceptions.DestinationAfterTrainException;
import utils.exceptions.DestinationHasStationException;
import utils.exceptions.NotEnoughCardsForRouteException;
import utils.exceptions.OutOfStationsException;
import utils.exceptions.RouteAfterTrainException;
import utils.exceptions.RouteOwnedException;

public class AbstractPlayer implements IPlayer {

	public static final int MAX_NUM_STATIONS = 3;
	public static final int MAX_NUM_TRAINS = 45;

	protected final String name;
	protected int numTrains;
	protected int numStations;
	protected TrainCarHand hand = new TrainCarHand();
	protected DestinationHand destinationHand = new DestinationHand();
	protected List<IRoute> routes = new ArrayList<IRoute>();
	
	private CardTurnEndManager cardTurnEndManager = new CardTurnEndManager();

	public AbstractPlayer() {
		this("New Player");
	}

	public AbstractPlayer(String name) {
		this.name = name;
		this.numTrains = MAX_NUM_TRAINS;
		this.numStations = MAX_NUM_STATIONS;
	}

	public boolean canDrawTrainCard() {

		return cardTurnEndManager.canDrawTrainCard();

	}
	

	public boolean canDrawDestination() {

		return cardTurnEndManager.canDrawDestinationCard();
		
	}

	@Override
	public void drawCardFromDeck(TrainCarDeck deck) {
		
		if (deck.isEmpty()) {
			deck.reFillFromDiscard();
		}
		
		TrainCarCard card = deck.draw();

		this.hand.addCard(card);
		this.cardTurnEndManager.addCardForCurrentTurn(new CardPackage(card, CardPackage.DECK));

		// end turn if collected 2 trains (or one rainbow)

		if (!this.canDrawTrainCard()) {
			this.cardTurnEndManager.endTurn();

			GameState.takeTurn();
		}
	}

	@Override
	public void drawCardFromDeck(DestinationDeck deck) throws DestinationAfterTrainException {
		if (canDrawDestination()) {
			this.destinationHand.addCard(deck.draw());

			// end turn when drawing destination
			this.cardTurnEndManager.endTurn();
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
		this.cardTurnEndManager.addCardForCurrentTurn(new CardPackage(pickedCard, CardPackage.DEAL));

		// end turn if collected 2 trains (or one rainbow)
		if (!this.canDrawTrainCard()) {
			this.cardTurnEndManager.endTurn();

			GameState.takeTurn();
		}
	}

	@Override
	public void claimRoute(IRoute route) throws NotEnoughCardsForRouteException, RouteOwnedException, RouteAfterTrainException {
		if (this.routes.contains(route))
		{
			throw new RouteOwnedException();
		}

		if (canDrawDestination()) {
			if (route instanceof DrawableRoute) {
				route = ((DrawableRoute) route).getRoute();
			}

			if (route instanceof FerryRoute) {
				claimFerryRoute((FerryRoute) route);
			} else if (route instanceof TunnelRoute) {
				claimTunnelRoute((TunnelRoute) route);
			} else {
				claimTrainRoute(route);
			}
		} else {
			throw new RouteAfterTrainException();
		}


	}

	private void claimTrainRoute(IRoute route) throws NotEnoughCardsForRouteException {
		TrainColor routeColor = (route instanceof AbstractColorableRoute) ? ((AbstractColorableRoute) route)
				.getColor() : TrainColor.RAINBOW;

				int numberOfColorInHand = this.hand.numInHand(routeColor);
				int numberOfRainbowInHand = this.hand.numInHand(TrainColor.RAINBOW);
				int routeLength = route.getLength();
				if (numberOfColorInHand >= routeLength) {
					discardCardsOfColor(routeLength, routeColor);
					addRoute(route);
					this.numTrains -= routeLength;
				} else {
					System.err.println(
							"Not enough cards for route!\nYou have "
									+ numberOfColorInHand + " " + routeColor
									+ " but the route is worth " + routeLength);
					throw new NotEnoughCardsForRouteException();
				}
	}

	private void claimFerryRoute(FerryRoute route) throws NotEnoughCardsForRouteException {

		if (hasEnoughCardsForFerry(route)) {

			TrainColor[] cardChoices = getFerryCardChoices(route.getLength() - route.getLocomotiveCount());
			String[] cardChoicesStrings = listColorsToString(cardChoices);

			int response = JOptionPane.showOptionDialog(null,
					"What color cards would you like to use?",
					"Ferry",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE,
					null,
					cardChoicesStrings,
					cardChoicesStrings[0]);

			discardCardsOfColor(route.getLength() - route.getLocomotiveCount(), cardChoices[response]);
			discardCardsOfColor(route.getLocomotiveCount(), TrainColor.RAINBOW);
			addRoute(route);
			this.numTrains -= route.getLength();

		} else {
			System.err.println(
					"Not enough cards for route!"
							+ " The route is worth " + route.getLength());
			throw new NotEnoughCardsForRouteException();
		}
	}

	public boolean hasEnoughCardsForFerry(FerryRoute route) {

		int numRainbowNeeded = route.getLocomotiveCount();

		int numOtherNeeded = route.getLength() - numRainbowNeeded;

		if (numRainbowNeeded <= this.hand.numInHand(TrainColor.RAINBOW)) {
			for (TrainColor color : TrainColor.getAllColors()) {
				if (this.hand.numInHand(color) >= numOtherNeeded) {
					return true;
				}
			}
		}

		return false;
	}

	public String[] listColorsToString(TrainColor... cardChoices) {
		String[] colorStrings = new String[cardChoices.length];

		for (int i = 0; i < colorStrings.length; i++) {

			String colorString;

			if (cardChoices[i] == TrainColor.RAINBOW) {
				colorString = "RAINBOW";
			} else {

				colorString = cardChoices[i].toString();

			}

			colorStrings[i] = colorString;
		}
		return colorStrings;
	}

	public TrainColor[] getFerryCardChoices(int numCardsNeeded) {

		List<TrainColor> cards = new ArrayList<TrainColor>();

		for (TrainColor color : TrainColor.getAllColors()) {
			if (numCardsNeeded <= this.hand.numInHand(color)) {
				cards.add(color);
			}
		}

		TrainColor[] cardsArray = new TrainColor[cards.size()];
		cardsArray = cards.toArray(cardsArray);

		return cardsArray;
	}

	private void claimTunnelRoute(TunnelRoute route) throws NotEnoughCardsForRouteException {

		TrainColor routeColor = route.getColor();
		TrainColor[] tunnelChoicesArray = new TrainColor[1];
		int firstresponse = 0;
		

		if (hasEnoughCardsForTunnelRoute(route, routeColor)) {
			
			if (route.getColor() == TrainColor.RAINBOW) {
				
				List<TrainColor> tunnelChoices = tunnelColorSelectionWhenRainbow(route);
				
				tunnelChoicesArray = new TrainColor[tunnelChoices.size()];
				tunnelChoicesArray = tunnelChoices.toArray(tunnelChoicesArray);
				
				String[] tunnelColorChoices = listColorsToString(tunnelChoicesArray);
				
				firstresponse = JOptionPane.showOptionDialog(null,
					"What color cards would you like to use?",
					"Ferry",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE,
					null,
					tunnelColorChoices,
					tunnelColorChoices[0]);
			}

			List<TrainCarCard> topThreeCards = GameState.getCardManager().getTrainCarDeck().drawTopThree();

			int numExtraCards = routeColorMatched(routeColor, topThreeCards);

			while (!topThreeCards.isEmpty()) {
				GameState.getCardManager().discard(topThreeCards.remove(0));
			}

			if (numExtraCards > 0) {

				if ((this.hand.numInHand(routeColor) - route.getLength()) >= numExtraCards) {

					String[] tunnelChoices = null;
					
					if (routeColor == TrainColor.RAINBOW) {
						tunnelChoices = getTunnelChoices(tunnelChoicesArray[firstresponse], numExtraCards);

					} else {
						tunnelChoices = getTunnelChoices(routeColor, numExtraCards);

					}
					
					String needExtra = String.format("Need %d more cards", numExtraCards);

					int response = JOptionPane.showOptionDialog(null,
							needExtra,
							"Tunnel",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.INFORMATION_MESSAGE,
							null,
							tunnelChoices,
							tunnelChoices[0]);

					switch (response) {
					case JOptionPane.CANCEL_OPTION:
						//TODO: CANCEL!!
						//	GameState.takeTurn();
						break;
					case JOptionPane.YES_OPTION:
						// use routeColor
						if (routeColor == TrainColor.RAINBOW) {
							discardCardsOfColor(route.getLength(), tunnelChoicesArray[firstresponse]);
							discardCardsOfColor(numExtraCards, tunnelChoicesArray[firstresponse]);
						} else {
							discardCardsOfColor(route.getLength(), routeColor);
							discardCardsOfColor(numExtraCards, routeColor);
						}
						addRoute(route);
						this.numTrains -= route.getLength();
						break;
					default:
						//use rainbow
						
						if (routeColor == TrainColor.RAINBOW) {
							discardCardsOfColor(route.getLength(), tunnelChoicesArray[firstresponse]);
						} else {
							discardCardsOfColor(route.getLength(), routeColor);
						}
						
						discardCardsOfColor(route.getLength(), routeColor);
						discardCardsOfColor(numExtraCards, TrainColor.RAINBOW);
						addRoute(route);
						this.numTrains -= route.getLength();
						break;
					}

				} else {
					throw new NotEnoughCardsForRouteException();
				}

			} else {
				if (routeColor == TrainColor.RAINBOW) {
					
					discardCardsOfColor(route.getLength(), tunnelChoicesArray[firstresponse]);
					
				} else {
				
					discardCardsOfColor(route.getLength(), routeColor);
					
				}
				
				addRoute(route);
				this.numTrains -= route.getLength();
			}

		} else {
			System.err.println(
					"Not enough cards for route!"
							+ " The route is worth " + route.getLength());
			throw new NotEnoughCardsForRouteException();
		}

	}
	
	public List<TrainColor> tunnelColorSelectionWhenRainbow(TunnelRoute route) {
		
		List<TrainColor> colors = new ArrayList<TrainColor>();
		
		for (TrainColor color : TrainColor.getAllColors()) {
			if (this.hand.numInHand(color) >= route.length) {
				
				colors.add(color);
			}
		}
		
		
		return colors;
		
	}

	public boolean hasEnoughCardsForTunnelRoute(IRoute route, TrainColor routeColor) {
		if (routeColor == TrainColor.RAINBOW) {
			for (TrainColor color : TrainColor.getAllColors()) {
				if (route.getLength() >= this.hand.numInHand(color)) {
					return true;
				}
			}
		} else {
		
			if (route.getLength() <= this.hand.numInHand(routeColor)) {
				return true;
			}
		}
		return false;
	}

	public String[] getTunnelChoices(TrainColor routeColor, int numExtraCards) {

		List<String> tunnelChoices = new ArrayList<String>();

		tunnelChoices.add("CANCEL");

		int numRainbow = this.hand.numInHand(TrainColor.RAINBOW);

		if (routeColor != TrainColor.RAINBOW) {
			tunnelChoices.add(routeColor.toString());
		} else {
			tunnelChoices.add("RAINBOW");
		}

		if (numRainbow >= numExtraCards) {
			tunnelChoices.add("RAINBOW");
		}

		String[] tunnelChoicesString = new String[tunnelChoices.size()];
		tunnelChoicesString = tunnelChoices.toArray(tunnelChoicesString);

		return tunnelChoicesString;
	}

	public int routeColorMatched(TrainColor routeColor,
			List<TrainCarCard> topThreeCards) {

		int numCardsMatched = 0;

		for (TrainCarCard card : topThreeCards) {
			if (card.getColor() == routeColor) {
				numCardsMatched++;
			}
		}
		return numCardsMatched;
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
