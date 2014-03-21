package gui;

public class Repainter extends Thread {
	private int fps;
	private final MainComponent comp;

	public Repainter(MainComponent comp, int fps) {
		this.comp = comp;
		this.fps = fps;
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (!comp.isPaused()) {
					Thread.sleep(1000 / this.fps);
					comp.repaint();
				}
			}
		} catch (InterruptedException e) {
			// nothing to catch
			e.printStackTrace();
		}
	}

	public void setFPS(int fps) {
		this.fps = fps;
	}
}
