import gui.drawables.DrawableDestination;
import gui.panels.LanguagePanel;
import gui.panels.MainPanel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

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
		
		JFrame localizeFrame = new JFrame("Language");
		localizeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		LanguagePanel languagePanel = new LanguagePanel();
		localizeFrame.getContentPane().add(languagePanel);
		localizeFrame.pack();
		localizeFrame.setVisible(true);

		
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
