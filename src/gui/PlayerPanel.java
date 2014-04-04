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
	private final Player player;

	/**
	 * Create the panel.
	 *
	 * @param player
	 */
	public PlayerPanel(Player player) {
		this.player = player;
		initGUI();
	}

	private void initGUI() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);

		this.lblName = createJLabel(this.player.getName());
		add(this.lblName);

		this.lblStations = createJLabel("Stations: ");
		add(this.lblStations);

		this.lblTrainCars = createJLabel("Train Cars: ");
		add(this.lblTrainCars);

		this.lblPoints = createJLabel("Points: ");
		add(this.lblPoints);
	}

	public void setPlayer(Player player) {
		this.lblName.setText(player.getName());
		// TODO: set station number
		this.lblTrainCars.setText(Integer.toString(player.getTrains().size()));
		this.lblPoints.setText(Integer.toString(player.getScore()));
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.player != null)
			setBackground(this.player.getColor());
	}

	private JLabel createJLabel(String arg0) {
		JLabel label = new JLabel(arg0);
		label.setFont(new Font("Lucida Bright", Font.BOLD, 14));
		return label;
	}

}
