package gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import utils.GameState;

public class NumPlayerActionListener implements ActionListener {

	protected int numPlayers;
	
	public NumPlayerActionListener(int num) {
		this.numPlayers = num;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		GameState.numPlayers = this.numPlayers;

	}

}
