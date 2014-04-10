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

	private PlayerPanel currentPlayerPanel;
	private JPanel mapPanel;

	private HandCardPanel blackPanel;
	private HandCardPanel whitePanel;
	private HandCardPanel redPanel;
	private HandCardPanel greenPanel;
	private HandCardPanel bluePanel;
	private HandCardPanel yellowPanel;
	private HandCardPanel purplePanel;
	private HandCardPanel orangePanel;

	private JPanel playerHandPanel;

	// private GameManager gameManager;

	private ArrayList<IPlayer> players = new ArrayList<IPlayer>();
	private JPanel dealPanel;

	private CardManager cardManager;

	private JLabel lblTrainCardCount;

	private JLabel lblDestinationCardCount;

	private JTable destinationTable;

	private JPanel playerInfoPanel;

	public HandCardPanel rainbowPanel;

	private JPanel dealtCardsPanel;

	public MainPanel() {
		setLayout(new MigLayout(
				"",
				"[900px:1200px:1600px,grow,fill][10%:n,right]",
				"[90.00:114.00:100.00,grow,fill][773px:773px:773px,fill][70:85.00:100,grow,bottom]"));

		this.players = getPlayers();
		this.gameState = GameState.getInstance().withPlayers(players);
		this.cardManager = gameState.getCardManager();

		addPlayersPanel();
		addDestinationDeckPanel();

		addMapPanel();
		addDealPanel();

		addPlayerInfoPanel();
		addDestinationTable();

		Player currentPlayer = (Player) gameState.getCurrentPlayer();
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
		DrawableTrainCarCard card1 = new DrawableTrainCarCard(cardManager
				.getDealCard(0).getColor());
		DealtCardPanel cardPanel1 = new DealtCardPanel();
		cardPanel1.setCard(card1);
		dealtCardsPanel.add(cardPanel1);

		DrawableTrainCarCard card2 = new DrawableTrainCarCard(cardManager
				.getDealCard(1).getColor());
		DealtCardPanel cardPanel2 = new DealtCardPanel();
		cardPanel2.setCard(card2);
		dealtCardsPanel.add(cardPanel2);

		DrawableTrainCarCard card3 = new DrawableTrainCarCard(cardManager
				.getDealCard(2).getColor());
		DealtCardPanel cardPanel3 = new DealtCardPanel();
		cardPanel3.setCard(card3);
		dealtCardsPanel.add(cardPanel3);

		DrawableTrainCarCard card4 = new DrawableTrainCarCard(cardManager
				.getDealCard(3).getColor());
		DealtCardPanel cardPanel4 = new DealtCardPanel();
		cardPanel4.setCard(card4);
		dealtCardsPanel.add(cardPanel4);

		DrawableTrainCarCard card5 = new DrawableTrainCarCard(cardManager
				.getDealCard(4).getColor());
		DealtCardPanel cardPanel5 = new DealtCardPanel();
		cardPanel5.setCard(card5);
		dealtCardsPanel.add(cardPanel5);

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
		Object[][] rowData = { { "Start", "End", "Value" } };
		Object[] columnNames = { "Destination Start", "Destination End",
				"Points" };
		destinationTable = new JTable(rowData, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		JScrollPane destScrollPane = new JScrollPane(destinationTable);
		destScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		destinationTable.setFillsViewportHeight(true);
		playerInfoPanel.add(destScrollPane, "cell 0 0,grow");

	}

	private void addHandPanel() {
		this.playerHandPanel = new JPanel();
		this.playerHandPanel.setLayout(new BoxLayout(playerHandPanel,
				BoxLayout.X_AXIS));
		playerInfoPanel.add(this.playerHandPanel, "cell 1 0,grow");

		// for (TrainColor color : TrainColor.getAllColors()) {
		// HandCardPanel handCardPanel = new HandCardPanel(color);
		// handCardPanel.setNumCards(this.gameManager.getDeck().numInDeck(color));
		// panel_1.add(new HandCardPanel(color));
		//
		// }

		Player currentPlayer = (Player) gameState.getCurrentPlayer();
		TrainCarHand hand = currentPlayer.getHand();

		this.blackPanel = new HandCardPanel(TrainColor.BLACK);
		this.blackPanel.setNumCards(hand.numInHand(TrainColor.BLACK));
		this.playerHandPanel.add(this.blackPanel);

		this.whitePanel = new HandCardPanel(TrainColor.WHITE);
		this.whitePanel.setNumCards(hand.numInHand(TrainColor.WHITE));
		this.playerHandPanel.add(this.whitePanel);

		this.redPanel = new HandCardPanel(TrainColor.RED);
		this.redPanel.setNumCards(hand.numInHand(TrainColor.RED));
		this.playerHandPanel.add(this.redPanel);

		this.greenPanel = new HandCardPanel(TrainColor.GREEN);
		this.greenPanel.setNumCards(hand.numInHand(TrainColor.GREEN));
		this.playerHandPanel.add(this.greenPanel);

		this.bluePanel = new HandCardPanel(TrainColor.BLUE);
		this.bluePanel.setNumCards(hand.numInHand(TrainColor.BLUE));
		this.playerHandPanel.add(this.bluePanel);

		this.yellowPanel = new HandCardPanel(TrainColor.YELLOW);
		this.yellowPanel.setNumCards(hand.numInHand(TrainColor.YELLOW));
		this.playerHandPanel.add(this.yellowPanel);

		this.purplePanel = new HandCardPanel(TrainColor.PINK);
		this.purplePanel.setNumCards(hand.numInHand(TrainColor.PINK));
		this.playerHandPanel.add(this.purplePanel);

		this.orangePanel = new HandCardPanel(TrainColor.ORANGE);
		this.orangePanel.setNumCards(hand.numInHand(TrainColor.ORANGE));
		this.playerHandPanel.add(this.orangePanel);

		this.rainbowPanel = new HandCardPanel(TrainColor.RAINBOW);
		this.rainbowPanel.setNumCards(hand.numInHand(TrainColor.RAINBOW));
		this.playerHandPanel.add(this.rainbowPanel);

	}

	private class TrainCarDeckListener extends MouseAdapter {

		private TrainCarDeck deck;

		public TrainCarDeckListener(TrainCarDeck trainCarDeck) {
			this.deck = trainCarDeck;
		}

		private void simulateDrawCard() {
			if (deck.size() > 0) {
				Player current = (Player) gameState.getCurrentPlayer();
				current.drawCardFromDeck(deck);
				lblDestinationCardCount.setText(Integer.toString(deck.size()));
			}
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// int cardsLeft =
			// Integer.parseInt(lblTrainCardCount.getText());
			// if (cardsLeft > 0) {
			// lblTrainCardCount.setText(Integer.toString(cardsLeft - 1));
			// }

			playerHandPanel.remove(MainPanel.this.blackPanel);
			playerHandPanel.remove(MainPanel.this.whitePanel);
			playerHandPanel.remove(MainPanel.this.redPanel);
			playerHandPanel.remove(MainPanel.this.greenPanel);
			playerHandPanel.remove(MainPanel.this.bluePanel);
			playerHandPanel.remove(MainPanel.this.yellowPanel);
			playerHandPanel.remove(MainPanel.this.purplePanel);
			playerHandPanel.remove(MainPanel.this.orangePanel);
			playerHandPanel.remove(MainPanel.this.rainbowPanel);

			simulateDrawCard();

			Player currentPlayer = (Player) gameState.getCurrentPlayer();
			TrainCarHand hand = currentPlayer.getHand();

			MainPanel.this.blackPanel = new HandCardPanel(TrainColor.BLACK);
			MainPanel.this.blackPanel.setNumCards(hand
					.numInHand(TrainColor.BLACK));
			MainPanel.this.playerHandPanel.add(MainPanel.this.blackPanel);

			MainPanel.this.whitePanel = new HandCardPanel(TrainColor.WHITE);
			MainPanel.this.whitePanel.setNumCards(hand
					.numInHand(TrainColor.WHITE));
			MainPanel.this.playerHandPanel.add(MainPanel.this.whitePanel);

			MainPanel.this.redPanel = new HandCardPanel(TrainColor.RED);
			MainPanel.this.redPanel.setNumCards(hand.numInHand(TrainColor.RED));
			MainPanel.this.playerHandPanel.add(MainPanel.this.redPanel);

			MainPanel.this.greenPanel = new HandCardPanel(TrainColor.GREEN);
			MainPanel.this.greenPanel.setNumCards(hand
					.numInHand(TrainColor.GREEN));
			MainPanel.this.playerHandPanel.add(MainPanel.this.greenPanel);

			MainPanel.this.bluePanel = new HandCardPanel(TrainColor.BLUE);
			MainPanel.this.bluePanel.setNumCards(hand
					.numInHand(TrainColor.BLUE));
			MainPanel.this.playerHandPanel.add(MainPanel.this.bluePanel);

			MainPanel.this.yellowPanel = new HandCardPanel(TrainColor.YELLOW);
			MainPanel.this.yellowPanel.setNumCards(hand
					.numInHand(TrainColor.YELLOW));
			MainPanel.this.playerHandPanel.add(MainPanel.this.yellowPanel);

			MainPanel.this.purplePanel = new HandCardPanel(TrainColor.PINK);
			MainPanel.this.purplePanel.setNumCards(hand
					.numInHand(TrainColor.PINK));
			MainPanel.this.playerHandPanel.add(MainPanel.this.purplePanel);

			MainPanel.this.orangePanel = new HandCardPanel(TrainColor.ORANGE);
			MainPanel.this.orangePanel.setNumCards(hand
					.numInHand(TrainColor.ORANGE));
			MainPanel.this.playerHandPanel.add(MainPanel.this.orangePanel);

			MainPanel.this.rainbowPanel = new HandCardPanel(TrainColor.RAINBOW);
			MainPanel.this.rainbowPanel.setNumCards(hand
					.numInHand(TrainColor.RAINBOW));
			MainPanel.this.playerHandPanel.add(MainPanel.this.rainbowPanel);

			lblTrainCardCount.setText(Integer.toString(cardManager
					.getTrainCarDeck().size()));
		}
	}

	private class DestinationDeckListener extends MouseAdapter {

		private final DestinationDeck deck;

		public DestinationDeckListener(DestinationDeck destDeck) {
			this.deck = destDeck;
		}

		private void simulateDrawCard() {
			if (deck.size() > 0) {
				Player current = (Player) gameState.getCurrentPlayer();
				current.drawCardFromDeck(deck);
				lblDestinationCardCount.setText(Integer.toString(deck.size()));
			}
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// FIXME
			// FOR DEMO PURPOSES
			// SWITCH PLAYER WHENEVER DEST CARD CHOSEN

			simulateDrawCard();

			// remove(currentPlayerPanel);
			// turnManager.nextPlayer();

			if (!deck.isEmpty()) {
				gameState.getTurnManager().rotatePlayers();
				currentPlayerPanel.setPlayer(gameState.getCurrentPlayer());
			}

			// MainPanel.this.currentPlayerPanel = new PlayerPanel(
			// MainPanel.this.turnManager.getCurrentPlayer());

			// add(MainPanel.this.currentPlayerPanel, "cell 1 2,grow");
		}
	}

	private MapPanel getMapPanel(String mapName) {
		MapPanel mapPanel = new MapPanel();
		mapPanel.setMapName(mapName);
		return mapPanel;
	}

	public void setMapPanel(MapPanel panel) {
		this.mapPanel = panel;
		this.repaint();
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
