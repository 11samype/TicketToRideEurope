package gui;
import gui.drawables.DrawableDestination;
import gui.listeners.LocaleMenuActionListener;
import gui.listeners.NumPlayerActionListener;
import gui.panels.MainPanel;

import java.awt.Button;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

import objects.Destination;
import objects.DestinationRoute;
import objects.interfaces.IRoute;
import utils.DestinationCardReader;
import utils.DestinationLocationReader;
import utils.GameState;
import utils.MessageHelper;
import utils.TrainRouteReader;

public class Main {

	private static final MainPanel mainPanel = new MainPanel();

	public static void main(String[] args) {
		GameState.initializeGameData(false);

		MessageHelper.setLocale(Locale.US);
		final String gameTitle = MessageHelper.getStringFromBundle(MessageHelper.getMessages(), "game.title");
		runGame(gameTitle);
	}

	public static void runGame(final String title) {
		final JFrame window = new JFrame(title);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

				window.setJMenuBar(new GameMenuBar(mainPanel));

				window.getContentPane().add(mainPanel);
				window.pack();
				window.setVisible(true);
			}
		});

	}

	

	
}
