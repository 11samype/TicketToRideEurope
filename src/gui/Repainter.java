package gui;

public class Repainter extends Thread {
	private int fps;
	private final MapPanel comp;

	public Repainter(MapPanel comp, int fps) {
		this.comp = comp;
		this.fps = fps;
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (!comp.isPaused())
				 {
					Thread.sleep(1000 / this.fps);
					this.comp.repaint();
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
