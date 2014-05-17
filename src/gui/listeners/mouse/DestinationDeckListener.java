package gui.listeners.mouse;

import gui.interfaces.IRefreshable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import objects.DestinationDeck;
import utils.GameState;
import utils.exceptions.DestinationAfterTrainException;

public class DestinationDeckListener extends MouseAdapter {

	private final DestinationDeck deck;
	private final IRefreshable listener;

	public DestinationDeckListener(IRefreshable component) {
		this.deck = GameState.getCardManager().getDestinationDeck();
		this.listener = component;
	}
	//
	//		private void drawCard() {
	//			getCurrentPlayer().drawCardFromDeck(this.deck);
	//			lblDestinationCardCount.setText(Integer.toString(this.deck.size()));
	//			GameState.takeTurn();
	//		}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		if (!this.deck.isEmpty()) {
			try {
				GameState.getCurrentPlayer().drawCardFromDeck(this.deck);

			} catch (DestinationAfterTrainException e) {
				JOptionPane window = new JOptionPane();
				JDialog dialog = window.createDialog(e.getTitle());
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
				//					JOptionPane.showMessageDialog(MainPanel.this, e.getMessage());
			} finally {
				listener.refresh();
			}

		}
	}
}