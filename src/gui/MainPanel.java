package gui;

import javax.swing.JPanel;

import managers.CardManager;
import managers.GameManager;
import managers.TurnManager;
import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JLabel;

import objects.Player;
import objects.TrainColor;
import objects.interfaces.IPlayer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;

import java.awt.Font;

import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.OverlayLayout;
import javax.swing.ScrollPaneConstants;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class MainPanel extends JPanel {
	private PlayerPanel currentPlayerPanel;
	private GameManager gameManager;
	private TurnManager turnManager;
	private MapPanel mapPanel;
	private JLayeredPane layeredPane;
	private ArrayList<Player> players = new ArrayList<Player>();

	public MainPanel() {
		this.players = getPlayers();
		
		CardManager cardManager = new CardManager();
		this.turnManager = new TurnManager(this.players);
		this.gameManager = new GameManager(cardManager, this.turnManager);
		
		setLayout(new MigLayout("", "[900px:1200px:1600px,grow,fill][10%:n,right]", "[90.00:114.00:100.00,grow,fill][350:550.00,grow,fill][70:85.00:100,grow,bottom]"));

		JPanel playerPanel = new JPanel();
		add(playerPanel, "cell 0 0,alignx left,growy");
		GridLayout playerPanelLayout = new GridLayout(1, 0, 0, 0);
		playerPanelLayout.setHgap(10);
		playerPanel.setLayout(playerPanelLayout);

		
		for (int i = 0; i < this.turnManager.numPlayers(); i++) {
			playerPanel.add(new PlayerPanel(this.turnManager.getPLayer(i)));
		}

		playerPanel.add(new JPanel()); // right padding

		JPanel destCardDeckPanel = new JPanel();
		destCardDeckPanel.setBackground(new Color(255, 140, 0));
		add(destCardDeckPanel, "cell 1 0,grow");
		destCardDeckPanel.setLayout(null);

		final JLabel lblDestinationCardCount = new JLabel("46");
		lblDestinationCardCount.setFont(new Font("Tahoma", Font.PLAIN, 16));
		destCardDeckPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// FOR DEMO PURPOSES
				// SWITCH PLAYER WHENEVER DEST CARD CHOSEN
				
				remove(MainPanel.this.currentPlayerPanel);
				MainPanel.this.turnManager.nextPlayer();
				MainPanel.this.currentPlayerPanel = new PlayerPanel(MainPanel.this.turnManager.getCurrentPlayer());
				
				add(MainPanel.this.currentPlayerPanel, "cell 1 2,grow");
				
				int cardsLeft = Integer.parseInt(lblDestinationCardCount
						.getText());
				if (cardsLeft > 0) {
					lblDestinationCardCount.setText(Integer
							.toString(cardsLeft - 1));
				}
			}
		});
		lblDestinationCardCount.setBounds(10, 11, 27, 20);
		destCardDeckPanel.add(lblDestinationCardCount);

		JPanel rootMapPanel = new JPanel() {
			@Override
			public boolean isOptimizedDrawingEnabled() {
				return false;
			}
		};
		LayoutManager overlay = new OverlayLayout(rootMapPanel);
		rootMapPanel.setLayout(overlay);
		add(rootMapPanel, "cell 0 1,grow");

		this.mapPanel = createMapPanel("Europe");
		rootMapPanel.add(this.mapPanel);


		 mxGraphComponent graphPanel = createGraphPanel();
		 rootMapPanel.add(graphPanel);

		JPanel dealPanel = new JPanel();
		add(dealPanel, "cell 1 1,grow");
		dealPanel.setLayout(new MigLayout("", "[130px:n,growprio 20,grow 50,fill]", "[80%:80%,fill][278px]"));

		JPanel cardPanel = new JPanel();
		dealPanel.add(cardPanel, "cell 0 0,grow");
		cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

		DrawableTrainCarCard card1 = new DrawableTrainCarCard(this.gameManager.getDeelCard(0).getColor());
		DealtCardPanel cardPanel1 = new DealtCardPanel();
		cardPanel1.setCard(card1);
		cardPanel.add(cardPanel1);

		DrawableTrainCarCard card2 = new DrawableTrainCarCard(this.gameManager.getDeelCard(1).getColor());
		DealtCardPanel cardPanel2 = new DealtCardPanel();
		cardPanel2.setCard(card2);
		cardPanel.add(cardPanel2);

		DrawableTrainCarCard card3 = new DrawableTrainCarCard(this.gameManager.getDeelCard(2).getColor());
		DealtCardPanel cardPanel3 = new DealtCardPanel();
		cardPanel3.setCard(card3);
		cardPanel.add(cardPanel3);

		DrawableTrainCarCard card4 = new DrawableTrainCarCard(this.gameManager.getDeelCard(3).getColor());
		DealtCardPanel cardPanel4 = new DealtCardPanel();
		cardPanel4.setCard(card4);
		cardPanel.add(cardPanel4);

		DrawableTrainCarCard card5 = new DrawableTrainCarCard(this.gameManager.getDeelCard(4).getColor());
		DealtCardPanel cardPanel5 = new DealtCardPanel();
		cardPanel5.setCard(card5);
		cardPanel.add(cardPanel5);

		JPanel trainCardDeckPanel = new JPanel();
		trainCardDeckPanel.setBackground(new Color(30, 144, 255));
		dealPanel.add(trainCardDeckPanel, "cell 0 1,grow");
		trainCardDeckPanel.setLayout(null);

		final JLabel lblTrainCardCount = new JLabel(Integer.toString(this.gameManager.getSizeOfDeck()));
		lblTrainCardCount.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTrainCardCount.setBounds(10, 11, 27, 20);
		trainCardDeckPanel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
