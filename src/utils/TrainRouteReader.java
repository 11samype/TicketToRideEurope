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

import org.apache.commons.lang3.tuple.Pair;

import objects.Destination;
import objects.RouteBuilder;
import objects.TrainColor;
import objects.interfaces.IRoute;

public class TrainRouteReader {

	private HashMap<Destination, List<IRoute>> graph = new HashMap<Destination, List<IRoute>>();
	private File f;
	private String fileName = "trainroutes.txt";

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

	private TrainRouteReader() throws FileNotFoundException {
		this.f = new File(fileName);
		if (!this.f.exists()) {
			throw new FileNotFoundException("Could not find trainroutes file");
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
		Pair<IRoute, IRoute> doubleRoutePair = Pair.of(null, null);
		
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
				boolean isPartOfDoubleRoute = false;

				RouteBuilder builder = null;

				// start-end (length) type [color] [ferryLocomtiveCount] [isDoubleRoute]
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

				// ferry count or color (ferry is always gray)
				if (matcher.find()) {
					if (type.equalsIgnoreCase("ferry")) {
						ferryLocomotiveCount = Integer.parseInt(matcher.group());
						builder.withLocomotiveCount(ferryLocomotiveCount);
					} else {
						color = TrainColor.fromString(matcher.group());
						builder.withColor(color);
					}
				}
				
				// 0 or 1 marker for double route
				if (matcher.find()) {
					String val = matcher.group();
					isPartOfDoubleRoute = (Integer.parseInt(val) != 0);
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
