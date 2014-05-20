package gui;
import gui.drawables.DrawableDestination;
import gui.listeners.LocaleMenuActionListener;
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
	
	private static MainPanel mainPanel = new MainPanel();

	public static void main(String[] args) {
		prepareGameData(false);
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
		String [] lblNames = new String[] {"Original", "English", "French", "German"};
		Locale[] locales = new Locale[] {MessageHelper.getGameLocale(), Locale.US, Locale.FRANCE, Locale.GERMANY};
		for (int i = 0; i < lblNames.length ; i++ ) {
			JMenuItem item = new JMenuItem(lblNames[i]);
			item.addActionListener(new LocaleMenuActionListener(locales[i], mainPanel));
//			group.add(item);
			menu.add(item);
		}

		menuBar.add(menu);
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
