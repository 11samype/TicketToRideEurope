package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = -8438576029794021570L;

	public MainPanel() {
		int width = 1200;
		int height = 800;
		this.setPreferredSize(new Dimension(width, height));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBackground(g);

	}

	private void drawBackground(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0,  0, getWidth(), getHeight());

	}

}
