import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import gui.MainPanel;
import gui.MapPanel;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import objects.Destination;
import objects.DestinationRoute;
import objects.interfaces.IRoute;

public class Main {
	private JFrame window;

	public Main() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				Main.this.window = new JFrame("TicketToRide Europe");
				Main.this.window.setPreferredSize(new Dimension(1200, 800));
				Main.this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				MainPanel panel = new MainPanel();

				MapPanel gamePanel = new MapPanel();
				gamePanel.setMapName("Europe");

				panel.setMapPanel(gamePanel);

				Main.this.window.getContentPane().add(panel);
				Main.this.window.pack();
				Main.this.window.setVisible(true);
			}
		});

	}

	public static void main(String[] args) {
		new Main();

		DestinationReader destReader = new DestinationReader();
		destReader.run();
		HashMap<Destination, List<DestinationRoute>> destinationGraph = destReader
				.getGraph();

		TrainRouteReader routeReader;
		try {
			routeReader = new TrainRouteReader("en");
			routeReader.run();
			HashMap<Destination, List<IRoute>> routeGraph = routeReader
					.getGraph();
			for (Iterator<Destination> i = routeGraph.keySet().iterator(); i
					.hasNext();) {
				Destination d = i.next();
				System.out.printf("%15s : %s\n", d, routeGraph.get(d));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
