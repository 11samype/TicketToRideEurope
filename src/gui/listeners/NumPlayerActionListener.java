package gui.listeners;

import gui.panels.MainPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import utils.GameState;

public class NumPlayerActionListener implements ActionListener {

	protected int numPlayers;
	private MainPanel mainPanel;
	
	public NumPlayerActionListener(int num, MainPanel mainPanel) {
		this.numPlayers = num;
		this.mainPanel = mainPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		GameState.numPlayers = this.numPlayers;
		
		GameState.withPlayers(GameState.getPlayersBasedOnNum());
		
		mainPanel.resetPlayersPanel();

	}

}
