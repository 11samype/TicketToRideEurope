package gui;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objects.Player;
import objects.interfaces.IPlayer;
import utils.MessageHelper;

public class PlayerPanel extends JPanel implements PlayerInfoListener {
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

		ResourceBundle messages = MessageHelper.getMessages();
		String stations = MessageHelper.getStringFromBundle(messages, "player.numStations", player.getNumStations());
		String trains = MessageHelper.getStringFromBundle(messages, "player.numTrains", player.getNumTrains());
		String score = MessageHelper.getStringFromBundle(messages, "player.score", player.getScore());

		this.lblStations = createJLabel(stations);
		this.lblTrainCars = createJLabel(trains);
		this.lblPoints = createJLabel(score);


//		this.lblStations = createJLabel(String.format(fmtStations, this.player.getNumStations()));
//		this.lblTrainCars = createJLabel(String.format(fmtTrainCars, this.player.getNumTrains()));
//		this.lblPoints = createJLabel(String.format(fmtPoints, this.player.getScore()));

		add(this.lblName);
		add(this.lblStations);
		add(this.lblTrainCars);
		add(this.lblPoints);
	}

	@Override
	public void setPlayer(IPlayer player) {
		this.player = (Player) player;
		this.lblName.setText(player.getName());

		ResourceBundle messages = MessageHelper.getMessages();
		String stations = MessageHelper.getStringFromBundle(messages, "player.numStations", player.getNumStations());
		String trains = MessageHelper.getStringFromBundle(messages, "player.numTrains", player.getNumTrains());
		String score = MessageHelper.getStringFromBundle(messages, "player.score", player.getScore());

		this.lblStations.setText(stations);
		this.lblTrainCars.setText(trains);
		this.lblPoints.setText(score);

		//		this.lblStations.setText(String.format(fmtStations, player.getNumStations()));
//		this.lblTrainCars.setText(String.format(fmtTrainCars, player.getNumTrains()));
//		this.lblPoints.setText(String.format(fmtPoints, player.getScore()));
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
