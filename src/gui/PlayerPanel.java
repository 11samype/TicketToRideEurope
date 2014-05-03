package gui;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objects.Player;
import objects.interfaces.IPlayer;

public class PlayerPanel extends JPanel {
	private JLabel lblName;
	private JLabel lblStations;
	private JLabel lblTrainCars;
	private JLabel lblPoints;
	private Player player;
	private Thread repainterThread;
	private int fps;

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
		repaintAtFPS(60);
	}

	private void repaintAtFPS(int fps) {
		this.fps = fps;
		if (this.repainterThread == null) {
			this.repainterThread = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							Thread.sleep(1000 / PlayerPanel.this.fps);
							setPlayer(getPlayer()); // repaint
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}
			});
			this.repainterThread.start();
		}
	}

	private void initGUI() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);

		this.lblName = createJLabel(this.player.getName());
		this.lblStations = createJLabel(String.format(fmtStations, this.player.getNumStations()));
		this.lblTrainCars = createJLabel(String.format(fmtTrainCars, this.player.getNumTrains()));
		this.lblPoints = createJLabel(String.format(fmtPoints, 0)); // TODO: Calculate Points

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
			setBackground(this.player.getColor().getAwtColor());
	}

	private JLabel createJLabel(String lbl) {
		JLabel label = new JLabel(lbl);
		label.setFont(new Font("Lucida Bright", Font.BOLD, 14));
		return label;
	}

	public IPlayer getPlayer() {
		return this.player;
	}

}
