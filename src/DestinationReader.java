import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import objects.Destination;
import objects.AbstractRoute;
import objects.DestinationRoute;

public class DestinationReader {

	HashMap<Destination, List<AbstractRoute>> graph = new HashMap<Destination, List<AbstractRoute>>();

	public void run() {
		String filePath = "destinations.txt";
		BufferedReader br = null;
		Pattern pattern = Pattern.compile("\\w+|\\d+");
		try {
			String line;
			br = new BufferedReader(new FileReader(filePath));

			while ((line = br.readLine()) != null) {
				// pre-define some variables
				String startDestName = null, endDestName = null;
				int weight = 1;
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
					weight = Integer.parseInt(matcher.group());
				}

				AbstractRoute startToEnd = new DestinationRoute(start, end,
						weight);
				AbstractRoute endToStart = new DestinationRoute(end, start,
						weight);

				// Add start->end
				if (graph.containsKey(start)) {
					graph.get(start).add(startToEnd);
				} else {
					ArrayList<AbstractRoute> routes = new ArrayList<AbstractRoute>();
					routes.add(startToEnd);
					graph.put(start, routes);
				}

				// Add end->start
				if (graph.containsKey(end)) {
					graph.get(end).add(endToStart);
				} else {
					ArrayList<AbstractRoute> routes = new ArrayList<AbstractRoute>();
					routes.add(endToStart);
					graph.put(end, routes);
				}

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
}
