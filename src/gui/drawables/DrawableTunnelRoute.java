package gui.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import objects.TrainColor;
import objects.TunnelRoute;
import objects.abstracts.AbstractColorableRoute;
import objects.interfaces.IRoute;

public class DrawableTunnelRoute extends DrawableRoute {

	public DrawableTunnelRoute(DrawableDestination start,
			DrawableDestination end, TunnelRoute route) {
		super(start, end, route, route.getColor());
	}
	
	private final BasicStroke _stroke = getTieStoke();

	protected BasicStroke getTieStoke() {
		double tieSize = singleTrainLength / 6;
		double tieGap = (singleTrainLength - 3 * tieSize) / 2;
		BasicStroke dashedTie = new BasicStroke(LINE_WIDTH + 8,
				BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {
						(float) tieSize, (float) tieGap, (float) tieSize,
						(float) tieGap, (float) tieSize, (float) GAP_SIZE,
				}, 0);
		return dashedTie;
	}

	@Override
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();

		g2.setColor(highlighted ? Color.CYAN : getAwtColor());
		g2.setStroke(_stroke);
		g2.draw(_line);
		g2.dispose();
		super.drawOn(g);
	}
}
