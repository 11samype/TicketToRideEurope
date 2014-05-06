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
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import objects.Destination;
import objects.GameState;
import objects.Player;
import objects.TrainColor;
import objects.abstracts.AbstractColorableRoute;
import objects.interfaces.IDrawable;
import objects.interfaces.IPlayer;
import objects.interfaces.IRoute;
import utils.GraphHelper;
import utils.MessageHelper;
import utils.SelectionHolder;

public class MapPanel extends JPanel {

	private static int FPS = 60;
	private static final HashMap<Destination, List<IRoute>> ROUTES = GraphHelper.ROUTE_LOOKUP;
	private static final HashMap<String, DrawableDestination> DESTS = GraphHelper.DEST_LOC_LOOKUP;
	private final List<IDrawable> drawables = new ArrayList<IDrawable>();
	private final List<DrawableRoute> drawableRoutes = new ArrayList<DrawableRoute>();

	private Repainter repainterThread;
	private BufferedImage bgImg;
	private String mapName;
	private boolean isPaused = false;
	public MapPanel() {
		initDrawableDestinations();
		initDrawableRoutes();
		this.addMouseListener(new DestinationClickListener());
		// this.addMouseListener(new RouteClickListener());
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
		for (Iterator<String> iter = DESTS.keySet().iterator(); iter.hasNext();) {
			DrawableDestination drawableDest = DESTS.get(iter.next());
			drawables.add(drawableDest);
		}
	}

	private void initDrawableRoutes() {
		Iterator<Destination> destIter = ROUTES.keySet().iterator();
		while (destIter.hasNext()) {
			Destination _start = destIter.next();
			// String startName = _start.getName();
			// DrawableDestination drawStart =
			// DEST_LOC_LOOKUP.get(_start.getName());
			List<IRoute> routesFromStart = ROUTES.get(_start);
			for (IRoute routeFromStart : routesFromStart) {
				// DrawableDestination drawEnd =
				// DEST_LOC_LOOKUP.get(iroute.getEnd().getName());
				if (!drawableRoutes.contains(routeFromStart))
					drawableRoutes.add(constructDrawableRoute(routeFromStart, null));

			}

		}
	}

