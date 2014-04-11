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
	private MapPanel mapPanel;

	private HandCardPanel blackPanel;
	private HandCardPanel whitePanel;
	private HandCardPanel redPanel;
	private HandCardPanel greenPanel;
	private HandCardPanel bluePanel;
	private HandCardPanel yellowPanel;
	private HandCardPanel purplePanel;
	private HandCardPanel orangePanel;

	private JPanel playerHandPanel;
	
	private JScrollPane destScrollPane;

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
	
	private HandCardPanel[] handPanels = new HandCardPanel[9];

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
		JPanel rootMapPanel = new JPanel() {
			@Override
			public boolean isOptimizedDrawingEnabled() {
				return false;
			}
		};
		LayoutManager overlay = new OverlayLayout(rootMapPanel);
		rootMapPanel.setLayout(overlay);
		add(rootMapPanel, "cell 0 1,grow");

		this.mapPanel = getMapPanel("Europe");
		rootMapPanel.add(this.mapPanel);

	}

	private void addDealPanel() {
		this.dealPanel = new JPanel();
		dealPanel.setLayout(new MigLayout("",
				"[130px:n,growprio 20,grow 50,fill]", "[80%:80%,fill][278px]"));
		add(dealPanel, "cell 1 1,grow");

		this.dealtCardsPanel = new JPanel();
		dealtCardsPanel.setLayout(new BoxLayout(dealtCardsPanel, BoxLayout.Y_AXIS));
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
		this.destinationTable = new JTable(rowData, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		this.destScrollPane = new JScrollPane(this.destinationTable);
		destScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.destinationTable.setFillsViewportHeight(true);
		this.playerInfoPanel.add(destScrollPane, "cell 0 0,grow");

	}

	private void addHandPanel() {
		this.playerHandPanel = new JPanel();
		this.playerHandPanel.setLayout(new BoxLayout(this.playerHandPanel,
				BoxLayout.X_AXIS));
		this.playerInfoPanel.add(this.playerHandPanel, "cell 1 0,grow");
		
		Player currentPlayer = (Player) this.gameState.getCurrentPlayer();
		TrainCarHand hand = currentPlayer.getHand();
		List<TrainColor> colors = TrainColor.getAllColors();
		for (int i = 0; i < colors.size() ; i++) {
			HandCardPanel handCardPanel = new HandCardPanel(colors.get(i));
			handCardPanel.setNumCards(hand.numInHand(colors.get(i)));
			this.playerHandPanel.add(new HandCardPanel(colors.get(i)));
			this.handPanels[i] = handCardPanel;
		}

	}

	private class TrainCarDeckListener extends MouseAdapter {

		private TrainCarDeck deck;

		public TrainCarDeckListener(TrainCarDeck trainCarDeck) {
			this.deck = trainCarDeck;
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// int cardsLeft =
			// Integer.parseInt(lblTrainCardCount.getText());
			// if (cardsLeft > 0) {
			// lblTrainCardCount.setText(Integer.toString(cardsLeft - 1));
			// }

			Player currentPlayer = (Player) MainPanel.this.gameState.getCurrentPlayer();
			TrainCarHand hand = currentPlayer.getHand();

			currentPlayer.drawCardFromDeck(MainPanel.this.gameState.getCardManager()
					.getTrainCarDeck());

			List<TrainColor> colors = TrainColor.getAllColors();
			for (int i = 0; i < colors.size() ; i++) {
				MainPanel.this.handPanels[i].setNumCards(hand.numInHand(colors.get(i)));
				MainPanel.this.handPanels[i].repaint();
				MainPanel.this.handPanels[i].validate();
			}
			
			//MainPanel.this.repaint();
			//MainPanel.this.validate();
			
			MainPanel.this.lblTrainCardCount.setText(Integer.toString(MainPanel.this.cardManager
					.getTrainCarDeck().size()));
		}
	}

	private class DestinationDeckListener extends MouseAdapter {

		private final DestinationDeck deck;

		public DestinationDeckListener(DestinationDeck destDeck) {
			this.deck = destDeck;
		}

		private void simulateDrawCard() {
			if (this.deck.size() > 0) {
				Player current = (Player) MainPanel.this.gameState.getCurrentPlayer();
				current.drawCardFromDeck(this.deck);
				MainPanel.this.lblDestinationCardCount.setText(Integer.toString(this.deck.size()));
			}
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// FIXME
			// FOR DEMO PURPOSES
			// SWITCH PLAYER WHENEVER DEST CARD CHOSEN

			simulateDrawCard();
			
			//Object[][] rowData = { { "Start", "End", "Value" } };
			Player current = (Player) MainPanel.this.gameState.getCurrentPlayer();
			Object[][] rowData = current.getDestinationsInJTableFormat();
			Object[] columnNames = { "Destination Start", "Destination End",
					"Points" };
			
			MainPanel.this.destinationTable = new JTable(rowData, columnNames) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}

			};

			MainPanel.this.destScrollPane = new JScrollPane(MainPanel.this.destinationTable);
			destScrollPane
					.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			MainPanel.this.destinationTable.setFillsViewportHeight(true);
			//MainPanel.this.playerInfoPanel.add(destScrollPane, "cell 0 0,grow");
			
			MainPanel.this.destinationTable.repaint();

			// remove(currentPlayerPanel);
			// turnManager.nextPlayer();
			
			if (!this.deck.isEmpty()) {
				MainPanel.this.gameState.getTurnManager().rotatePlayers();
				MainPanel.this.currentPlayerPanel.setPlayer(gameState.getCurrentPlayer());
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
