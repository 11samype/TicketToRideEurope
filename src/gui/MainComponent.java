package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class MainComponent extends JComponent {

	private static final long serialVersionUID = -8438576029794021570L;
	private String mapName;
	private Thread repainterThread;

	private boolean paused = false;
	private static int FPS = 60;
	private static Dimension SIZE = new Dimension(1200, 800);

	public MainComponent() {
		this.setPreferredSize(SIZE);
		startRepainter(FPS);
	}

	private void startRepainter(int fps) {
		Runnable r = new Repainter(this, fps);
		this.repainterThread = new Thread(r);
		this.repainterThread.start();
	}

	public boolean isPaused() {
		return paused;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBackground(g);
	}

	public void setMapName(String name) {
		this.mapName = name;
	}

	private synchronized void drawBackground(Graphics g) {
		BufferedImage bgImg = null;
		try {
			bgImg = ImageIO.read(new File("img//" + mapName + ".png"));
			System.out.println("draw");
			g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), Color.BLACK, null);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
