import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import gui.DrawableDestination;
import gui.MainPanel;
import gui.MapPanel;

import javax.swing.*;

import objects.Destination;
import objects.DestinationRoute;
import objects.interfaces.IRoute;
import utils.DestinationCardReader;
import utils.DestinationLocationReader;
import utils.TrainRouteReader;

public class Main {

	private static final String _cityBundleName = "Cities";

	public static Locale CURRENT_LOCALE = getGameLocale();
	public static ResourceBundle CITIES_BUNDLE = ResourceBundle.getBundle(
			_cityBundleName, CURRENT_LOCALE);

	public static Locale getGameLocale() {
		return new Locale("game", "ORIG");
	}

	public static void useGameLocale() {
		CURRENT_LOCALE = getGameLocale();
		CITIES_BUNDLE = ResourceBundle.getBundle(_cityBundleName,
				CURRENT_LOCALE);
	}

	public static String getStringFromBundle(ResourceBundle bundle, String key) {
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			e.printStackTrace();
			return '!' + key + '!';
		}
	}

	public static String getStringFromBundle(ResourceBundle bundle, String key,
			Object... params) {
		try {
			MessageFormat formatter = new MessageFormat(bundle.getString(key),
					CURRENT_LOCALE);
			return formatter.format(params);
		} catch (MissingResourceException e) {
			e.printStackTrace();
			return '!' + key + '!';
		}
	}

	public static void main(String[] args) {

		prepareGameData(true);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final JFrame window = new JFrame("TicketToRide Europe");
				window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

				MainPanel mainPanel = new MainPanel();

				window.getContentPane().add(mainPanel);
				window.pack();
				window.setVisible(true);
			}
		});
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
