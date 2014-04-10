package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.BoxLayout;

import objects.Player;
import objects.interfaces.IPlayer;

import java.awt.Graphics;

public class PlayerPanel extends JPanel {
	private JLabel lblName;
	private JLabel lblStations;
	private JLabel lblTrainCars;
	private JLabel lblPoints;
	private Player player;

	private static final String fmtStations = "Stations: %d";
	private static final String fmtTrainCars = "Train Cars: %d";
	private static final String fmtPoints = "Points: %d";

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
		this.lblStations = createJLabel(String.format(fmtStations, 0));
		this.lblTrainCars = createJLabel(String.format(fmtTrainCars, 0));
		this.lblPoints = createJLabel(String.format(fmtPoints, 0));

		add(this.lblName);
		add(this.lblStations);
		add(this.lblTrainCars);
		add(this.lblPoints);
	}

	public void setPlayer(IPlayer player) {
		this.player = (Player) player;
		this.lblName.setText(player.getName());
		this.lblStations.setText(String.format(fmtStations,
				player.getNumStations()));
		this.lblTrainCars.setText(String.format(fmtTrainCars,
				player.getNumTrains()));
		this.lblPoints.setText(String.format(fmtPoints, player.getScore()));
		this.repaint();
		this.revalidate();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.player != null)
			setBackground(this.player.getColor());
	}

	private JLabel createJLabel(String lbl) {
		JLabel label = new JLabel(lbl);
		label.setFont(new Font("Lucida Bright", Font.BOLD, 14));
		return label;
	}

}
