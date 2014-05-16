package objects;

import gui.IRefreshable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import objects.abstracts.AbstractDeal;
import objects.interfaces.IDeal;
import objects.interfaces.IDeck;
import objects.interfaces.IPlayer;

public class GameState {
	private static GameState sInstance;
	public static int MAX_PLAYERS = 5;
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
		if (getInstance().gameGUI != null)
			getInstance().gameGUI.refresh();
	}

	public static List<IPlayer> getPlayers() {
		return GameState.getTurnManager().getPlayers();
	}

	public static IPlayer getCurrentPlayer() {
		return GameState.getTurnManager().getCurrentPlayer();
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
