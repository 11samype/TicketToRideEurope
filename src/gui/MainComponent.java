package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class MainComponent extends JComponent {

	private static final long serialVersionUID = -8438576029794021570L;
	private static Dimension SIZE = new Dimension(1200, 800);
	private Repainter repainterThread;
	private BufferedImage bgImg;

	private List<IDrawable> drawables = new ArrayList<>();
	private String mapName;
	private boolean isPaused = false;
	private static int FPS = 60;

	public MainComponent() {
		this.setPreferredSize(SIZE);
		repaintAtFPS(FPS);
	}

	private void repaintAtFPS(int fps) {
		if (this.repainterThread == null) {
			this.repainterThread = new Repainter(this, fps);
			this.repainterThread.start();
		} else {
			this.repainterThread.setFPS(fps);
		}
	}

	public boolean isPaused() {
		return isPaused;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBackground(g);
		drawDrawables(g);
	}

	public void setMapName(String name) {
		this.mapName = name;
	}

	private synchronized void drawDrawables(Graphics g) {
		for (IDrawable drawable : this.drawables) {
			drawable.drawOn(g);
		}
	}

	/**
	 * Gets a singleton background image
	 *
	 * @return the loaded background image
	 */
	private synchronized BufferedImage getBackgroundImage() {
		if (this.bgImg == null) {
			try {
				bgImg = ImageIO.read(new File("img//" + mapName + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bgImg;
	}

	private synchronized void drawBackground(Graphics g) {
		g.drawImage(getBackgroundImage(), 0, 0, getWidth(), getHeight(),
				Color.BLACK, null);

	}
}
