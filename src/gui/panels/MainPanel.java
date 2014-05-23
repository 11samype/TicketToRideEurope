package gui.panels;

import gui.GameMenuBar;
import gui.Main;
import gui.drawables.DrawableTrainCarCard;
import gui.factory.JPanelFactory;
import gui.interfaces.IRefreshable;
import gui.listeners.LocaleChangeListener;
import gui.listeners.LocaleMenuActionListener;
import gui.listeners.PlayerUpdater;
import gui.listeners.mouse.DealCardClickListener;
import gui.listeners.mouse.DestinationDeckListener;
import gui.listeners.mouse.TrainCarDeckListener;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;
import objects.Player;
import objects.interfaces.IPlayer;
import utils.GameState;
import utils.MessageHelper;
import utils.GameState.CardManager;

public class MainPanel extends JPanel implements IRefreshable, LocaleChangeListener {
	
	private List<PlayerUpdater> playerInfoListeners = new ArrayList<PlayerUpdater>();
	private List<LocaleChangeListener> localeChangeListeners = new ArrayList<LocaleChangeListener>();
	private JFrame topFrame;

	private JPanel trainCarDeckPanel;

	public MainPanel() {
		setLayout(new MigLayout(
				"",
				"[900px:1200px:1600px,grow,fill][10%:n,right]",
				"[80.00:90.00:100.00,fill][700px:750px:750px,fill][90:90:100,grow,bottom]"));

		initGUI();
	}


	public void initGUI() {
		playerInfoListeners.clear();
		localeChangeListeners.clear();
		initGameState();
		addTopRow();
		addMiddleRow();
		addBottomRow();
		this.refresh();
	}
	
	public void resetGame() {
		this.removeAll();
		this.initGUI();
	}

	protected void initGameState() {
		GameState.withPlayers(getPlayers());
		GameState.withGUI(this);
	}


	protected void addTopRow() {
		addPlayersPanel();
		addDestinationDeckPanel();
	}


	protected void addMiddleRow() {
		addMapPanel();
		addDealPanel();
	}


	protected void addBottomRow() {
		addPlayerInfoPanel();
		PlayerPanel currentPlayerPanel = new PlayerPanel(GameState.getCurrentPlayer());
		add(currentPlayerPanel, "cell 1 2,grow");
		this.playerInfoListeners.add(currentPlayerPanel);
	}


	private void addPlayersPanel() {
		JPanel playersPanel = new JPanel();
		playersPanel.setLayout(new GridLayout(1, 0, 10, 0));

		for (IPlayer player : GameState.getPlayers()) {
			PlayerPanel panel = new PlayerPanel((Player) player);
			playersPanel.add(panel);
			this.localeChangeListeners.add(panel);
		}
		for (int i = GameState.getPlayers().size(); i < GameState.MAX_PLAYERS + 1; i++) {
			JPanel padding = new JPanel();
			
			playersPanel.add(padding); // right padding
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
		this.trainCarDeckPanel = JPanelFactory.createDeckPanel(GameState.getCardManager().getTrainCarDeck(), new Color(30, 144, 255), new TrainCarDeckListener(MainPanel.this));
		dealPanel.add(this.trainCarDeckPanel, "cell 0 1,grow");
	}
	
	public void resetTrainCarDeckPanel() {
		this.trainCarDeckPanel = JPanelFactory.createDeckPanel(GameState.getCardManager().getTrainCarDeck(), new Color(30, 144, 255),new TrainCarDeckListener(MainPanel.this));
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
		this.localeChangeListeners.add(destinationTable);

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

	private ArrayList<IPlayer> getPlayers() {

		return GameState.getPlayersBasedOnNum();
		
	}

	@Override
	public void refresh() {
		for (PlayerUpdater listener : this.playerInfoListeners) {
			listener.setPlayer(GameState.getCurrentPlayer());
		}
	}

	@Override
	public void notifyLocaleChange() {
		topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		topFrame.setTitle(MessageHelper.getStringFromBundle(MessageHelper.getMessages(), "game.title"));
		topFrame.setJMenuBar(new GameMenuBar(this));
		for (LocaleChangeListener listener : this.localeChangeListeners) {
			listener.notifyLocaleChange();
		}

	}

}