//				int cardsLeft = Integer.parseInt(lblTrainCardCount.getText());
//				if (cardsLeft > 0) {
//					lblTrainCardCount.setText(Integer.toString(cardsLeft - 1));
//				}
				System.out.println(MainPanel.this.gameManager.drawFromDeck().getColor().toString());
				lblTrainCardCount.setText(Integer.toString(MainPanel.this.gameManager.getSizeOfDeck()));
				
				// TODO: Implement
				
			}
		});
		trainCardDeckPanel.add(lblTrainCardCount);

		JPanel handPanel = new JPanel();
		handPanel.setBackground(Color.LIGHT_GRAY);
		add(handPanel, "cell 0 2,grow");
		handPanel.setLayout(new MigLayout("", "[30%:n,fill][grow]", "[grow]"));

		JTextArea destinationTextArea = new JTextArea(4, 2);
		destinationTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		destinationTextArea
				.setText("Start-End\tPoints\r\nStart2-End2\tPoints\r\n\r\n\r\n");
		destinationTextArea.setEditable(false);
		JScrollPane destCardPanel = new JScrollPane(destinationTextArea);
		destCardPanel
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		handPanel.add(destCardPanel, "cell 0 0,grow");

		JPanel panel_1 = new JPanel();
		handPanel.add(panel_1, "cell 1 0,grow");
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		for (TrainColor color : TrainColor.getAllColors()) {
			panel_1.add(new HandCardPanel(color));

		}

		
		this.currentPlayerPanel = new PlayerPanel(this.turnManager.getCurrentPlayer());
		add(this.currentPlayerPanel, "cell 1 2,grow");

	}

	private MapPanel createMapPanel(String mapName) {
		MapPanel mapPanel = new MapPanel();
		mapPanel.setMapName(mapName);
//		mapPanel.setLayout(new BoxLayout(mapPanel, BoxLayout.X_AXIS));
		return mapPanel;
	}

	private ArrayList<Player> getPlayers() {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player("Alice"));
		players.add(new Player("Bob"));
		players.add(new Player("Charlie"));
		players.add(new Player("Dan"));
		players.add(new Player("Jeff"));
		return players;
	}

//	public IPlayer getNextPlayer() {
//		ArrayList<IPlayer> rotated = new ArrayList<IPlayer>();
//		rotated.addAll(this.players.subList(1, this.players.size()));
//		rotated.add(this.players.get(0));
//
//		return this.players.get(0);
//
//	}

	public void setMapPanel(MapPanel panel) {
		this.mapPanel = panel;
		this.repaint();
	}

	public mxGraphComponent createGraphPanel() {
		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();
		graph.getModel().beginUpdate();
		try {
			Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,
					30);
			Object v2 = graph.insertVertex(parent, null, "World!", 240, 150,
					80, 30);
			graph.insertEdge(parent, null, "Edge", v1, v2);
		} finally {
			graph.getModel().endUpdate();
		}
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		return graphComponent;
	}
}
