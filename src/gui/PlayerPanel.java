package gui;

import javax.swing.JPanel;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.BoxLayout;

import objects.Player;

import java.awt.Color;
import java.awt.Graphics;

public class PlayerPanel extends JPanel {
	private JLabel lblName;
	private JLabel lblStations;
	private JLabel lblTrainCars;
	private JLabel lblPoints;
	private Player player;
	private Color bgColor;

	/**
	 * Create the panel.
	 */
	public PlayerPanel() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);

		this.lblName = createJLabel("Player#");
		add(this.lblName);

		this.lblStations = createJLabel("Stations: ");
		add(this.lblStations);

		this.lblTrainCars = createJLabel("Train Cars: ");
		add(this.lblTrainCars);

		this.lblPoints = createJLabel("Points: ");
		add(this.lblPoints);

	}

	public PlayerPanel(Player player) {
		this();
		setPlayer(player);
	}

	private void setPlayer(Player player) {
		this.player = player;
		this.lblName.setText(player.getName());
		this.bgColor = player.getColor();

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(this.bgColor);
	}

	private JLabel createJLabel(String arg0) {
		JLabel label = new JLabel(arg0);
		label.setFont(new Font("Lucida Bright", Font.BOLD, 14));
		return label;
	}

}
