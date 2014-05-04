package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class DealtCardPanel extends JPanel {

	private DrawableTrainCarCard card;

	public DealtCardPanel(DrawableTrainCarCard card) {
		this.setCard(card);
	}

	public void setCard(DrawableTrainCarCard card) {
		this.card = card;
		this.repaint();
		this.revalidate();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.card == null) {
			g.setColor(Color.black);
			g.drawRect(0, 0, getWidth() - 2, getHeight() - 2);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, getWidth() - 2, getHeight() - 2);
		} else {
			BufferedImage cardImage = card.getImage();
			g.drawImage(cardImage, 0, 0, getWidth(), getHeight(), Color.BLACK,
					null);
		}
	}
}
