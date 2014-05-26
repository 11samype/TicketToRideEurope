package utils;

import gui.drawables.DrawableDestination;
import gui.drawables.DrawableRoute;
import gui.interfaces.IRefreshable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.swing.JOptionPane;

import objects.Destination;
import objects.DestinationCard;
import objects.DestinationDeck;
import objects.DestinationRoute;
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
import utils.exceptions.RouteAfterTrainException;
import utils.exceptions.RouteOwnedException;
import utils.exceptions.RouteTakenException;

public class GameState {
	private static GameState sInstance;
	public static int MAX_PLAYERS = 5;
	public static int numPlayers = 4;
	private CardManager cardManager;
	private TurnManager turnManager;
	private IRefreshable gameGUI;
	private static int turnCount = Integer.MAX_VALUE;

	public static Queue<TrainColor> availableColors = new LinkedList<TrainColor>(
			Arrays.asList(TrainColor.WHITE, TrainColor.ORANGE,
					TrainColor.GREEN, TrainColor.RED, TrainColor.YELLOW));

	public static GameState getInstance() {
		if (sInstance == null) {
			sInstance = new GameState();
		}
		return sInstance;
	}

	private GameState() {
		this(new ArrayList<Player>());
	}

	private GameState(List<Player> players) {
		availableColors = new LinkedList<TrainColor>(
				Arrays.asList(TrainColor.WHITE, TrainColor.ORANGE,
						TrainColor.GREEN, TrainColor.RED, TrainColor.YELLOW));

		
		this.cardManager = new CardManager();
		this.turnManager = new TurnManager(players);
	
		dealTrainsToPlayers(players);
		dealDestinationsToPlayers(players);
	}

	private void dealDestinationsToPlayers(List<Player> players) {
		// take 6 longest destination cards
		Collections.sort(cardManager.getDestinationDeck().getCards(), new Comparator<DestinationCard>() {

			@Override
			public int compare(DestinationCard c1, DestinationCard c2) {
				return -1 * new Integer(c1.getScore()).compareTo(new Integer(c2.getScore()));
			}

		});

		// remove and shuffle top 6
		List<DestinationCard> top6 = new ArrayList<DestinationCard>();
		for (int i = 0; i < 6; i++) {
			top6.add(cardManager.getDestinationDeck().draw());
		}
		Collections.shuffle(top6);

		// deal one to each player
		for (IPlayer iPlayer : players) {
			Player player = (Player) iPlayer;
			player.getDestinationHand().add(top6.remove(0));
		}

		// shuffle rest of cards
		Collections.shuffle(cardManager.getDestinationDeck().getCards());

		// deal 3 cards to each player
		for (IPlayer iPlayer : players) {
			Player player = (Player) iPlayer;
			for (int i = 0; i < 3; i++) {
				player.getDestinationHand().add(cardManager.getDestinationDeck().draw());
			}
		}
	}

	private void dealTrainsToPlayers(List<Player> players) {
		for (IPlayer iPlayer : players) {
			Player player = (Player) iPlayer;
			for (int i = 0; i < 4; i++) {
				player.getHand().addCard(cardManager.getTrainCarDeck().draw());
			}
		}
	}

	public static GameState withPlayers(List<Player> players) {
		sInstance = new GameState(players);
		numPlayers = players.size();
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
		
		if (turnCount <= 0) {
			endGame();
			return;
		}
		turnCount--;
		
		Player current = GameState.getCurrentPlayer();
		if (current.getNumTrains() <= 2) {
			turnCount = getPlayers().size() - 1;
		}
		
		
		GameState.getTurnManager().rotatePlayers();
		refreshGUI();
	}
	
	private static void endGame() {
		
		List<Player> players = getPlayers();
		
		IPlayer winner = new Player();
		
		for (IPlayer player : players) {
			
			if (player.getScore() > winner.getScore()) {
				winner = player;
			}
			
		}
		
		String winnerString = String.format("%s WINS!", winner.getName());
		
		//wait?
		
		int response = JOptionPane.showConfirmDialog(null,
				winnerString,
				"Winner",
				JOptionPane.OK_OPTION,
				JOptionPane.INFORMATION_MESSAGE
				);
		
		System.exit(0);
		
	}

//	private static void startCountDown() {
//		countDown = true;
//		count = numPlayers;
//
//	}

	public static void refreshGUI() {
		if (getInstance().gameGUI != null)
			getInstance().gameGUI.refresh();
	}

	public static List<Player> getPlayers() {
		return GameState.getTurnManager().getPlayers();
	}

