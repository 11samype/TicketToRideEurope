package gui.listeners;

import gui.panels.MainPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import objects.Player;
import objects.interfaces.IPlayer;
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
		List<Player> players = new ArrayList<Player>();
		for (int i = 0; i < numPlayers; i++) {
			players.add(new Player("Player " + (i + 1)));
		}
		GameState.withPlayers(players);
		
		mainPanel.resetGame();
	}

}
