package gui.listeners.mouse;

import gui.interfaces.IRefreshable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import objects.Player;
import objects.TrainCarDeck;
import utils.GameState;

public class TrainCarDeckListener extends MouseAdapter {

	private TrainCarDeck deck;
	private final IRefreshable component;

	public TrainCarDeckListener(IRefreshable component) {
		this.deck = GameState.getCardManager().getTrainCarDeck();
		this.component = component;
	}

	@Override
	public void mouseClicked(MouseEvent ev) {

		Player current = GameState.getCurrentPlayer();
		if (!deck.isEmpty()) {
			current.drawCardFromDeck(deck);
		}
		
		component.refresh();
	}
}