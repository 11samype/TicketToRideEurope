package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import objects.GameState;
import objects.Player;
import objects.TrainCarDeck;

public class TrainCarDeckListener extends MouseAdapter {

	private TrainCarDeck deck;
	private final IRefreshable listener;

	public TrainCarDeckListener(TrainCarDeck trainCarDeck, IRefreshable component) {
		this.deck = trainCarDeck;
		this.listener = component;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		Player current = (Player) GameState.getCurrentPlayer();
		if (!deck.isEmpty()) {
			current.drawCardFromDeck(deck);
		}
		
		listener.refresh();
	}
}