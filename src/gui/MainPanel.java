package gui;

import javax.swing.JPanel;

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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.OverlayLayout;
import javax.swing.ScrollPaneConstants;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class MainPanel extends JPanel {
	private MapPanel mapPanel;
	private JLayeredPane layeredPane;
	private ArrayList<IPlayer> players = new ArrayList<IPlayer>();

	public MainPanel() {
		setLayout(new MigLayout("", "[900px:1200px:1600px,grow,fill][10%:n,right]", "[90.00:114.00:100.00,grow,fill][773px:773px:773px,fill][70:85.00:100,grow,bottom]"));

		JPanel playerPanel = new JPanel();
		add(playerPanel, "cell 0 0,alignx left,growy");
		GridLayout playerPanelLayout = new GridLayout(1, 0, 0, 0);
		playerPanelLayout.setHgap(10);
		playerPanel.setLayout(playerPanelLayout);

		ArrayList<Player> players = getPlayers();
		for (int i = 0; i < 4; i++) {
			playerPanel.add(new PlayerPanel(players.get(i)));
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

		mapPanel = createMapPanel("Europe");
		rootMapPanel.add(mapPanel);

		JPanel dealPanel = new JPanel();
		add(dealPanel, "cell 1 1,grow");
		dealPanel.setLayout(new MigLayout("",
				"[130px:n,growprio 20,grow 50,fill]", "[80%:80%,fill][278px]"));

		JPanel cardPanel = new JPanel();
		dealPanel.add(cardPanel, "cell 0 0,grow");
		cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

		DrawableTrainCarCard rainbow = new DrawableTrainCarCard(
				TrainColor.RAINBOW);
		DealtCardPanel cardPanel1 = new DealtCardPanel();
		cardPanel1.setCard(rainbow);
		cardPanel.add(cardPanel1);

		DealtCardPanel cardPanel2 = new DealtCardPanel();
		cardPanel2.setCard(rainbow);
		cardPanel.add(cardPanel2);

		DealtCardPanel cardPanel3 = new DealtCardPanel();
		cardPanel3.setCard(rainbow);
		cardPanel.add(cardPanel3);

		DealtCardPanel cardPanel4 = new DealtCardPanel();
		cardPanel4.setCard(rainbow);
		cardPanel.add(cardPanel4);

		DealtCardPanel cardPanel5 = new DealtCardPanel();
		cardPanel5.setCard(rainbow);
		cardPanel.add(cardPanel5);

		JPanel trainCardDeckPanel = new JPanel();
		trainCardDeckPanel.setBackground(new Color(30, 144, 255));
		dealPanel.add(trainCardDeckPanel, "cell 0 1,grow");

		final JLabel lblTrainCardCount = new JLabel("140");
		lblTrainCardCount.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTrainCardCount.setBounds(10, 11, 27, 20);
		trainCardDeckPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int cardsLeft = Integer.parseInt(lblTrainCardCount.getText());
				if (cardsLeft > 0) {
					lblTrainCardCount.setText(Integer.toString(cardsLeft - 1));
				}
			}
		});
		trainCardDeckPanel.add(lblTrainCardCount);

		JPanel handPanel = new JPanel();
		handPanel.setBackground(Color.LIGHT_GRAY);
		add(handPanel, "cell 0 2,grow");
		handPanel.setLayout(new MigLayout("", "[30%:n,fill][grow]", "[100px:n:100px]"));

		Object rowData[][] = {
				{ "Start", "End", "Value" } };
		Object columnNames[] = { "Destination Start", "Destination End", "Points" };
		JTable destinationTable = new JTable(rowData, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JScrollPane destScrollPane = new JScrollPane(destinationTable);
		destScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		destinationTable.setFillsViewportHeight(true);
		handPanel.add(destScrollPane, "cell 0 0,grow");

		JPanel panel_1 = new JPanel();
		handPanel.add(panel_1, "cell 1 0,grow");
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		// JPanel blackPanel = new HandCardPanel(TrainColor.BLACK);
		// panel_1.add(blackPanel);
		//
		// JPanel whitePanel = new HandCardPanel(TrainColor.WHITE);
		// panel_1.add(whitePanel);
		//
		// JPanel redPanel = new HandCardPanel(TrainColor.RED);
		// panel_1.add(redPanel);
		//
		// JPanel greenPanel = new HandCardPanel(TrainColor.GREEN);
		// panel_1.add(greenPanel);
		//
		// JPanel bluePanel = new HandCardPanel(TrainColor.BLUE);
		// panel_1.add(bluePanel);
		//
		// JPanel yellowPanel = new HandCardPanel(TrainColor.YELLOW);
		// panel_1.add(yellowPanel);
		//
		// JPanel purplePanel = new HandCardPanel(TrainColor.PINK);
		// panel_1.add(purplePanel);
		//
		// JPanel orangePanel = new HandCardPanel(TrainColor.ORANGE);
		// panel_1.add(orangePanel);

		for (TrainColor color : TrainColor.getAllColors()) {
			panel_1.add(new HandCardPanel(color));

		}

		PlayerPanel currentPlayerPanel = new PlayerPanel(new Player());
		add(currentPlayerPanel, "cell 1 2,grow");

	}

	private MapPanel createMapPanel(String mapName) {
		MapPanel mapPanel = new MapPanel();
		mapPanel.setMapName(mapName);
		// mapPanel.setLayout(new BoxLayout(mapPanel, BoxLayout.X_AXIS));
		return mapPanel;
	}

	private ArrayList<Player> getPlayers() {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player("Alice"));
		players.add(new Player("Bob"));
		players.add(new Player("Charlie"));
		players.add(new Player("Dan"));
		return players;
	}

	public IPlayer getNextPlayer() {
		ArrayList<IPlayer> rotated = new ArrayList<IPlayer>();
		rotated.addAll(players.subList(1, players.size()));
		rotated.add(players.get(0));

		return players.get(0);

	}

	public void setMapPanel(MapPanel panel) {
		this.mapPanel = panel;
		this.repaint();
	}
}
