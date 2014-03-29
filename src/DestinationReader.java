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

public class DestinationReader {

	private HashMap<Destination, List<DestinationRoute>> graph = new HashMap<Destination, List<DestinationRoute>>();

	public HashMap<Destination, List<DestinationRoute>> getGraph() {
		return this.graph;
	}

	public void run() {
		String filePath = "destinations.txt";
		BufferedReader br = null;
		Pattern pattern = Pattern.compile("(\\w+)");
		try {
			String line;
			br = new BufferedReader(new FileReader(filePath));

			while ((line = br.readLine()) != null) {
				// pre-define some variables
				String startDestName = null, endDestName = null;
				int score = 1;
				Destination start = null, end = null;

				Matcher matcher = pattern.matcher(line);

				if (matcher.find()) {
					startDestName = matcher.group();
					start = new Destination(startDestName);
				}

				if (matcher.find()) {
					endDestName = matcher.group();
					end = new Destination(endDestName);
				}

				if (matcher.find()) {
					score = Integer.parseInt(matcher.group());
				}

				DestinationRoute startToEnd = new DestinationRoute(start, end,
						score);
				DestinationRoute endToStart = new DestinationRoute(end, start,
						score);

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

	private void addRouteToGraph(Destination destination, DestinationRoute route) {
		if (this.graph.containsKey(destination)) {
			this.graph.get(destination).add(route);
		} else {
			ArrayList<DestinationRoute> routes = new ArrayList<DestinationRoute>();
			routes.add(route);
			this.graph.put(destination, routes);
		}

	}
}