	private static DrawableRoute constructDrawableRoute(IRoute iroute,
			TrainColor currentPlayerColor) {
		TrainColor color = TrainColor.RAINBOW;
		DrawableDestination drawStart = DESTS.get(iroute.getStart().getName());
		DrawableDestination drawEnd = DESTS.get(iroute.getEnd().getName());
		if (currentPlayerColor != null) {
			return new DrawableRoute(drawStart, drawEnd, iroute.getLength(), currentPlayerColor);
		} else {
			if (iroute instanceof AbstractColorableRoute) {
				AbstractColorableRoute route = (AbstractColorableRoute) iroute;
				return new DrawableRoute(drawStart, drawEnd, route.getLength(), route.getColor());
			} else
				return new DrawableRoute(drawStart, drawEnd, iroute.getLength(), color);
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
				this.bgImg = ImageIO.read(new File("img//" + this.mapName + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return this.bgImg;
	}

	private synchronized void drawBackground(Graphics g) {
		g.drawImage(getBackgroundImage(), 0, 0, getWidth(), getHeight(), Color.BLACK, null);
	}

	public void tryToClaimRoute(Player current, IRoute routeToClaim,
			ArrayList<DrawableRoute> drawablesToAdd) throws UnsupportedOperationException {
		if (routeToClaim != null) {
			final ResourceBundle messages = MessageHelper.getMessages();
			final String claimError = MessageHelper.getStringFromBundle(messages, "claim.error.tilte");
			final String alreadyOwn = MessageHelper.getStringFromBundle(messages, "claim.error.unable.message") +
					MessageHelper.getStringFromBundle(messages, "claim.error.alreadyOwn.message");
			final String notEnoughCards = MessageHelper.getStringFromBundle(messages, "claim.error.unable.message") +
					MessageHelper.getStringFromBundle(messages, "claim.error.notEnoughCards.message");
			final String alreadyTaken = MessageHelper.getStringFromBundle(messages, "claim.error.unable.message") +
					MessageHelper.getStringFromBundle(messages, "claim.error.alreadyTaken.message");

			for (IPlayer player : GameState.getPlayers()) {
				boolean playerHasRoute = player.getRoutes().contains(routeToClaim);
				if (player == current && playerHasRoute) {
					JOptionPane.showMessageDialog(this,
							alreadyOwn,
							claimError, JOptionPane.ERROR_MESSAGE);
					throw new UnsupportedOperationException(alreadyOwn);
				}
				if (!playerHasRoute) {
					try {
						current.claimRoute(routeToClaim);
						MainPanel.getInstance().updatePlayerDetails();
						drawablesToAdd.add(constructDrawableRoute(routeToClaim, current.getColor()));
					} catch (UnsupportedOperationException e) {
						JOptionPane.showMessageDialog(this,
								notEnoughCards,
								claimError,	JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
					return;
				} else
					JOptionPane.showMessageDialog(this,
							alreadyTaken,
							"Claim Error", JOptionPane.ERROR_MESSAGE);
				throw new UnsupportedOperationException(alreadyTaken);
			}
		}
	}

	public void tryToClaimRoute(Player current, SelectionHolder selectedPoints,
			ArrayList<DrawableRoute> drawablesToAdd)
					throws UnsupportedOperationException {
		IRoute routeToClaim = GraphHelper.getAdjecentRouteBetween(
				(Destination) selectedPoints.get(0),
				(Destination) selectedPoints.get(1));
		tryToClaimRoute(current, routeToClaim, drawablesToAdd);
	}

	public void tryToBuildStation(Player current, Destination dest) {
		boolean placed = false;

		final ResourceBundle messages = MessageHelper.getMessages();
		final String buildError = MessageHelper.getStringFromBundle(messages, "build.error.title");
		final String noStations = MessageHelper.getStringFromBundle(messages, "build.error.unable.message") +
				MessageHelper.getStringFromBundle(messages, "build.error.noStations.message");
		final String alreadyTaken = MessageHelper.getStringFromBundle(messages, "build.error.unable.message") +
				MessageHelper.getStringFromBundle(messages, "build.error.alreadyTaken.message");

		try {
			placed = dest.buildStation(current);
			if (!placed) {
				JOptionPane.showMessageDialog(this,
						alreadyTaken,
						buildError, JOptionPane.ERROR_MESSAGE);
			}
		} catch (UnsupportedOperationException e) {
			JOptionPane.showMessageDialog(this,
					noStations,
					buildError, JOptionPane.ERROR_MESSAGE);
		}

	}

	private class DestinationClickListener extends MouseAdapter {

		SelectionHolder selectedPoints = new SelectionHolder(2);

		@Override
		public void mouseClicked(MouseEvent e) {
			DrawableDestination clickedDest = null;
			GameState.getInstance();
			Player current = (Player) GameState.getCurrentPlayer();
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
									tryToClaimRoute(current, selectedPoints, drawablesToAdd);
								} catch (UnsupportedOperationException ex) {
									ex.printStackTrace();
								} finally {
									selectedPoints.clear();
								}
							}
						}
						System.out.println(clickedDest.getName());
					} else if (SwingUtilities.isRightMouseButton(e)) {
						tryToBuildStation(current, clickedDest);
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
			GameState.getInstance();
			Player current = (Player) GameState.getCurrentPlayer();
			ArrayList<DrawableRoute> drawablesToAdd = new ArrayList<DrawableRoute>();

			for (IDrawable drawnRoute : drawableRoutes) {
				if (drawnRoute.contains(e.getPoint()) && (drawnRoute instanceof DrawableRoute)) {
					clickedRoute = (DrawableRoute) drawnRoute;
					if (SwingUtilities.isLeftMouseButton(e)) {
						tryToClaimRoute(current, clickedRoute, drawablesToAdd);
						System.out.println(clickedRoute);
					} else if (SwingUtilities.isRightMouseButton(e)) {

					}
				}
			}
		}
	}
}
