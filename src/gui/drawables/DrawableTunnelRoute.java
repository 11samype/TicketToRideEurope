package gui.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import objects.TrainColor;

public class DrawableTunnelRoute extends DrawableRoute {
	

	public DrawableTunnelRoute(DrawableDestination start, DrawableDestination end,
			int length, TrainColor color) {
		super(start, end, length, color);

	}
	
	protected BasicStroke getTieStoke() {
		double tieSize = singleTrainLength / 6;
		double tieGap = singleTrainLength / 4;
		BasicStroke dashedTie = new BasicStroke(
				LINE_WIDTH + 8, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_ROUND, 0,
				new float[] {
						(float) tieSize,
						(float) tieGap,
						(float) tieSize,
						(float) tieGap,
						(float) tieSize,
						(float) GAP_SIZE,
						
				},
						0);
		return dashedTie;
	}
	
	@Override
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		
		g2.setColor(highlighted ? Color.CYAN : getAwtColor());
		g2.setStroke(getTieStoke());
		if (super._line == null) {
			_line = getLine();
		}
		g2.draw(_line);
		g2.dispose();
		super.drawOn(g);
	}
}
