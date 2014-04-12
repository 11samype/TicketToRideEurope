package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import objects.Destination;
import objects.GameState;
import objects.Player;
import objects.interfaces.IRoute;
import utils.DestinationLocationReader;
import utils.TrainRouteReader;

public class MapPanel extends JPanel {

	private static int FPS = 60;
	private static final HashMap<Destination, List<IRoute>> ROUTE_LOOKUP = TrainRouteReader
			.getInstance().getGraph();
	private static final HashMap<String, DrawableDestination> DEST_LOC_LOOKUP = DestinationLocationReader
			.getInstance().getDestinations();

	private final List<IDrawable> drawables = new ArrayList<IDrawable>();

	private Repainter repainterThread;
	private BufferedImage bgImg;
	private String mapName;
	private boolean isPaused = false;

	public MapPanel() {
		getDrawableDestinations();
		getDrawableRoutes();
		this.addMouseListener(new DestinationClickListener());
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
		return this.isPaused;
	}


	public void setMapName(String name) {
		this.mapName = name;
	}

	public String getMapName() {
		return this.mapName;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// drawBackground(g);
		drawDrawables(g);
	}

	private synchronized void drawDrawables(Graphics g) {
		for (IDrawable drawable : drawables) {
			drawable.drawOn(g);
		}
	}

	private void getDrawableDestinations() {
		for (Iterator<String> iter = DEST_LOC_LOOKUP.keySet().iterator(); iter
				.hasNext();) {
			DrawableDestination drawableDest = DEST_LOC_LOOKUP.get(iter.next());
			drawables.add(drawableDest);
		}
	}

	private void getDrawableRoutes() {
		Iterator<Destination> destIter = ROUTE_LOOKUP.keySet().iterator();
		while (destIter.hasNext()) {
			Destination _start = destIter.next();
			DrawableDestination drawStart = DEST_LOC_LOOKUP.get(_start.getName());
			Iterator<IRoute> routeIter = ROUTE_LOOKUP.get(_start).iterator();
			for (IRoute route : ROUTE_LOOKUP.get(_start)) {
				DrawableDestination drawEnd = DEST_LOC_LOOKUP.get(route.getEnd().getName());
				drawables.add(new DrawableRoute(drawStart, drawEnd));
			}

		}
	}

	public boolean addDrawable(IDrawable drawable) {
		return drawables.add(drawable);
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
		Player current = (Player) GameState.getInstance().getCurrentPlayer();

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
