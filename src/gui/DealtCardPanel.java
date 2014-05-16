package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class DealtCardPanel extends JPanel implements IRefreshable {

	private DrawableTrainCarCard card;

	public DealtCardPanel(DrawableTrainCarCard card) {
		this.setCard(card);
	}

	public DrawableTrainCarCard getCard() {
		return this.card;
	}

	public void setCard(DrawableTrainCarCard card) {
		this.card = card;
		this.refresh();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		if (this.card == null) {
			g2.setColor(Color.black);
			g2.drawRect(0, 0, getWidth() - 2, getHeight() - 2);
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillRect(0, 0, getWidth() - 2, getHeight() - 2);
		} else {
			g2.drawImage(card.getImage(), 0, 0, getWidth(), getHeight(), Color.BLACK,
					null);
		}
		g2.dispose();
	}

	@Override
	public void refresh() {
		this.repaint();
		this.revalidate();
	}
}
