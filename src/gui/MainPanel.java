package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.OverlayLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;
import objects.DestinationDeck;
import objects.GameState;
import objects.GameState.CardManager;
import objects.Player;
import objects.TrainCarDeck;
import objects.interfaces.IPlayer;
import utils.MessageHelper;
import utils.exceptions.DestinationAfterTrainException;

public class MainPanel extends JPanel implements IRefreshable, LocaleChangeListener {
//	private final GameState gameState;
	private CardManager cardManager;

	private PlayerPanel currentPlayerPanel;
	private JPanel mapPanelRoot;

	private HandPanel playerHandPanel;

	private JScrollPane destScrollPane;

	private ArrayList<IPlayer> players = new ArrayList<IPlayer>();
	private JPanel dealPanel;

	private JLabel lblTrainCardCount;

	private JLabel lblDestinationCardCount;

	private DestinationTable destinationTable;

	private JPanel playerInfoPanel;

	private JPanel dealtCardsPanel;
	private List<PlayerInfoListener> playerInfoListeners = new ArrayList<PlayerInfoListener>();
	private List<LocaleChangeListener> localeChangeListeners = new ArrayList<LocaleChangeListener>();

	private static MainPanel sInstance;

	public static MainPanel getInstance() {
		if (sInstance == null) {
			sInstance = new MainPanel();
		}
		return sInstance;
	}

	private MainPanel() {
		setLayout(new MigLayout(
				"",
				"[900px:1250px:1600px,grow,fill][10%:n,right]",
				"[90.00:114.00:100.00,grow,fill][773px:773px:773px,fill][70:85.00:100,grow,bottom]"));

		this.players = getPlayers();
		GameState.withPlayers(players).withGUI(this);
		this.cardManager = GameState.getCardManager();

		addPlayersPanel();
		addDestinationDeckPanel();

		addPlayerInfoPanel();
		addDestinationTable();

		addMapPanel();
		addDealPanel();

		Player currentPlayer = getCurrentPlayer();
		this.currentPlayerPanel = new PlayerPanel(currentPlayer);
		add(this.currentPlayerPanel, "cell 1 2,grow");
		this.playerInfoListeners.add(currentPlayerPanel);
	}

	private void addPlayersPanel() {
		JPanel playersPanel = new JPanel();
		GridLayout playerPanelLayout = new GridLayout(1, 0, 10, 0);
		playersPanel.setLayout(playerPanelLayout);

		for (IPlayer player : GameState.getPlayers()) {
			PlayerPanel panel = new PlayerPanel((Player) player);
			playersPanel.add(panel);
			localeChangeListeners.add(panel);
		}
		for (int i = GameState.getPlayers().size(); i < GameState.MAX_PLAYERS + 1; i++) {
			playersPanel.add(new JPanel()); // right padding
		}

		add(playersPanel, "cell 0 0,alignx left,growy");
	}

	private void addDestinationDeckPanel() {
		DestinationDeck destDeck = GameState.getCardManager()
				.getDestinationDeck();

		JPanel destCardDeckPanel = new JPanel();
		destCardDeckPanel.setBackground(new Color(255, 140, 0));
		add(destCardDeckPanel, "cell 1 0,grow");
		destCardDeckPanel.setLayout(new CardLayout());

		lblDestinationCardCount = createJLabel(
				Integer.toString(destDeck.size()), 10, 11, 27, 20);
		destCardDeckPanel.add(lblDestinationCardCount);
		destCardDeckPanel.addMouseListener(new DestinationDeckListener(destDeck, MainPanel.this));
	}

	private static JLabel createJLabel(String str, int x, int y, int w, int h) {
		JLabel lbl = new JLabel(str);
		lbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl.setBounds(x, y, w, h);
		return lbl;
	}

	private void addMapPanel() {
		this.mapPanelRoot = new JPanel() {
			@Override
			public boolean isOptimizedDrawingEnabled() {
				return false;
			}
		};
		LayoutManager overlay = new OverlayLayout(this.mapPanelRoot);
		this.mapPanelRoot.setLayout(overlay);
		add(this.mapPanelRoot, "cell 0 1,grow");

		this.mapPanelRoot.add(new MapPanel(MainPanel.this));

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

		lblTrainCardCount = createJLabel(Integer.toString(carDeck.size()), 10,
				11, 27, 20);
		trainCardDeckPanel.add(lblTrainCardCount);
		trainCardDeckPanel.addMouseListener(new TrainCarDeckListener(carDeck, MainPanel.this));
		dealPanel.add(trainCardDeckPanel, "cell 0 1,grow");

	}

