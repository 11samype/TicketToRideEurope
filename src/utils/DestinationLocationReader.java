package utils;

import gui.drawables.DrawableDestination;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DestinationLocationReader {
	private HashMap<String, DrawableDestination> destMap = new HashMap<String, DrawableDestination>();
	private File f;
	private String fileName = "city-locations.txt";
	
	private static DestinationLocationReader sInstance;

	public static DestinationLocationReader getInstance() {
		if (sInstance == null) {
			try {
				sInstance = new DestinationLocationReader();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return sInstance;
	}

	private DestinationLocationReader() throws FileNotFoundException {
		this.f = new File(fileName);
		if (!this.f.exists()) {
			throw new FileNotFoundException(
					"Could not find the locations file for the cities");
		}
	}

	public HashMap<String, DrawableDestination> getDestinations() {
		if (this.destMap.isEmpty())
			run();
		return this.destMap;
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
				DrawableDestination dest = null;
				String destName = null;
				int xLoc = 0;
				int yLoc = 0;

				// Parsing Format: name x y
				Matcher matcher = pattern.matcher(line);

				// name
				if (matcher.find()) {
					destName = matcher.group();
				}

				// x-location
				if (matcher.find()) {
					xLoc = Integer.parseInt(matcher.group());
				}

				// y-location
				if (matcher.find()) {
					yLoc = Integer.parseInt(matcher.group());
				}

				dest = new DrawableDestination(destName, new Point(xLoc, yLoc));

				destMap.put(destName, dest);

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
