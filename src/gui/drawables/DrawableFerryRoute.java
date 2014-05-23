package gui.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import objects.FerryRoute;

public class DrawableFerryRoute extends DrawableRoute {

	public DrawableFerryRoute(DrawableDestination start,
			DrawableDestination end, FerryRoute route) {
		super(start, end, route);
	}
	
	private final BasicStroke _stroke = getFerryCountStoke();

	@Override
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();

		g2.setColor(Color.BLUE);
		g2.setStroke(_stroke);
		g2.draw(_line);
		g2.dispose();
		super.drawOn(g);
	}

	private BasicStroke getFerryCountStoke() {
		int ferryCount = ((FerryRoute) getRoute()).getLocomotiveCount();
		float[] stroke = new float[(int) (1L << ferryCount)];
		stroke[0] = (float) (singleTrainLength + GAP_SIZE / 2);
		for (int i = 1; i < stroke.length - 1; i += 2) {
			stroke[i] = 0;
			stroke[i + 1] = (float) (singleTrainLength + GAP_SIZE);
		}
		stroke[stroke.length - 1] = (float) (route.getLength()
				* (singleTrainLength + GAP_SIZE) - GAP_SIZE);

		BasicStroke ferryStoke = new BasicStroke(LINE_WIDTH + 8,
				BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, stroke, 0);
		return ferryStoke;
	}

}
