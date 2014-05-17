package gui.listeners.mouse;

import gui.drawables.DrawableDestination;
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
import utils.SelectionHolder;
import utils.exceptions.LocalizedException;

public class DestinationClickListener extends MouseAdapter {

	private final List<DrawableRoute> drawableRoutes;
	SelectionHolder selectedPoints = new SelectionHolder(2);
	ArrayList<DrawableRoute> drawablesToAdd = new ArrayList<DrawableRoute>();
	private final List<IDrawable> drawables;

	public DestinationClickListener(List<IDrawable> drawableDests, List<DrawableRoute> drawableRoutes) {
		this.drawables = drawableDests;
		this.drawableRoutes = drawableRoutes;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		DrawableDestination clickedDest = null;
		Player current = GameState.getCurrentPlayer();
		drawablesToAdd.clear();

		for (IDrawable drawable : drawables) {
			if (drawable.contains(e.getPoint()) && (drawable instanceof DrawableDestination)) {
				clickedDest = (DrawableDestination) drawable;

				try {
					if (SwingUtilities.isLeftMouseButton(e)) {
						// if (selectedPoints.contains(clickedDest)) {
						// selectedPoints.remove(clickedDest);
						// } else {
						if (!selectedPoints.add(clickedDest)) {
							selectedPoints.remove(clickedDest);
						}
						if (selectedPoints.isFull()) {
							GameState.getInstance().claimRouteBewteenPoints(
									current, selectedPoints, drawablesToAdd);
						}
						// }
						// System.out.println(clickedDest.getName());
					} else if (SwingUtilities.isRightMouseButton(e)) {
						clickedDest.buildStation(current);
						GameState.takeTurn();
					}
				} catch (LocalizedException ex) {
					JOptionPane.showMessageDialog(e.getComponent(),
							ex.getMessage(), ex.getTitle(),
							JOptionPane.ERROR_MESSAGE);
				} finally {
					if (selectedPoints.isFull())
						selectedPoints.clear();
				}

			} // end if contains

		} // end for-loop

		drawableRoutes.addAll(drawablesToAdd);

	}
}