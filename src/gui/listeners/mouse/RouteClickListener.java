package gui.listeners.mouse;

import gui.drawables.DrawableDoubleRoute;
import gui.drawables.DrawableRoute;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import objects.Player;
import objects.interfaces.IDrawable;
import utils.GameState;
import utils.exceptions.LocalizedException;

public class RouteClickListener extends MouseAdapter {

	ArrayList<DrawableRoute> drawablesToAdd = new ArrayList<DrawableRoute>();
	private final List<DrawableRoute> drawableRoutes;
	private final DestinationHoverListener destHoverListener;
	
	public RouteClickListener(DestinationHoverListener destListener, List<DrawableRoute> drawables) {
		this.destHoverListener = destListener;
		this.drawableRoutes = drawables;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		DrawableRoute clickedRoute = null;
		Player current = GameState.getCurrentPlayer();
		drawablesToAdd.clear();

		if (!destHoverListener.isOverDestination) {
			Point2D p = e.getPoint();
			for (IDrawable drawnRoute : drawableRoutes) {
				if (drawnRoute instanceof DrawableDoubleRoute) {
					DrawableDoubleRoute clicked = (DrawableDoubleRoute) drawnRoute;
					try {
						if (SwingUtilities.isLeftMouseButton(e) && clicked.contains(p)) {
							if (clicked.topLineContains(p)) {
								GameState.getInstance().claimRoute(current, clicked.getTopRoute(), null);
								clicked.getTopRoute().takeBy(current);
							} else if (clicked.bottomLineContains(p)) {
								GameState.getInstance().claimRoute(current, clicked.getBottomRoute(), null);
								clicked.getBottomRoute().takeBy(current);
							}
							
						}
					} catch (LocalizedException ex) {
						JOptionPane.showMessageDialog(e.getComponent(), ex.getMessage(), ex.getTitle(),
								JOptionPane.ERROR_MESSAGE);
						break;
					}
				}
				else if (drawnRoute instanceof DrawableRoute) {
					clickedRoute = (DrawableRoute) drawnRoute;
					try {
						if (SwingUtilities.isLeftMouseButton(e) && drawnRoute.contains(e.getPoint())) {
							GameState.getInstance().claimRoute(current, clickedRoute, drawablesToAdd);
						}
					} catch (LocalizedException ex) {
						JOptionPane.showMessageDialog(e.getComponent(), ex.getMessage(), ex.getTitle(),
								JOptionPane.ERROR_MESSAGE);
						break;
					}
				}
			}
		}

		drawableRoutes.addAll(drawablesToAdd);
	}
}