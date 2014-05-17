package gui.panels;

import gui.drawables.DrawableTrainCarCard;
import gui.factory.JPanelFactory;
import gui.interfaces.IRefreshable;
import gui.listeners.LocaleChangeListener;
import gui.listeners.PlayerUpdater;
import gui.listeners.mouse.DealCardClickListener;
import gui.listeners.mouse.DestinationDeckListener;
import gui.listeners.mouse.TrainCarDeckListener;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;
import objects.Player;
import objects.interfaces.IPlayer;
import utils.GameState;
import utils.MessageHelper;
import utils.GameState.CardManager;

public class MainPanel extends JPanel implements IRefreshable, LocaleChangeListener {
	
	private List<PlayerUpdater> playerInfoListeners = new ArrayList<PlayerUpdater>();
	private List<LocaleChangeListener> localeChangeListeners = new ArrayList<LocaleChangeListener>();


	public MainPanel() {
		setLayout(new MigLayout(
				"",
				"[900px:1250px:1600px,grow,fill][10%:n,right]",
				"[90.00:114.00:100.00,grow,fill][773px:773px:773px,fill][70:85.00:100,grow,bottom]"));

//		this.players = getPlayers();
		GameState.withPlayers(getPlayers());
		GameState.withGUI(this);

		// top row
		addPlayersPanel();
		addDestinationDeckPanel();

		// mid row
		addMapPanel();
		addDealPanel();

		// bottom row
		addPlayerInfoPanel();
		PlayerPanel currentPlayerPanel = new PlayerPanel(GameState.getCurrentPlayer());
		add(currentPlayerPanel, "cell 1 2,grow");
		this.playerInfoListeners.add(currentPlayerPanel);

		this.refresh();
	}

	private void addPlayersPanel() {
		JPanel playersPanel = new JPanel();
		playersPanel.setLayout(new GridLayout(1, 0, 10, 0));

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

	private void addMapPanel() {
		MapPanel map = new MapPanel();
//		map.setLayout(new OverlayLayout(map));
		//		this.mapPanelRoot.add(new MapPanel());
		add(map, "cell 0 1,grow");

	}

	private void addDealPanel() {
		JPanel dealPanel = JPanelFactory.createPanel(new MigLayout("", "[130px:n,growprio 20,grow 50,fill]", "[80%:80%,fill][278px]"), null, null);

		JPanel dealtCardsPanel = JPanelFactory.createPanel(null, null, null);
		dealtCardsPanel.setLayout(new BoxLayout(dealtCardsPanel, BoxLayout.Y_AXIS));
		dealPanel.add(dealtCardsPanel, "cell 0 0,grow");

		addTrainCarDeckPanel(dealPanel);
		dealCardsToDealPanel(dealtCardsPanel);
		
		add(dealPanel, "cell 1 1,grow");
	}

	private void dealCardsToDealPanel(JPanel dealtCardsPanel) {
		CardManager cardManager = GameState.getCardManager();
		for (int i = 0; i < 5; i++) {
			DrawableTrainCarCard card = new DrawableTrainCarCard(cardManager.getDealCard(i).getColor());
			DealtCardPanel cardPanel = new DealtCardPanel(card);
			cardPanel.addMouseListener(new DealCardClickListener(i, cardManager, MainPanel.this));
			dealtCardsPanel.add(cardPanel);
		}
	}

	private void addTrainCarDeckPanel(JPanel dealPanel) {
		JPanel panel = JPanelFactory.createDeckPanel(GameState.getCardManager().getTrainCarDeck(), new Color(30, 144, 255), new TrainCarDeckListener(MainPanel.this));
		dealPanel.add(panel, "cell 0 1,grow");
	}

	private void addDestinationDeckPanel() {
		JPanel panel = JPanelFactory.createDeckPanel(GameState.getCardManager().getDestinationDeck(), new Color(255, 140, 0), new DestinationDeckListener(MainPanel.this));
		add(panel, "cell 1 0,grow");
	}

	private void addPlayerInfoPanel() {
		JPanel panel = JPanelFactory.createPanel(new MigLayout("", "[30%:n,fill][grow]","[100px:n:100px]"), Color.LIGHT_GRAY, null);
		add(panel, "cell 0 2,grow");
		addDestinationTable(panel);
		addHandPanel(panel);
	}

	private void addDestinationTable(JPanel playerInfoPanel) {
		DestinationTable destinationTable = new DestinationTable();
		this.playerInfoListeners.add(destinationTable);

		JScrollPane destScrollPane = new JScrollPane(destinationTable);
		destScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		destinationTable.setFillsViewportHeight(true);

		playerInfoPanel.add(destScrollPane, "cell 0 0,grow");
	}

	private void addHandPanel(JPanel playerInfoPanel) {
		HandPanel playerHandPanel = new HandPanel();
		this.playerInfoListeners.add(playerHandPanel);
		playerInfoPanel.add(playerHandPanel, "cell 1 0,grow");
	}

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
		for (PlayerUpdater listener : this.playerInfoListeners) {
			listener.setPlayer(GameState.getCurrentPlayer());
		}
	}

	@Override
	public void notifyLocaleChange() {
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		topFrame.setTitle(MessageHelper.getStringFromBundle(MessageHelper.getMessages(), "game.title"));
		for (LocaleChangeListener listener : this.localeChangeListeners) {
			listener.notifyLocaleChange();
		}

	}

}
