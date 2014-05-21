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
import utils.MessageHelper;
import utils.TrainRouteReader;

public class Main {

	public static void main(String[] args) {
		prepareGameData(false);
		
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
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				final JFrame window = new JFrame(title);
				window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

				MainPanel mainPanel = new MainPanel();
				window.setJMenuBar(getMenuBar(mainPanel));
				window.getContentPane().add(mainPanel);
				window.pack();
				window.setVisible(true);
			}
		});
	}

	protected static JMenuBar getMenuBar(MainPanel mainPanel) {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Locale");
		ButtonGroup group = new ButtonGroup();
		String [] lblNames = new String[] {"Original", "English", "French", "German"};
		Locale[] locales = new Locale[] {MessageHelper.getGameLocale(), Locale.US, Locale.FRANCE, Locale.GERMANY};
		for (int i = 0; i < lblNames.length ; i++ ) {
			JRadioButtonMenuItem button = new JRadioButtonMenuItem(lblNames[i]);
			button.addActionListener(new LocaleMenuActionListener(locales[i]));
			group.add(button);
			menu.add(button);
		}
		
		JMenu numPlayerMenu = new JMenu("Players");
		ButtonGroup numPlayerGroup = new ButtonGroup();
		String[] lblNumbers = new String[] {"1", "2", "3", "4", "5"};
		
		for (int i = 0; i < lblNumbers.length; i++) {
			JRadioButtonMenuItem button = new JRadioButtonMenuItem(lblNumbers[i]);
			button.addActionListener(new NumPlayerActionListener(Integer.parseInt(lblNumbers[i]), mainPanel));
			numPlayerGroup.add(button);
			numPlayerMenu.add(button);
			if (lblNumbers[i] == "1") {
				button.setSelected(true);
			}
		}

		menuBar.add(menu);
		menuBar.add(numPlayerMenu);
		return menuBar;
	}

	private static void prepareGameData(boolean log) {
		readTrainRoutesFile(log);
		readDestinationCardsFile(log);
		readDestinationLocationFile(log);

	}

	private static void readDestinationCardsFile(boolean log) {
		DestinationCardReader reader = DestinationCardReader.getInstance();
		if (log) {
			Set<DestinationRoute> routes = reader.getRoutes();
			int k = 1;
			for (Iterator<DestinationRoute> i = routes.iterator(); i.hasNext();) {
				DestinationRoute d = i.next();
				System.out.printf("[%d] %15s -- %15s (%s)\n", k++,
						d.getStart(), d.getEnd(), d.getScore());
			}
			System.out
			.println("-----------------------------------------------------");
		}

	}

	private static void readDestinationLocationFile(boolean log) {
		DestinationLocationReader reader = DestinationLocationReader
				.getInstance();
		if (log) {
			HashMap<String, DrawableDestination> dests = reader
					.getDestinations();
			int k = 1;
			for (Iterator<String> i = dests.keySet().iterator(); i.hasNext();) {
				DrawableDestination d = dests.get(i.next());
				System.out
				.printf("[%2d] %15s (%.2f, %.2f)\n", k++, d.getName(),
						d.getCenter().getX(), d.getCenter().getY());
			}
			System.out
			.println("----------------------------------------------------");
		}
	}

	private static void readTrainRoutesFile(boolean log) {
		TrainRouteReader reader = TrainRouteReader.getInstance();
		if (log) {
			HashMap<Destination, List<IRoute>> routeGraph = reader.getGraph();
			int k = 1;
			for (Iterator<Destination> i = routeGraph.keySet().iterator(); i
					.hasNext();) {
				Destination d = i.next();
				List<IRoute> routeLst = routeGraph.get(d);
				System.out.printf("[%2d] %15s : %s\n", k++, d, routeLst);
			}
			System.out
			.println("----------------------------------------------------");
		}

	}
}
