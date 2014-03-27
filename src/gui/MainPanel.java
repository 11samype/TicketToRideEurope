package gui;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;
import javax.swing.SpringLayout;

import objects.Player;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;

import java.awt.Font;

public class MainPanel extends JPanel {
	private MapPanel mapPanel;

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

		mapPanel = createMapPanel("Europe");

		add(mapPanel, "cell 0 1,grow");

		JPanel dealPanel = new JPanel();
		add(dealPanel, "cell 1 1,grow");
		dealPanel.setLayout(new MigLayout("", "[100px:n,fill]",
				"[80%:80%,fill][278px]"));

		JPanel cardPanel = new JPanel();
		dealPanel.add(cardPanel, "cell 0 0,grow");

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

		JPanel panel = new JPanel();
		handPanel.add(panel, "cell 0 0,grow");

		JPanel panel_1 = new JPanel();
		handPanel.add(panel_1, "cell 1 0,grow");

		PlayerPanel currentPlayerPanel = new PlayerPanel();
		add(currentPlayerPanel, "cell 1 2,grow");

	}

	private MapPanel createMapPanel(String mapName) {
		MapPanel mapPanel = new MapPanel();
		mapPanel.setMapName(mapName);
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
}
