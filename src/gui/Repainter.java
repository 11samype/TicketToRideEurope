package gui;

import java.awt.Component;

public class Repainter extends Thread {
	private int fps;
	private final Component comp;

	public Repainter(Component comp, int fps) {
		this.comp = comp;
		this.fps = fps;
	}

	@Override
	public void run() {
		try {
			while (true) {
					Thread.sleep(1000 / this.fps);
					this.comp.repaint();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setFPS(int fps) {
		this.fps = fps;
	}
}
