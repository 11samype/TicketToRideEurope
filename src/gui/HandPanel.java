package gui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import objects.TrainCarHand;
import objects.TrainColor;
import objects.interfaces.IPlayer;

public class HandPanel extends JPanel {

	private List<TrainColor> colors = TrainColor.getAllColors();
	private List<HandCardPanel> handPanels = new ArrayList<HandCardPanel>();

	public HandPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		addCardPanels();

	}

	private void addCardPanels() {
		for (TrainColor color : colors) {
			HandCardPanel panel = new HandCardPanel(color);
			handPanels.add(panel);
			add(panel);
		}

	}

	public void setPlayer(IPlayer p) {
		TrainCarHand hand = (TrainCarHand) p.getHand();
		for (int i = 0; i < this.handPanels.size(); i++) {
			TrainColor color = colors.get(i);
			int numCardsForColor = hand.numInHand(color);
			this.handPanels.get(i).setNumCards(numCardsForColor);
		}
		repaint();
		revalidate();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (HandCardPanel panel : handPanels) {
			panel.repaint();
			panel.revalidate();
		}
	}

}
