import gui.MainPanel;

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
				window.getContentPane().add(new MainPanel());
				window.pack();
				window.setVisible(true);
			}
		});

	}

	public static void main(String[] args) {
		new Main();

	}

}
