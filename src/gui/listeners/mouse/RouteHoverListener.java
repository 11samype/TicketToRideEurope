package gui.listeners.mouse;

import gui.drawables.DrawableDoubleRoute;
import gui.drawables.DrawableRoute;

import java.awt.Point;
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
//		DrawableRoute routeUnderMouse = null;
		Point p = e.getPoint();
		for (DrawableRoute drawnRoute : drawableRoutes) {
			
			if (drawnRoute instanceof DrawableDoubleRoute) {
				DrawableDoubleRoute route = (DrawableDoubleRoute) drawnRoute;
				boolean high = !destHoverListener.isOverDestination && route.contains(p);
//				if (high) {
//			
//				} else {
//					route.unhighlight();
//				}
			}
			
			
			
			else {
				boolean high = !destHoverListener.isOverDestination && drawnRoute.contains(p);
//					routeUnderMouse.highlight();
//				} else {
//					routeUnderMouse.unhighlight();
//				}
			}
		}

	}
}