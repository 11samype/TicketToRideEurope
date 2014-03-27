package gui;

import javax.swing.JPanel;

import java.awt.FlowLayout;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.BoxLayout;

import objects.Player;

import java.awt.Component;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;

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

		lblName = createJLabel("Player#");
		add(lblName);

		lblStations = createJLabel("Stations: ");
		add(lblStations);

		lblTrainCars = createJLabel("Train Cars: ");
		add(lblTrainCars);

		lblPoints = createJLabel("Points: ");
		add(lblPoints);

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
		setBackground(bgColor);
	}

	private JLabel createJLabel(String arg0) {
		JLabel label = new JLabel(arg0);
		label.setFont(new Font("Lucida Bright", Font.BOLD, 14));
		return label;
	}

}
