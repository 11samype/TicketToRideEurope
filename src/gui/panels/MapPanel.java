package gui.panels;

import gui.drawables.DrawableDestination;
import gui.drawables.DrawableRoute;
import gui.interfaces.IRefreshable;
import gui.listeners.mouse.DestinationClickListener;
import gui.listeners.mouse.DestinationHoverListener;
import gui.listeners.mouse.RouteClickListener;
import gui.listeners.mouse.RouteHoverListener;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import objects.Destination;
import objects.interfaces.IDrawable;
import objects.interfaces.IRoute;
import utils.GraphHelper;

public class MapPanel extends RepaintableComponent implements IRefreshable {

	
	private static final HashMap<Destination, List<IRoute>> ROUTES = GraphHelper.ROUTE_LOOKUP;
	private static final HashMap<String, DrawableDestination> DESTS = GraphHelper.DEST_LOC_LOOKUP;
	private final List<IDrawable> drawables = new ArrayList<IDrawable>();
	private final List<DrawableRoute> drawableRoutes = new ArrayList<DrawableRoute>();

	
	// private BufferedImage bgImg;
	// private final String mapName = "Europe";
	private final DestinationClickListener destClickListener = new DestinationClickListener(drawables, drawableRoutes);
	private final DestinationHoverListener destHoverListener = new DestinationHoverListener(drawables);
	private final RouteClickListener routeClickListener = new RouteClickListener(destHoverListener, drawableRoutes);
	private final RouteHoverListener routeHoverListener = new RouteHoverListener(destHoverListener, drawableRoutes);

	public MapPanel() {
		initDrawableDestinations();
		initDrawableRoutes();
		this.addMouseListener(destClickListener);
		this.addMouseListener(routeClickListener);
		this.addMouseMotionListener(destHoverListener);
		this.addMouseMotionListener(routeHoverListener);
		
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
				if (!drawableRoutes.contains(routeFromStart)) {
					DrawableRoute route = DrawableRoute.constructFromRoute(routeFromStart, null, DESTS);
					drawableRoutes.add(route);
				}
				// else {
				// System.out.println("Double");
				// System.out.println(routeFromStart);
				// }

			}

		}
	}


	/**
	 * Gets a singleton background image
	 *
	 * @return the loaded background image
	 */
	// private synchronized BufferedImage getBackgroundImage() {
	// if (this.bgImg == null) {
	// try {
	// this.bgImg = ImageIO.read(new File("img//" + this.mapName
	// + ".png"));
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// return this.bgImg;
	// }

	// Never used
	// private synchronized void drawBackground(Graphics g) {
	// g.drawImage(getBackgroundImage(), 0, 0, getWidth(), getHeight(),
	// Color.BLACK, null); }


}