	private void dealCardsToDealPanel() {
		for (int i = 0; i < 5; i++) {
			DrawableTrainCarCard card = new DrawableTrainCarCard(cardManager
					.getDealCard(i).getColor());
			DealtCardPanel cardPanel = new DealtCardPanel(card);
			cardPanel.addMouseListener(new DealCardListener(i, cardManager, MainPanel.this));
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
	
	private JPanel createJPanel(LayoutManager layout, Color bg, String layoutConstraints) {
		JPanel panel = new JPanel();
		panel.setBackground(bg);
		panel.setLayout(layout);
		this.add(panel, layoutConstraints);
		
		return panel;
	}

	private void addDestinationTable() {
		this.destinationTable = new DestinationTable();
		this.destinationTable.setPlayer(getCurrentPlayer());

		this.destScrollPane = new JScrollPane(this.destinationTable);
		destScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.destinationTable.setFillsViewportHeight(true);
		this.playerInfoListeners.add(destinationTable);

		this.playerInfoPanel.add(destScrollPane, "cell 0 0,grow");

	}

	private void addHandPanel() {
		this.playerHandPanel = new HandPanel();
		playerHandPanel.setPlayer(getCurrentPlayer());
		this.playerInfoPanel.add(this.playerHandPanel, "cell 1 0,grow");
		this.playerInfoListeners.add(playerHandPanel);
	}

	private class DestinationDeckListener extends MouseAdapter {

		private final DestinationDeck deck;
		private final IRefreshable listener;

		public DestinationDeckListener(DestinationDeck destDeck, IRefreshable component) {
			this.deck = destDeck;
			this.listener = component;
		}
//
//		private void drawCard() {
//			getCurrentPlayer().drawCardFromDeck(this.deck);
//			lblDestinationCardCount.setText(Integer.toString(this.deck.size()));
//			GameState.takeTurn();
//		}

		@Override
		public void mouseClicked(MouseEvent arg0) {

			if (!this.deck.isEmpty()) {
				try {
					getCurrentPlayer().drawCardFromDeck(this.deck);
					
				} catch (DestinationAfterTrainException e) {
					JOptionPane window = new JOptionPane();
					JDialog dialog = window.createDialog(e.getTitle());
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
//					JOptionPane.showMessageDialog(MainPanel.this, e.getMessage());
				} finally {
					listener.refresh();
				}

			}
		}
	}

	public void setMapPanel(MapPanel panel) {
		this.mapPanelRoot = panel;
	}

	public Player getCurrentPlayer() {
		return (Player) GameState.getCurrentPlayer();
	}

//	public void updatePlayerDetails() {
//		for (PlayerInfoListener listener : this.playerInfoListeners) {
//			listener.setPlayer(getCurrentPlayer());
//		}
		// destinationTable.setPlayer(getCurrentPlayer());
		// playerHandPanel.setPlayer(getCurrentPlayer());
		// currentPlayerPanel.setPlayer(getCurrentPlayer());
//	}

	// TODO: Get the players rather than hard-code them
	private ArrayList<IPlayer> getPlayers() {
		ArrayList<IPlayer> players = new ArrayList<IPlayer>();
		for (int i = 0; i < GameState.MAX_PLAYERS; i++) {
			players.add(new Player("Player " + (i + 1)));
		}
		return players;
	}

	@Override
	public void refresh() {
//		updatePlayerDetails();
		for (PlayerInfoListener listener : this.playerInfoListeners) {
			listener.setPlayer(getCurrentPlayer());
		}
		lblTrainCardCount.setText(Integer.toString(this.cardManager.getTrainCarDeck().size()));
		lblDestinationCardCount.setText(Integer.toString(this.cardManager.getDestinationDeck().size()));
	}

	@Override
	public void changeLocale() {
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		topFrame.setTitle(MessageHelper.getStringFromBundle(MessageHelper.getMessages(), "game.title"));
		
	}

}
