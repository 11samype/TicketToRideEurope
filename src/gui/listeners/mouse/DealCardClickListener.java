package gui.listeners.mouse;

import gui.drawables.DrawableTrainCarCard;
import gui.interfaces.IRefreshable;
import gui.panels.DealtCardPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import objects.Player;
import utils.GameState;
import utils.GameState.CardManager;

public class DealCardClickListener extends MouseAdapter implements IRefreshable {

	private int index;
	private DealtCardPanel panel;
	private final CardManager cardManager;
	private final IRefreshable listener;

	public DealCardClickListener(int cardInt, CardManager cardManager, IRefreshable component) {
		this.index = cardInt;
		this.cardManager = cardManager;
		this.listener = component;
	}

	@Override
	public void refresh() {
		if (!cardManager.getTrainCarDeck().isEmpty()) {
			// new DrawSimulator(panel, newCard).start();
			DrawableTrainCarCard newCard = new DrawableTrainCarCard(cardManager.getDealCard(index));
			this.panel.setCard(newCard);
		} else {
			this.panel.setCard(null);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		this.panel = (DealtCardPanel) arg0.getSource();
		Player current = GameState.getCurrentPlayer();
		
		// peek at deal card to check if it is null
		// TrainCarCard cardAtPos = cardManager.getDealCard(index);

		// if (cardAtPos != null) {
		try {
			current.drawCardFromDeal(this.cardManager, index);
			this.refresh();

		} catch (NullPointerException e) {
			System.err.println("NULL Card Clicked!");
			// e.printStackTrace();
		} finally {
			listener.refresh();
		}
	}

	// private class DrawSimulator extends Thread {
	// private DrawableTrainCarCard newCard;
	// private final DealtCardPanel panel;
	//
	// public DrawSimulator(DealtCardPanel panel, DrawableTrainCarCard card) {
	// this.panel = panel;
	// this.newCard = card;
	// }
	//
	// @Override
	// public void run() {
	// this.panel.setCard(null);
	// try {
	// Thread.sleep(300);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// } finally {
	// this.panel.setCard(this.newCard);
	// }
	// }
	// }
}