import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import objects.Destination;
import objects.DestinationRoute;
import objects.TrainColor;
import objects.TrainRoute;
import objects.abstracts.AbstractRoute;
import objects.interfaces.IRoute;

public class TrainRouteReader {

	private HashMap<Destination, List<AbstractRoute>> graph = new HashMap<Destination, List<AbstractRoute>>();

	public HashMap<Destination, List<AbstractRoute>> getGraph() {
		return graph;
	}

	public void run() {
		String filePath = "TrainRoutes.txt";
		BufferedReader br = null;
		Pattern pattern = Pattern.compile("\\w+|\\d+");
		try {
			String line;
			br = new BufferedReader(new FileReader(filePath));

			while ((line = br.readLine()) != null) {
				// pre-define some variables
				Destination start = null, end = null;
				String startDestName = null, endDestName = null;
				int length = 1;
				String type = "train";
				TrainColor color = null;
				int ferryLocomotiveCount = 0;

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

				// type
				if (matcher.find()) {
					type = matcher.group();
				}

				if (matcher.find()) {
					if (!type.equalsIgnoreCase("ferry")) {
						color = TrainColor.fromString(matcher.group());
					} else {
						ferryLocomotiveCount = Integer
								.parseInt(matcher.group());
					}
				}



//				addRouteToGraph(start, startToEnd);
//				addRouteToGraph(end, endToStart);

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

	private void addRouteToGraph(Destination destination, AbstractRoute route) {
		if (graph.containsKey(destination)) {
			graph.get(destination).add(route);
		} else {
			ArrayList<AbstractRoute> routes = new ArrayList<AbstractRoute>();
			routes.add(route);
			graph.put(destination, routes);
		}

	}
}
