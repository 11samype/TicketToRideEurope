import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import gui.MainComponent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import objects.Destination;
import objects.DestinationRoute;

public class Main {
	private JFrame window;

	public Main() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				window = new JFrame("TicketToRide Europe");
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				MainComponent gamePanel = new MainComponent();
				gamePanel.setMapName("Europe");

				window.getContentPane().add(gamePanel);
				window.pack();
				window.setVisible(true);
			}
		});

	}

	public static void main(String[] args) {
		new Main();
		DestinationReader destReader = new DestinationReader();
		destReader.run();
		HashMap<Destination, List<DestinationRoute>> graph = destReader
				.getGraph();
		for (Iterator<Destination> i = graph.keySet().iterator(); i.hasNext();) {
			Destination d = i.next();
			System.out.printf("%15s : %s\n", d, graph.get(d));
		}
	}

}
