package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import objects.Destination;
import objects.RouteBuilder;
import objects.TrainColor;
import objects.interfaces.IRoute;

public class TrainRouteReader {

	private HashMap<Destination, List<IRoute>> graph = new HashMap<Destination, List<IRoute>>();
	private File f;
	private String fileFmt = "TrainRoutes-%s.txt";

	private static TrainRouteReader sInstance;

	public static TrainRouteReader getInstance() {
		if (sInstance == null) {
			try {
				sInstance = new TrainRouteReader();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return sInstance;
	}

	public static TrainRouteReader getLanguageInstance(String lang) {
		try {
			sInstance = new TrainRouteReader(lang);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return sInstance;
	}

	private TrainRouteReader() throws FileNotFoundException {
		this("orig");
	}

	private TrainRouteReader(String lang) throws FileNotFoundException {
		this.f = new File(String.format(fileFmt, lang));
		if (!this.f.exists()) {
			throw new FileNotFoundException("Could not find the " + lang
					+ "language file for the TrainRoutes.");
		}
	}

	public HashMap<Destination, List<IRoute>> getGraph() {
		if (this.graph.isEmpty())
			run();
		return this.graph;
	}

	private void run() {
		// String filePath = String.format(fileFmt, lang);
		BufferedReader br = null;
		Pattern pattern = Pattern.compile("(\\w+)");
		try {
			String line;
			br = new BufferedReader(new FileReader(this.f));

			while ((line = br.readLine()) != null) {
				// pre-define some variables
				Destination start = null, end = null;
				String startDestName = null, endDestName = null;
				int length = 1;
				String type = "train";
				TrainColor color = null;
				int ferryLocomotiveCount = 0;

				RouteBuilder builder = null;

				// start-end (length) type [color] [ferryLocomtiveCount]
				// Note: ferry does not have a color, and only it has a count
				Matcher matcher = pattern.matcher(line);

				// start
				if (matcher.find()) {
					startDestName = matcher.group();
					start = new Destination(startDestName);
				}

				// end
				if (matcher.find()) {
					endDestName = matcher.group();
					end = new Destination(endDestName);
				}

				// length
				if (matcher.find()) {
					length = Integer.parseInt(matcher.group());
				}

				builder = new RouteBuilder(start, end, length);

				// type
				if (matcher.find()) {
					type = matcher.group();
				}

				if (matcher.find()) {
					if (!type.equalsIgnoreCase("ferry")) {
						color = TrainColor.fromString(matcher.group());
						builder.withColor(color);
					} else {
						ferryLocomotiveCount = Integer
								.parseInt(matcher.group());
						builder.withLocomotiveCount(ferryLocomotiveCount);
					}
				}

				IRoute startToEnd = builder.build(type);
				IRoute endToStart = builder.reverseDirection().build(type);

				addRouteToGraph(start, startToEnd);
				addRouteToGraph(end, endToStart);

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void addRouteToGraph(Destination destination, IRoute route) {
		if (this.graph.containsKey(destination)) {
			this.graph.get(destination).add(route);
		} else {
			ArrayList<IRoute> routes = new ArrayList<IRoute>();
			routes.add(route);
			this.graph.put(destination, routes);
		}

	}
}
