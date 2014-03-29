package gui;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;
import javax.swing.SpringLayout;

import objects.Player;
import objects.TrainColor;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

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
	private MapPanel mapPanel;
	private JLayeredPane layeredPane;

	public MainPanel() {
		setLayout(new MigLayout(
				"",
				"[70.00%:900.00,grow,fill][10%:n,right]",
				"[90.00:114.00:100.00,grow,fill][350:550.00,grow,fill][70:85.00:100,grow,bottom]"));

		JPanel playerPanel = new JPanel();
		add(playerPanel, "cell 0 0,alignx left,growy");
		GridLayout playerPanelLayout = new GridLayout(1, 0, 0, 0);
		playerPanelLayout.setHgap(10);
		playerPanel.setLayout(playerPanelLayout);

		ArrayList<Player> players = getPlayers();
		for (int i = 0; i < 4; i++) {
			playerPanel.add(new PlayerPanel(players.get(i)));
		}

		playerPanel.add(new JPanel());

		// PlayerPanel playerPanel_1 = new PlayerPanel();
		// playerPanel.add(playerPanel_1);
		//
		// PlayerPanel playerPanel_2 = new PlayerPanel();
		// playerPanel.add(playerPanel_2);
		//
		// PlayerPanel playerPanel_3 = new PlayerPanel();
		// playerPanel.add(playerPanel_3);
		//
		// PlayerPanel playerPanel_4 = new PlayerPanel();
		// playerPanel.add(playerPanel_4);

		JPanel destCardDeckPanel = new JPanel();
		destCardDeckPanel.setBackground(new Color(255, 140, 0));
		add(destCardDeckPanel, "cell 1 0,grow");
		destCardDeckPanel.setLayout(null);

		final JLabel lblDestinationCardCount = new JLabel("120");
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
			public boolean isOptimizedDrawingEnabled() {
				return false;
			}
		};
		LayoutManager overlay = new OverlayLayout(rootMapPanel);
		rootMapPanel.setLayout(overlay);
		add(rootMapPanel, "cell 0 1,grow");

		mapPanel = createMapPanel("Europe");
		rootMapPanel.add(mapPanel);
		// rootMapPanel.add(mapPanel);

		// mapPanel = createMapPanel("Europe");
		// layeredPane.add(mapPanel, 0);

		// mxGraphComponent graphPanel = createGraphPanel();
		// layeredPane.add(graphPanel, 0);
		// layeredPane.add(createGraphPanel());

		JPanel dealPanel = new JPanel();
		add(dealPanel, "cell 1 1,grow");
		dealPanel.setLayout(new MigLayout("", "[100px:n,fill]",
				"[80%:80%,fill][278px]"));

		JPanel cardPanel = new JPanel();
		dealPanel.add(cardPanel, "cell 0 0,grow");
		cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

		JPanel trainCar1Panel = new JPanel();
		cardPanel.add(trainCar1Panel);

		JPanel trainCar2Panel = new JPanel();
		cardPanel.add(trainCar2Panel);

		JPanel trainCar3Panel = new JPanel();
		cardPanel.add(trainCar3Panel);

		JPanel trainCar4Panel = new JPanel();
		cardPanel.add(trainCar4Panel);

		JPanel trainCar5Panel = new JPanel();
		cardPanel.add(trainCar5Panel);

		JPanel cardPanel1 = new DeelCardPanel();
		cardPanel.add(cardPanel1);

		JPanel cardPanel2 = new DeelCardPanel();
		cardPanel.add(cardPanel2);

		JPanel cardPanel3 = new DeelCardPanel();
		cardPanel.add(cardPanel3);

		JPanel cardPanel4 = new DeelCardPanel();
		cardPanel.add(cardPanel4);

		JPanel cardPanel5 = new DeelCardPanel();
		cardPanel.add(cardPanel5);

		JPanel trainCardDeckPanel = new JPanel();
		trainCardDeckPanel.setBackground(new Color(30, 144, 255));
		dealPanel.add(trainCardDeckPanel, "cell 0 1,grow");
		trainCardDeckPanel.setLayout(null);

		final JLabel lblTrainCardCount = new JLabel("120");
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

		JPanel blackPanel = new HandCardPanel(TrainColor.BLACK);
		panel_1.add(blackPanel);

		JPanel whitePanel = new HandCardPanel(TrainColor.WHITE);
		panel_1.add(whitePanel);

		JPanel redPanel = new HandCardPanel(TrainColor.RED);
		panel_1.add(redPanel);

		JPanel greenPanel = new HandCardPanel(TrainColor.GREEN);
		panel_1.add(greenPanel);

		JPanel bluePanel = new HandCardPanel(TrainColor.BLUE);
		panel_1.add(bluePanel);

		JPanel yellowPanel = new HandCardPanel(TrainColor.YELLOW);
		panel_1.add(yellowPanel);

		JPanel purplePanel = new HandCardPanel(TrainColor.PINK);
		panel_1.add(purplePanel);

		JPanel orangePanel = new HandCardPanel(TrainColor.ORANGE);
		panel_1.add(orangePanel);

		PlayerPanel currentPlayerPanel = new PlayerPanel();
		add(currentPlayerPanel, "cell 1 2,grow");

	}

	private MapPanel createMapPanel(String mapName) {
		MapPanel mapPanel = new MapPanel();
		mapPanel.setMapName(mapName);
		mapPanel.setLayout(new BoxLayout(mapPanel, BoxLayout.X_AXIS));
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
