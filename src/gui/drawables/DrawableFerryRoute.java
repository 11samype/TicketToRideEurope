package gui.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import objects.FerryRoute;
import objects.interfaces.IRoute;

public class DrawableFerryRoute extends DrawableRoute {

	public DrawableFerryRoute(DrawableDestination start, DrawableDestination end, FerryRoute route) {
		super(start, end, route);
	}
	
	
	@Override
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();

		g2.setColor(Color.BLUE);
		g2.setStroke(getFerryCountStoke());
		if (super._line == null) {
			_line = getLine();
		}
		g2.draw(_line);
		g2.dispose();
		super.drawOn(g);
	}


	private BasicStroke getFerryCountStoke() {
		double ferryCount = ((FerryRoute) getRoute()).getLocomotiveCount();
		BasicStroke ferryStoke = new BasicStroke(LINE_WIDTH + 8,
				BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, new float[] {
						(float) (singleTrainLength + GAP_SIZE / 2),
						(float) (route.getLength() * (singleTrainLength + GAP_SIZE) - GAP_SIZE)
				}, 0);
		return ferryStoke;
	}

}
