package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import objects.Destination;
import objects.DestinationRoute;

public class DestinationReader {

	private Set<DestinationRoute> routes = new HashSet<DestinationRoute>();

	public Set<DestinationRoute> getRoutes() {
		return this.routes;
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
//				DestinationRoute endToStart = new DestinationRoute(end, start,
//						score);

				addRoute(startToEnd);

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

	private void addRoute(DestinationRoute route) {
		if (!this.routes.contains(route)) {
			this.routes.add(route);
		}
	}
}
