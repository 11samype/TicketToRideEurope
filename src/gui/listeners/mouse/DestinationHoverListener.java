package gui.listeners.mouse;

import gui.drawables.DrawableDestination;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import objects.interfaces.IDrawable;

public class DestinationHoverListener extends MouseMotionAdapter {
	public boolean isOverDestination;
	final List<IDrawable> drawables;
	
	public DestinationHoverListener(List<IDrawable> drawables) {
		this.drawables= drawables;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		DrawableDestination destUnderMouse = null;

		for (IDrawable drawn : drawables) {
			if (drawn instanceof DrawableDestination) {
				destUnderMouse = (DrawableDestination) drawn;
				if (destUnderMouse.contains(e.getPoint())) {
					this.isOverDestination = true;
					break;
				} else {
					this.isOverDestination = false;
				}
			}
		}

	}
}