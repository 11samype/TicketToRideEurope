package gui;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JLabel;

import objects.DestinationDeck;
import objects.GameState;
import objects.GameState.CardManager;
import objects.Player;
import objects.TrainCarDeck;
import objects.TrainCarHand;
import objects.TrainColor;
import objects.interfaces.IPlayer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;

import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.OverlayLayout;
import javax.swing.ScrollPaneConstants;

public class MainPanel extends JPanel {
	private final GameState gameState;
	private CardManager cardManager;

	private PlayerPanel currentPlayerPanel;
	private JPanel mapPanel;

	private HandPanel playerHandPanel;

	private JScrollPane destScrollPane;

	private ArrayList<IPlayer> players = new ArrayList<IPlayer>();
	private JPanel dealPanel;

	private JLabel lblTrainCardCount;

	private JLabel lblDestinationCardCount;

	private DestinationTable destinationTable;

	private JPanel playerInfoPanel;

	private JPanel dealtCardsPanel;

	// private HandCardPanel[] handPanels = new HandCardPanel[9];

	public MainPanel() {
		setLayout(new MigLayout(
				"",
				"[900px:1250px:1600px,grow,fill][10%:n,right]",
				"[90.00:114.00:100.00,grow,fill][773px:773px:773px,fill][70:85.00:100,grow,bottom]"));

		this.players = getPlayers();
		this.gameState = GameState.getInstance().withPlayers(players);
		this.cardManager = gameState.getCardManager();

		addPlayersPanel();
		addDestinationDeckPanel();

		addPlayerInfoPanel();
		addDestinationTable();

		addMapPanel();
		addDealPanel();

		Player currentPlayer = getCurrentPlayer();
		this.currentPlayerPanel = new PlayerPanel(currentPlayer);
		add(this.currentPlayerPanel, "cell 1 2,grow");
	}

	private void addPlayersPanel() {
		JPanel playerPanel = new JPanel();
		add(playerPanel, "cell 0 0,alignx left,growy");
		GridLayout playerPanelLayout = new GridLayout(1, 0, 0, 0);
		playerPanelLayout.setHgap(10);
		playerPanel.setLayout(playerPanelLayout);

		List<IPlayer> players = this.gameState.getPlayers();
		for (int i = 0; i < players.size(); i++) {
			playerPanel.add(new PlayerPanel((Player) players.get(i)));
		}

		playerPanel.add(new JPanel()); // right padding
	}

	private void addDestinationDeckPanel() {
		DestinationDeck destDeck = gameState.getCardManager()
				.getDestinationDeck();

		JPanel destCardDeckPanel = new JPanel();
		destCardDeckPanel.setBackground(new Color(255, 140, 0));
		add(destCardDeckPanel, "cell 1 0,grow");
		destCardDeckPanel.setLayout(new CardLayout());

		lblDestinationCardCount = new JLabel(Integer.toString(destDeck.size()));
		lblDestinationCardCount.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDestinationCardCount.setBounds(10, 11, 27, 20);
		destCardDeckPanel.add(lblDestinationCardCount);
		destCardDeckPanel
				.addMouseListener(new DestinationDeckListener(destDeck));
	}

	private void addMapPanel() {
		this.mapPanel = new JPanel() {
			@Override
			public boolean isOptimizedDrawingEnabled() {
				return false;
			}
		};
		LayoutManager overlay = new OverlayLayout(this.mapPanel);
		this.mapPanel.setLayout(overlay);
		add(this.mapPanel, "cell 0 1,grow");

		this.mapPanel.add(getMapPanel("Europe"));

	}

	private void addDealPanel() {
		this.dealPanel = new JPanel();
		dealPanel.setLayout(new MigLayout("",
				"[130px:n,growprio 20,grow 50,fill]", "[80%:80%,fill][278px]"));
		add(dealPanel, "cell 1 1,grow");

		this.dealtCardsPanel = new JPanel();
		dealtCardsPanel.setLayout(new BoxLayout(dealtCardsPanel,
				BoxLayout.Y_AXIS));
		dealPanel.add(dealtCardsPanel, "cell 0 0,grow");

		addTrainCarDeckPanel();

		dealCardsToDealPanel();
	}

