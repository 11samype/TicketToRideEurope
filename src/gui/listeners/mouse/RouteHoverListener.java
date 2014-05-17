package gui.listeners.mouse;

import gui.drawables.DrawableRoute;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import objects.interfaces.IDrawable;

public class RouteHoverListener extends MouseMotionAdapter {

	private final DestinationHoverListener destHoverListener;
	private final List<DrawableRoute> drawableRoutes;
	
	public RouteHoverListener(DestinationHoverListener destListener, List<DrawableRoute> drawables) {
		this.destHoverListener = destListener;
		this.drawableRoutes = drawables;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		DrawableRoute routeUnderMouse = null;
		for (IDrawable drawnRoute : drawableRoutes) {
			if (drawnRoute instanceof DrawableRoute) {
				routeUnderMouse = (DrawableRoute) drawnRoute;
				if (!destHoverListener.isOverDestination && drawnRoute.contains(e.getPoint())) {
					routeUnderMouse.highlight();
				} else {
					routeUnderMouse.unhighlight();
				}
			}
		}

	}
}