package gui.panels;

import gui.Repainter;
import gui.interfaces.IRefreshable;

import javax.swing.JComponent;

public abstract class RepaintableComponent extends JComponent implements IRefreshable{
	
	private static int FPS = 60;
	protected Repainter repainterThread;
//	private boolean threaded;
	
	public RepaintableComponent(boolean continuousRedraw) {
		if (continuousRedraw) {
//			threaded = continuousRedraw;
			repaintAtFPS(FPS);
		}
	}
	
	public RepaintableComponent() {
		this(true);
	}
	
	protected void repaintAtFPS(int fps) {
		if (this.repainterThread == null) {
			this.repainterThread = new Repainter(this, fps);
			this.repainterThread.start();
		} else {
			this.repainterThread.setFPS(fps);
		}
	}
	
	@Override
	public void refresh() {
		repaint();
		revalidate();
	}
	

}