	private void addTrainCarDeckPanel() {
		TrainCarDeck carDeck = cardManager.getTrainCarDeck();

		JPanel trainCardDeckPanel = new JPanel();
		trainCardDeckPanel.setBackground(new Color(30, 144, 255));

		lblTrainCardCount = new JLabel(Integer.toString(carDeck.size()));
		lblTrainCardCount.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTrainCardCount.setBounds(10, 11, 27, 20);
		trainCardDeckPanel.add(lblTrainCardCount);
		trainCardDeckPanel.addMouseListener(new TrainCarDeckListener(carDeck));
		dealPanel.add(trainCardDeckPanel, "cell 0 1,grow");

	}

	private void dealCardsToDealPanel() {
		for (int i = 0; i < 5; i++) {
			DrawableTrainCarCard card = new DrawableTrainCarCard(cardManager
					.getDealCard(i).getColor());
			DealtCardPanel cardPanel = new DealtCardPanel();
			cardPanel.setCard(card);
			dealtCardsPanel.add(cardPanel);
		}
	}

	private void addPlayerInfoPanel() {
		playerInfoPanel = new JPanel();
		playerInfoPanel.setBackground(Color.LIGHT_GRAY);
		playerInfoPanel.setLayout(new MigLayout("", "[30%:n,fill][grow]",
				"[100px:n:100px]"));
		this.add(playerInfoPanel, "cell 0 2,grow");

		addHandPanel();
	}

	private void addDestinationTable() {
		this.destinationTable = new DestinationTable();
		this.destinationTable.setPlayer(getCurrentPlayer());

		this.destScrollPane = new JScrollPane(this.destinationTable);
		destScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.destinationTable.setFillsViewportHeight(true);
		this.playerInfoPanel.add(destScrollPane, "cell 0 0,grow");

	}

	private void addHandPanel() {
		this.playerHandPanel = new HandPanel();
		this.playerInfoPanel.add(this.playerHandPanel, "cell 1 0,grow");
		this.playerHandPanel.setPlayer(getCurrentPlayer());
	}

	private class TrainCarDeckListener extends MouseAdapter {

		private TrainCarDeck deck;

		public TrainCarDeckListener(TrainCarDeck trainCarDeck) {
			this.deck = trainCarDeck;
		}

		private void simulateDrawCard() {
			if (deck.size() > 0) {
				Player current = getCurrentPlayer();
				current.drawCardFromDeck(deck);
				playerHandPanel.setPlayer(current);
				lblTrainCardCount.setText(Integer.toString(deck.size()));
			}
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			simulateDrawCard();
		}
	}

	private class DestinationDeckListener extends MouseAdapter {

		private final DestinationDeck deck;

		public DestinationDeckListener(DestinationDeck destDeck) {
			this.deck = destDeck;
		}

		private void simulateDrawCard() {
			Player current = getCurrentPlayer();
			current.drawCardFromDeck(this.deck);
			lblDestinationCardCount.setText(Integer.toString(this.deck.size()));
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// FIXME
			// FOR DEMO PURPOSES
			// SWITCH PLAYER WHENEVER DEST CARD CHOSEN

			if (!this.deck.isEmpty()) {
				simulateDrawCard();
				nextTurn();
			}
		}
	}

	private MapPanel getMapPanel(String mapName) {
		MapPanel mapPanel = new MapPanel(playerHandPanel);
		mapPanel.setMapName(mapName);
		return mapPanel;
	}

	public void setMapPanel(MapPanel panel) {
		this.mapPanel = panel;
	}

	public Player getCurrentPlayer() {
		return (Player) this.gameState.getCurrentPlayer();
	}

	public void nextTurn() {
		gameState.takeTurn();
		destinationTable.setPlayer(getCurrentPlayer());
		playerHandPanel.setPlayer(getCurrentPlayer());
		currentPlayerPanel.setPlayer(getCurrentPlayer());
		// TODO: Refill deal cards
	}

	// TODO: Get the players rather than hard-code them
	private ArrayList<IPlayer> getPlayers() {
		ArrayList<IPlayer> players = new ArrayList<IPlayer>();
		players.add(new Player("Alice"));
		players.add(new Player("Bob"));
		players.add(new Player("Charlie"));
		players.add(new Player("Dan"));
		players.add(new Player("Jeff"));
		return players;
	}

}
