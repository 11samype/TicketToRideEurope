package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import objects.Destination;
import objects.DestinationRoute;

public class DestinationCardReader {

	private String fileName = "destinations.txt";
	private File f;

	private Set<DestinationRoute> routes = new HashSet<DestinationRoute>();

	private static DestinationCardReader sInstance;

	public static DestinationCardReader getInstance() {
		if (sInstance == null) {
			try {
				sInstance = new DestinationCardReader();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return sInstance;
	}

	private DestinationCardReader() throws FileNotFoundException {
		this.f = new File(fileName);
		if (!this.f.exists()) {
			throw new FileNotFoundException(
					"Could not find the file for the destination cards");
		}
	}

	public Set<DestinationRoute> getRoutes() {
		if (this.routes.isEmpty())
			run();
		return this.routes;
	}

	private void run() {

		BufferedReader br = null;
		Pattern pattern = Pattern.compile("(\\w+)");
		try {
			String line;
			br = new BufferedReader(new FileReader(this.f));

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
				// DestinationRoute endToStart = new DestinationRoute(end,
				// start,
				// score);

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
