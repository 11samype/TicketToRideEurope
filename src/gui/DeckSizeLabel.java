package gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;

import objects.interfaces.IDeck;

public class DeckSizeLabel extends JLabel implements PropertyChangeListener {

	private final IDeck<?> deck;

	public DeckSizeLabel(IDeck<?> deck) {
		super();
		this.deck = deck;
		setText(Integer.toString(deck.size()));
		this.deck.addChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setText(evt.getNewValue().toString());
	}
}
