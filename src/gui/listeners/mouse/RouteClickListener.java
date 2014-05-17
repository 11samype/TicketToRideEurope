package gui.listeners.mouse;

import gui.drawables.DrawableRoute;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
			for (IDrawable drawnRoute : drawableRoutes) {
				if (drawnRoute instanceof DrawableRoute) {
					clickedRoute = (DrawableRoute) drawnRoute;
					try {
						if (SwingUtilities.isLeftMouseButton(e) && drawnRoute.contains(e.getPoint())) {
							GameState.getInstance().claimRoute(current, clickedRoute, drawablesToAdd);
						}
					} catch (LocalizedException ex) {
						JOptionPane.showMessageDialog(e.getComponent(), ex.getMessage(), ex.getTitle(),
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}

		drawableRoutes.addAll(drawablesToAdd);
	}
}