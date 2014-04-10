package objects;

import java.util.ArrayList;
import java.util.List;

import managers.TurnManager;
import objects.interfaces.IPlayer;

public class GameState {
	private static GameState sInstance;
	private CardManager cardManager;
	private TurnManager turnManager;

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

	public void takeTurn() {
		getTurnManager().rotatePlayers();
	}

	public List<IPlayer> getPlayers() {
		return GameState.getInstance().getTurnManager().getPlayers();
	}

	public IPlayer getCurrentPlayer() {
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

		public void fillDealFromDeck() {
			while (!this.deal.isDealFull()) {
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

		public void dealTrainCarCardToPlayer(Player player) {
			player.drawCardFromDeck(this.trainCarDeck);
		}

		public void dealDestinationCardToPlayer(Player player) {
			player.drawCardFromDeck(this.destinationDeck);
		}
	}

	public class TurnManager {

		private final List<IPlayer> players;
		private int currentPlayerIndex = 0;

		public TurnManager(List<IPlayer> players) {
			this.players = players;
		}

		public void rotatePlayers() {
			this.currentPlayerIndex = (this.currentPlayerIndex + 1) % getPlayers().size();
		}

		private List<IPlayer> getPlayers() {
			return this.players;
		}

		private IPlayer getCurrentPlayer() {
			return this.players.get(this.currentPlayerIndex);
		}

	}

}
