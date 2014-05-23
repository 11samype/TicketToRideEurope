package gui.panels;

import gui.drawables.DrawableDestination;
import gui.drawables.DrawableDoubleRoute;
import gui.drawables.DrawableRoute;
import gui.interfaces.IRefreshable;
import gui.listeners.mouse.DestinationClickListener;
import gui.listeners.mouse.DestinationHoverListener;
import gui.listeners.mouse.RouteClickListener;
import gui.listeners.mouse.RouteHoverListener;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

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
//		super(false);
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
//		 drawBackground(g);
		drawDrawables(g);
	}

	private synchronized void drawDrawables(Graphics g) {
		for (DrawableRoute route : drawableRoutes) {
//			if (route instanceof DrawableDoubleRoute) {
//				((DrawableDoubleRoute) route).drawOn(g);
//			} else {
				route.drawOn(g);
//			}
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
		List<DrawableRoute> routesToAdd = new ArrayList<DrawableRoute>();
		DrawableDoubleRoute doubleRoute = null;
		DrawableRoute route = null;
		
		for (Destination startDest : ROUTES.keySet()) {
			// make a copy to remove from
			List<IRoute> routesFromStart = new ArrayList<IRoute>(ROUTES.get(startDest));
			routesToAdd.clear();
			
			 // while there are routes from here
			while (!routesFromStart.isEmpty()) {
				// construct a drawable route
				IRoute iroute = routesFromStart.remove(0);
							
				route = DrawableRoute.construct(iroute, null, DESTS);
				if (!drawableRoutes.contains(route)) { // if not currently drawn
					// if there is a double route
					int doubleIndex = -1;
					if ((doubleIndex = routesFromStart.indexOf(iroute)) != -1) {
						// draw the double
						DrawableRoute bottom = DrawableRoute.construct(routesFromStart.remove(doubleIndex), null, DESTS);
						doubleRoute = new DrawableDoubleRoute(route, bottom);
						routesToAdd.add(doubleRoute);
 					} else { // draw the single
 						routesToAdd.add(route);
 					}
				}
				
			}
			// add all the constructed routes
			drawableRoutes.addAll(routesToAdd);
			
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
