package gui.panels;

import gui.listeners.PlayerUpdater;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import objects.TrainCarHand;
import objects.TrainColor;
import objects.interfaces.IPlayer;

public class HandPanel extends JPanel implements PlayerUpdater {

	private List<HandCardPanel> handCardPanels = new ArrayList<HandCardPanel>();

	public HandPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		addCardPanels();

	}

	private void addCardPanels() {
		for (TrainColor color : TrainColor.getAllColors()) {
			HandCardPanel panel = new HandCardPanel(color);
			handCardPanels.add(panel);
			add(panel);
		}

	}

	@Override
	public void setPlayer(IPlayer p) {
		TrainCarHand hand = (TrainCarHand) p.getHand();
		for (int i = 0; i < this.handCardPanels.size(); i++) {
			TrainColor color = TrainColor.getAllColors().get(i);
			int numCardsForColor = hand.numInHand(color);
			this.handCardPanels.get(i).setNumCards(numCardsForColor);
		}
		repaint();
		revalidate();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (HandCardPanel panel : handCardPanels) {
			panel.repaint();
			panel.revalidate();
		}
	}

}
