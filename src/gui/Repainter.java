package gui;

import gui.interfaces.IRefreshable;

public class Repainter extends Thread {
	private int fps;
	private final IRefreshable comp;

	public Repainter(IRefreshable comp, int fps) {
		this.comp = comp;
		this.fps = fps;
	}

	@Override
	public void run() {
		try {
			while (true) {
					Thread.sleep(1000 / this.fps);
					this.comp.refresh();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setFPS(int fps) {
		this.fps = fps;
	}
}
