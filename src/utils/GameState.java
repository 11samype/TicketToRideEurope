package utils;

import gui.drawables.DrawableRoute;
import gui.interfaces.IRefreshable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import objects.Destination;
import objects.DestinationCard;
import objects.DestinationDeck;
import objects.DiscardPile;
import objects.Player;
import objects.TrainCarCard;
import objects.TrainCarDeal;
import objects.TrainCarDeck;
import objects.TrainColor;
import objects.abstracts.AbstractDeal;
import objects.interfaces.IDeal;
import objects.interfaces.IDeck;
import objects.interfaces.IPlayer;
import objects.interfaces.IRoute;
import utils.exceptions.NotEnoughCardsForRouteException;
import utils.exceptions.RouteOwnedException;
import utils.exceptions.RouteTakenException;

public class GameState {
	private static GameState sInstance;
	public static int MAX_PLAYERS = 5;
	public static int numPlayers = 1;
	private CardManager cardManager;
	private TurnManager turnManager;
	private IRefreshable gameGUI;
	
	public static final Queue<TrainColor> availableColors = new LinkedList<TrainColor>(
			Arrays.asList(TrainColor.WHITE, TrainColor.ORANGE,
					TrainColor.GREEN, TrainColor.RED, TrainColor.YELLOW));

	public static GameState getInstance() {
		if (sInstance == null) {
			sInstance = new GameState();
		}
		return sInstance;
	}

	private GameState() {
		this(new ArrayList<IPlayer>());
	}

	private GameState(List<IPlayer> players) {
		this.cardManager = new CardManager();
		this.turnManager = new TurnManager(players);
	}

	public static GameState withPlayers(List<IPlayer> players) {
		sInstance = new GameState(players);
		return getInstance();
	}

	public static GameState withGUI(IRefreshable gui) {
		getInstance().gameGUI = gui;
		return sInstance;
	}

	public static CardManager getCardManager() {
		return GameState.getInstance().cardManager;
	}

	public static TurnManager getTurnManager() {
		return GameState.getInstance().turnManager;
	}

	public static void takeTurn() {
		GameState.getTurnManager().rotatePlayers();
		refreshGUI();
	}
	
	public static void refreshGUI() {
		if (getInstance().gameGUI != null)
			getInstance().gameGUI.refresh();
	}

	public static List<IPlayer> getPlayers() {
		return GameState.getTurnManager().getPlayers();
	}

	public static Player getCurrentPlayer() {
		return (Player) GameState.getTurnManager().getCurrentPlayer();
	}

	public void claimRoute(Player claimer, IRoute routeToClaim, ArrayList<DrawableRoute> drawablesToAdd)
			throws RouteOwnedException, RouteTakenException, NotEnoughCardsForRouteException {
		if (routeToClaim != null) {
			for (IPlayer player : getPlayers()) {
				if (player == claimer) {
					claimer.claimRoute(routeToClaim);
					if (drawablesToAdd != null) {
						drawablesToAdd.add(DrawableRoute.constructFromRoute(routeToClaim, claimer.getColor(), GraphHelper.DEST_LOC_LOOKUP));
					}
				} else {
					if (player.getRoutes().contains(routeToClaim)) {
						throw new RouteTakenException();
					}
				}
			}
			
			refreshGUI();
		}
	}


	public void claimRouteBewteenPoints(Player current, SelectionHolder selectedPoints,
			ArrayList<DrawableRoute> drawablesToAdd) throws RouteOwnedException, RouteTakenException, NotEnoughCardsForRouteException {
		IRoute routeToClaim = GraphHelper.getAdjecentRouteBetween((Destination) selectedPoints.get(0),
				(Destination) selectedPoints.get(1));
		this.claimRoute(current, routeToClaim, drawablesToAdd);
	}

	public class CardManager {

		private IDeck<DestinationCard> destinationDeck;
		private IDeck<TrainCarCard> trainCarDeck;
		private IDeal<TrainCarCard> deal;
		private IDeck<TrainCarCard> discardPile;

		public CardManager() {
			this.destinationDeck = new DestinationDeck();
			this.trainCarDeck = new TrainCarDeck();
			this.deal = new TrainCarDeal();
			this.discardPile = new DiscardPile<TrainCarCard>();
			fillDealFromDeck();
		}

		public void fillDealFromDeck() {
			while (!(this.deal.isFull() || trainCarDeck.isEmpty())) {
				this.deal.addCard(trainCarDeck.draw());
			}
		}

		public TrainCarCard drawDealCard(int index) {
			TrainCarCard cardToReturn = this.deal.removeCardAtPosition(index);
			fillDealFromDeck();
			return cardToReturn;
		}

		public TrainCarCard getDealCard(int index) {
			return ((AbstractDeal<TrainCarCard>) getDealCards()).getCardAtPosition(index);
		}

		public TrainCarDeal getDealCards() {
			return (TrainCarDeal) this.deal;
		}

		public void discard(TrainCarCard card) {
			getDiscardPile().add(card);
		}

		public TrainCarDeck getTrainCarDeck() {
			return (TrainCarDeck) this.trainCarDeck;
		}

		public DestinationDeck getDestinationDeck() {
			return (DestinationDeck) this.destinationDeck;
		}

		public DiscardPile<TrainCarCard> getDiscardPile() {
			return (DiscardPile<TrainCarCard>) this.discardPile;
		}
	}

	public class TurnManager {

		private final List<IPlayer> players;
		private int currentPlayerIndex = 0;

		public TurnManager(List<IPlayer> players) {
			this.players = players;
		}

		public void rotatePlayers() {
			((Player) getCurrentPlayer()).setPrevTurnCardNum();
			this.currentPlayerIndex = (this.currentPlayerIndex + 1)
					% getPlayers().size();
		}

		private List<IPlayer> getPlayers() {
			return this.players;
		}

		private IPlayer getCurrentPlayer() {
			return this.players.get(this.currentPlayerIndex);
		}

	}

}
