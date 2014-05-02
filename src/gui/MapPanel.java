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
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import objects.Destination;
import objects.GameState;
import objects.Player;
import objects.TrainColor;
import objects.abstracts.AbstractColorableRoute;
import objects.interfaces.IPlayer;
import objects.interfaces.IRoute;
import utils.DestinationLocationReader;
import utils.TrainRouteReader;
import utils.SelectionHolder;

public class MapPanel extends JPanel {

	private static int FPS = 60;
	private static final HashMap<Destination, List<IRoute>> ROUTE_LOOKUP = TrainRouteReader
			.getInstance().getGraph();
	private static final HashMap<String, DrawableDestination> DEST_LOC_LOOKUP = DestinationLocationReader
			.getInstance().getDestinations();

	private final List<IDrawable> drawables = new ArrayList<IDrawable>();
	private final List<DrawableRoute> drawableRoutes = new ArrayList<DrawableRoute>();

	private Repainter repainterThread;
	private BufferedImage bgImg;
	private String mapName;
	private boolean isPaused = false;
	private HandPanel playerHandPanel;

	public MapPanel(HandPanel playerHandPanel) {
		this.playerHandPanel = playerHandPanel;
		initDrawableRoutes();
		initDrawableDestinations();
		this.addMouseListener(new DestinationClickListener());
		this.addMouseListener(new RouteClickListener());
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
		for (DrawableRoute route : drawableRoutes) {
			route.drawOn(g);
		}
		for (IDrawable drawable : drawables) {
			drawable.drawOn(g);
		}
	}

	private void initDrawableDestinations() {
		for (Iterator<String> iter = DEST_LOC_LOOKUP.keySet().iterator(); iter
				.hasNext();) {
			DrawableDestination drawableDest = DEST_LOC_LOOKUP.get(iter.next());
			drawables.add(drawableDest);
		}
	}

	private void initDrawableRoutes() {
		Iterator<Destination> destIter = ROUTE_LOOKUP.keySet().iterator();
		while (destIter.hasNext()) {
			Destination _start = destIter.next();
			// String startName = _start.getName();
			// DrawableDestination drawStart = DEST_LOC_LOOKUP.get(_start
			// .getName());
			List<IRoute> routesFromStart = ROUTE_LOOKUP.get(_start);
			for (int i = 0; i < routesFromStart.size(); i++) {
				// DrawableDestination drawEnd = DEST_LOC_LOOKUP.get(iroute
				// .getEnd().getName());
				drawableRoutes.add(constructDrawableRoute(
						routesFromStart.get(i), null));
			}
		}
	}

	private DrawableRoute constructDrawableRoute(IRoute iroute,
			TrainColor currentPlayerColor) {
		TrainColor color = TrainColor.RAINBOW;
		DrawableDestination drawStart = DEST_LOC_LOOKUP.get(iroute.getStart()
				.getName());
		DrawableDestination drawEnd = DEST_LOC_LOOKUP.get(iroute.getEnd()
				.getName());
		if (currentPlayerColor != null) {
			return new DrawableRoute(drawStart, drawEnd, iroute.getLength(),
					currentPlayerColor);
		} else {
			if (iroute instanceof AbstractColorableRoute) {
				AbstractColorableRoute route = (AbstractColorableRoute) iroute;
				return new DrawableRoute(drawStart, drawEnd, route.getLength(),
						route.getColor());
			} else
				return new DrawableRoute(drawStart, drawEnd,
						iroute.getLength(), color);
		}
	}

	public static boolean areConnected(Destination start, Destination end) {
		return getRouteBetween(start, end) != null;
	}

	public static IRoute getRouteBetween(Destination start, Destination end) {
		List<IRoute> routes = ROUTE_LOOKUP.get(start);
		if (routes != null) {
			for (int i = 0; i < routes.size(); i++) {
				if (routes.get(i).getEnd().equals(end)) {
					return routes.get(i);
				}
			}
		}
		return null;
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

	public void tryToClaimRoute(Player current, SelectionHolder selectedPoints,
			ArrayList<DrawableRoute> drawablesToAdd)
			throws UnsupportedOperationException {
		IRoute routeToClaim = getRouteBetween(selectedPoints.get(0),
				selectedPoints.get(1));
		if (routeToClaim != null) {
			DrawableRoute drawableRouteToClaim = constructDrawableRoute(
					routeToClaim, current.getColor());
			for (IPlayer player : GameState.getInstance().getPlayers()) {
				if (player == current
						&& player.getRoutes().contains(routeToClaim)) {
					JOptionPane.showMessageDialog(this,
							"Unable to claim route.\nYou already own that!",
							"Claim Error", JOptionPane.ERROR_MESSAGE);
					throw new UnsupportedOperationException(
							"Unable to claim route.\nYou already own that!");
				}
				if (!player.getRoutes().contains(routeToClaim)) {
					try {
						current.claimRoute(routeToClaim);
						playerHandPanel.setPlayer(current);
						drawablesToAdd.add(drawableRouteToClaim);
					} catch (UnsupportedOperationException e) {
						JOptionPane
								.showMessageDialog(
										this,
										"Unable to claim route.\nNot enough cards for this route!",
										"Claim Error",
										JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
					return;
				} else
					JOptionPane
							.showMessageDialog(
									this,
									"Unable to claim route.\nThat route has already been taken!",
									"Claim Error", JOptionPane.ERROR_MESSAGE);
				throw new UnsupportedOperationException(
						"Unable to claim route.\nThat route has already been taken!");
			}
		}
	}

	private class DestinationClickListener extends MouseAdapter {

		SelectionHolder selectedPoints = new SelectionHolder();

		@Override
		public void mouseClicked(MouseEvent e) {
			DrawableDestination clickedDest = null;
			Player current = (Player) GameState.getInstance()
					.getCurrentPlayer();
			ArrayList<DrawableRoute> drawablesToAdd = new ArrayList<DrawableRoute>();

			for (IDrawable drawable : drawables) {
				if (drawable.contains(e.getPoint()) && (drawable instanceof DrawableDestination)) {
					clickedDest = (DrawableDestination) drawable;

					if (SwingUtilities.isLeftMouseButton(e)) {
						if (selectedPoints.contains(clickedDest)) {
							selectedPoints.remove(clickedDest);
						} else {
							selectedPoints.add(clickedDest);
							if (selectedPoints.isFull()) {
								try {
									tryToClaimRoute(current, selectedPoints,
											drawablesToAdd);
								} catch (UnsupportedOperationException ex) {
									ex.printStackTrace();
								} finally {
									selectedPoints.clear();
								}
							}
						}
						System.out.println(clickedDest.getName());
					} else if (SwingUtilities.isRightMouseButton(e)) {
						System.out.println(clickedDest.getName());
					}
				} // end if contains

			} // end for-loop

			drawableRoutes.addAll(drawablesToAdd);
			drawablesToAdd.clear();

		}
	}

	private class RouteClickListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			DrawableRoute clickedRoute = null;
			Player current = (Player) GameState.getInstance()
					.getCurrentPlayer();

			for (IDrawable drawable : drawables) {
				if (drawable.contains(e.getPoint()) && (drawable instanceof DrawableRoute)) {
					clickedRoute = (DrawableRoute) drawable;

					if (SwingUtilities.isLeftMouseButton(e)) {

					} else if (SwingUtilities.isRightMouseButton(e)) {

					}
				}
			}
		}
	}
}
