import gui.MainComponent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
	private JFrame window;

	public Main() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				window = new JFrame("TicketToRide Window");
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				MainComponent panel = new MainComponent();
				panel.setMapName("Europe");

				window.getContentPane().add(panel);
				window.pack();
				window.setVisible(true);
			}
		});

	}

	public static void main(String[] args) {
		new Main();
		new DestinationReader().run();
	}

}
