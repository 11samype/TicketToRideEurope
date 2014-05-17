package gui.panels;

import gui.interfaces.IRefreshable;
import gui.listeners.LocaleChangeListener;
import gui.listeners.PlayerUpdater;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import objects.Player;
import objects.interfaces.IPlayer;
import utils.MessageHelper;

public class PlayerPanel extends RepaintableComponent implements PlayerUpdater, LocaleChangeListener, IRefreshable{
	private JLabel lblName = createJLabel("");
	private JLabel lblStations = createJLabel("");
	private JLabel lblTrainCars = createJLabel("");
	private JLabel lblPoints = createJLabel("");
	private Player player;
	private Color bg;

//	private static final String fmtStations = "Stations: %d";
//	private static final String fmtTrainCars = "Train Cars: %d";
//	private static final String fmtPoints = "Points: %d";

	/**
	 * Create the panel.
	 *
	 * @param player
	 */
	public PlayerPanel(Player player) {
		super();
		initGUI();
		setPlayer(player);
	
		}

	private void initGUI() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		add(this.lblName);
		add(this.lblStations);
		add(this.lblTrainCars);
		add(this.lblPoints);
	}
	
	@Override
	public void setPlayer(IPlayer player) {
		this.player = (Player) player;
		this.bg = this.player.getColor().getAwtColor();

		ResourceBundle messages = MessageHelper.getMessages();
		String stations = MessageHelper.getStringFromBundle(messages, "player.numStations", player.getNumStations());
		String trains = MessageHelper.getStringFromBundle(messages, "player.numTrains", player.getNumTrains());
		String score = MessageHelper.getStringFromBundle(messages, "player.score", player.getScore());
	
		this.lblName.setText(player.getName());
		this.lblStations.setText(stations);
		this.lblTrainCars.setText(trains);
		this.lblPoints.setText(score);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(bg);
		g.fillRect(0, 0, getWidth(), getHeight());
//		if (this.player != null && this.player.getColor() != null)
			
	}

	private JLabel createJLabel(String lbl) {
		JLabel label = new JLabel(lbl);
		label.setFont(new Font("Lucida Bright", Font.BOLD, 14));
		return label;
	}
	
	@Override
	public void notifyLocaleChange() {
		this.refresh();
	}

	@Override
	public void refresh() {
		setPlayer(this.player);
		this.repaint();
		this.revalidate();
	}


}
