package objects;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import objects.interfaces.IPlayer;

public class GameState {
	private static GameState sInstance;
	private CardManager cardManager;
	private TurnManager turnManager;
	public static final Queue<TrainColor> availableColors = new LinkedList<TrainColor>(
			Arrays.asList(TrainColor.WHITE, TrainColor.ORANGE,
					TrainColor.GREEN, TrainColor.RED, TrainColor.YELLOW));

	public static GameState getInstance() {
		if (sInstance == null)
			sInstance = new GameState();
		return sInstance;
	}

	private GameState() {
		init();
	}

	public void init() {
		// TODO Clear the board, get number of players, deal the cards
		this.cardManager = new CardManager();
	}

	public GameState withPlayers(List<IPlayer> players) {
		GameState game = GameState.getInstance();
		game.turnManager = new TurnManager(players);
		return game;
	}

	public CardManager getCardManager() {
		return this.cardManager;
	}

	public TurnManager getTurnManager() {
		return this.turnManager;
	}

	public static void takeTurn() {
		GameState.getInstance().getTurnManager().rotatePlayers();
	}

	public static List<IPlayer> getPlayers() {
		return GameState.getInstance().getTurnManager().getPlayers();
	}

	public static IPlayer getCurrentPlayer() {
		return GameState.getInstance().getTurnManager().getCurrentPlayer();
	}

	public class CardManager {

		private DestinationDeck destinationDeck;
		private TrainCarDeck trainCarDeck;
		private TrainCarDeal deal;
		private DiscardPile discardPile;

		public CardManager() {
			this.destinationDeck = new DestinationDeck();
			this.trainCarDeck = new TrainCarDeck();
			this.deal = new TrainCarDeal();
			this.discardPile = new DiscardPile();

			fillDealFromDeck();
		}

		/*
		 * returns cards added
		 */
		public void fillDealFromDeck() {
			while (!this.deal.isFull() && !trainCarDeck.isEmpty()) {
				this.deal.addCard(trainCarDeck.draw());
			}

		}

		public TrainCarCard drawDealCard(int index) {
			TrainCarCard cardToReturn = this.deal.removeCardAtPosition(index);
			fillDealFromDeck();
			return cardToReturn;
		}

		public TrainCarCard getDealCard(int index) {
			return getDealCards().getCardAtPosition(index);
		}

		public TrainCarDeal getDealCards() {
			return this.deal;
		}

		public TrainCarDeck getTrainCarDeck() {
			return this.trainCarDeck;
		}

		public DestinationDeck getDestinationDeck() {
			return this.destinationDeck;
		}

		public DiscardPile getDiscardPile() {
			return this.discardPile;
		}
	}

	public class TurnManager {

		private final List<IPlayer> players;
		private int currentPlayerIndex = 0;

		public TurnManager(List<IPlayer> players) {
			this.players = players;
		}

		public void rotatePlayers() {
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
