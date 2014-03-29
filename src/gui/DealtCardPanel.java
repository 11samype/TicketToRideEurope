package gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class DealtCardPanel extends JPanel {

	private DrawableTrainCarCard card;

	public DealtCardPanel() {
		// this.setPreferredSize(SIZE);
	}

	public void setCard(DrawableTrainCarCard card) {
		this.card = card;
		this.repaint();

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.printf("%d %d\n", getWidth(), getHeight());
		if (this.card == null) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
		} else {
			g.drawImage(card.getImage(), 0, 0, getWidth(), getHeight(),
					Color.BLACK, null);
		}
	}
}