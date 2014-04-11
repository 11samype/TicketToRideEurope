package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import utils.DestinationLocationReader;

public class MapPanel extends JPanel {

	private static int FPS = 60;

	private Repainter repainterThread;
	private BufferedImage bgImg;

	private List<IDrawable> drawables = new ArrayList<IDrawable>();
	private String mapName;
	private boolean isPaused = false;

	public MapPanel() {
		repaintAtFPS(FPS);
		getDrawableDestinations();
		this.addMouseListener(new DestinationClickListener());
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
		return this.isPaused;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// drawBackground(g);
		drawDrawables(g);
	}

	public void setMapName(String name) {
		this.mapName = name;
	}
	
	public String getMapName() {
		return this.mapName;
	}

	private void getDrawableDestinations() {
		DestinationLocationReader destReader = DestinationLocationReader
				.getInstance();

		HashSet<DrawableDestination> destinations = destReader
				.getDestinations();
		for (Iterator<DrawableDestination> iter = destinations.iterator(); iter
				.hasNext();) {
			DrawableDestination drawableDest = iter.next();
			this.drawables.add(drawableDest);
		}
	}

	private synchronized void drawDrawables(Graphics g) {
		for (IDrawable drawable : this.drawables) {
			drawable.drawOn(g);
		}
	}

	public boolean addDrawable(IDrawable drawable) {
		return this.drawables.add(drawable);
	}

	/**
	 * Gets a singleton background image
	 * 
	 * @return the loaded background image
	 */
	private synchronized BufferedImage getBackgroundImage() {
		if (this.bgImg == null) {
			try {
				this.bgImg = ImageIO.read(new File("img//" + this.mapName
						+ ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return this.bgImg;
	}

	private synchronized void drawBackground(Graphics g) {
		g.drawImage(getBackgroundImage(), 0, 0, getWidth(), getHeight(),
				Color.BLACK, null);
	}

	private class SelectionHolder extends ArrayList<DrawableDestination> {
		private final static int MAX_SIZE = 2;

		@Override
		public void clear() {
			for (DrawableDestination selectedDest : this) {
				selectedDest.deselect();
			}
			super.clear();
		}

		@Override
		public boolean add(DrawableDestination d) {
			d.select();
			if (this.size() >= MAX_SIZE) {
				this.clear();
			}
			return super.add(d);
		}

		public boolean isFull() {
			return this.size() == MAX_SIZE;
		}

	}

	private class DestinationClickListener extends MouseAdapter {

		SelectionHolder selectedPoints = new SelectionHolder();
		ArrayList<IDrawable> drawablesToAdd = new ArrayList<IDrawable>();

		@Override
		public void mouseClicked(MouseEvent e) {
			DrawableDestination clickedDest = null;
			for (IDrawable drawable : drawables) {
				if (drawable.contains(e.getPoint())
						&& (drawable instanceof DrawableDestination)) {
					clickedDest = (DrawableDestination) drawable;
					if (SwingUtilities.isLeftMouseButton(e)) {
						if (selectedPoints.contains(clickedDest)) {
							clickedDest.deselect();
							selectedPoints.remove(clickedDest);
						} else {
							selectedPoints.add(clickedDest);
							if (selectedPoints.isFull()) {
								drawablesToAdd.add(new DrawableRoute(
										selectedPoints.get(0), selectedPoints
												.get(1)));
								selectedPoints.clear();
							}
							System.out.println(clickedDest.getName());
						}

					} else if (SwingUtilities.isRightMouseButton(e)) {
						System.out.println(clickedDest.getName());
					}
				} // end if contains

			} // end for-loop

			drawables.addAll(drawablesToAdd);
			drawablesToAdd.clear();

		}
	}
}
