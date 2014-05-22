package gui;
import gui.drawables.DrawableDestination;
import gui.panels.LanguagePanel;
import gui.panels.NumPlayerPanel;
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

	private static MainPanel mainPanel = new MainPanel();

	public static void main(String[] args) {
		GameState.initializeGameData(false);

		//		JFrame localizeFrame = new JFrame("Language");
		//		localizeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//
		//		LanguagePanel languagePanel = new LanguagePanel();
		//		localizeFrame.getContentPane().add(languagePanel);
		//		localizeFrame.pack();
		//		localizeFrame.setVisible(true);


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

				window.setJMenuBar(getMenuBar());
				window.getContentPane().add(mainPanel);
				window.pack();
				window.setVisible(true);
			}
		});

	}

	public static JMenuBar getMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu(MessageHelper.getStringFromBundle(MessageHelper.getMessages(), "menu.locale.title"));
		//		ButtonGroup group = new ButtonGroup();
		String [] lblNames = new String[] {"English", "French", "German", "Multi-National"};
		Locale[] locales = new Locale[] {Locale.US, Locale.FRANCE, Locale.GERMANY, MessageHelper.getGameLocale()};
		for (int i = 0; i < lblNames.length ; i++ ) {
			JMenuItem item = new JMenuItem(lblNames[i]);
			item.addActionListener(new LocaleMenuActionListener(locales[i], mainPanel));
			//			group.add(item);
			menu.add(item);
		}

		JMenu numPlayerMenu = new JMenu("Players");
		ButtonGroup numPlayerGroup = new ButtonGroup();
		String[] lblNumbers = new String[] {"1", "2", "3", "4", "5"};

		for (int i = 0; i < lblNumbers.length; i++) {
			JRadioButtonMenuItem button = new JRadioButtonMenuItem(lblNumbers[i]);
			button.addActionListener(new NumPlayerActionListener(Integer.parseInt(lblNumbers[i])));
			numPlayerGroup.add(button);
			numPlayerMenu.add(button);
			if (lblNumbers[i] == "2") {
				button.doClick();
			}
		}

		menuBar.add(menu);
		menuBar.add(numPlayerMenu);
		return menuBar;
	}

	
}
