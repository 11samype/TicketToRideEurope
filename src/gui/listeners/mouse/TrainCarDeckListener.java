package gui.listeners.mouse;

import gui.interfaces.IRefreshable;
import gui.panels.MainPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import objects.DiscardPile;
import objects.Player;
import objects.TrainCarCard;
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