	public static Player getCurrentPlayer() {
		return (Player) GameState.getTurnManager().getCurrentPlayer();
	}


	public static void initializeGameData(boolean log) {
		readTrainRoutesFile(log);
		readDestinationCardsFile(log);
		readDestinationLocationFile(log);

	}

	private static void readDestinationCardsFile(boolean log) {
		DestinationCardReader reader = DestinationCardReader.getInstance();
		if (log) {
			Set<DestinationRoute> routes = reader.getRoutes();
			int k = 1;
			for (Iterator<DestinationRoute> i = routes.iterator(); i.hasNext();) {
				DestinationRoute d = i.next();
				System.out.printf("[%d] %15s -- %15s (%s)\n", k++,
						d.getStart(), d.getEnd(), d.getScore());
			}
			System.out
			.println("-----------------------------------------------------");
		}

	}

	private static void readDestinationLocationFile(boolean log) {
		DestinationLocationReader reader = DestinationLocationReader
				.getInstance();
		if (log) {
			HashMap<String, DrawableDestination> dests = reader
					.getDestinations();
			int k = 1;
			for (Iterator<String> i = dests.keySet().iterator(); i.hasNext();) {
				DrawableDestination d = dests.get(i.next());
				System.out
				.printf("[%2d] %15s (%.2f, %.2f)\n", k++, d.getName(),
						d.getCenter().getX(), d.getCenter().getY());
			}
			System.out
			.println("----------------------------------------------------");
		}
	}

	private static void readTrainRoutesFile(boolean log) {
		TrainRouteReader reader = TrainRouteReader.getInstance();
		if (log) {
			HashMap<Destination, List<IRoute>> routeGraph = reader.getGraph();
			int k = 1;
			for (Iterator<Destination> i = routeGraph.keySet().iterator(); i
					.hasNext();) {
				Destination d = i.next();
				List<IRoute> routeLst = routeGraph.get(d);
				System.out.printf("[%2d] %15s : %s\n", k++, d, routeLst);
			}
			System.out
			.println("----------------------------------------------------");
		}

	}

	public void claimRoute(Player claimer, IRoute routeToClaim, ArrayList<DrawableRoute> drawablesToAdd)
			throws RouteOwnedException, RouteTakenException, NotEnoughCardsForRouteException, RouteAfterTrainException {
		if (routeToClaim != null) {
			for (IPlayer player : getPlayers()) {
				if (player == claimer) {
					claimer.claimRoute(routeToClaim);
					if (drawablesToAdd != null) {
						drawablesToAdd.add(DrawableRoute.construct(routeToClaim, claimer.getColor(), GraphHelper.DEST_LOC_LOOKUP));
					}
				} else {
					if (player.getRoutes().contains(routeToClaim)) {
						throw new RouteTakenException();
					}
				}
			}

			GameState.takeTurn();
			refreshGUI();

		}
	}


	public void claimRouteBetweenPoints(Player current, SelectionHolder selectedPoints,
			ArrayList<DrawableRoute> drawablesToAdd) throws RouteOwnedException, RouteTakenException, NotEnoughCardsForRouteException, RouteAfterTrainException {
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
			while (!this.deal.isFull()) {
				if (trainCarDeck.isEmpty()) {
					trainCarDeck.populate(GameState.getCardManager().getDiscardPile().pickup());
				}
				try {
					this.deal.addCard(trainCarDeck.draw());
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
					break;
				}
			}
		}

		public TrainCarCard drawDealCard(int index) {
			TrainCarCard cardToReturn = null;
			try {
				cardToReturn = this.deal.removeCardAtPosition(index);
			} catch (NullPointerException e) {
				e.printStackTrace();
			} finally {
				fillDealFromDeck();
			}
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

		private final List<Player> players;
		private int currentPlayerIndex = 0;

		public TurnManager(List<Player> players) {
			this.players = players;
		}

		public void rotatePlayers() {

			this.currentPlayerIndex = (this.currentPlayerIndex + 1)
					% getPlayers().size();
			getCardManager().fillDealFromDeck();
		}

		private List<Player> getPlayers() {
			return this.players;
		}

		private IPlayer getCurrentPlayer() {
			return this.players.get(this.currentPlayerIndex);
		}

	}

	public static List<Player> getPlayersBasedOnNum() {
		List<Player> players = new ArrayList<Player>();
		for (int i = 0; i < numPlayers; i++) {
			players.add(new Player("Player " + (i + 1)));
		}
		return players;
	}
 
}
