package gui;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import objects.TrainColor;
import objects.abstracts.AbstractRoute;

public class DrawableRoute extends AbstractRoute implements IDrawable {

	// Width and height of rectangular region around mouse
	// pointer to use for hit detection on lines
	private static final int HIT_BOX_SIZE = 2;
	private TrainColor color;

	public DrawableRoute(DrawableDestination start, DrawableDestination end,
			int length) {
		super(start, end, length);
	}

	public DrawableRoute(DrawableDestination start, DrawableDestination end) {
		this(start, end, 1);
	}

	public DrawableRoute(DrawableDestination start, DrawableDestination end, int length, TrainColor color) {
		super(start, end, length);
		this.color = color;
	}


	@Override
	public DrawableDestination getStart() {
		return (DrawableDestination) this.start;
	}

	@Override
	public DrawableDestination getEnd() {
		return (DrawableDestination) this.end;
	}

	@Override
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Stroke saveStroke = g2.getStroke();
		if (this.color != null)
			g2.setColor(this.color.getAwtColor());
		g2.setStroke(new BasicStroke(DrawableDestination.DOT_RADIUS / 2));
		Line2D routeLine = getLine();
		g2.drawLine((int) routeLine.getX1(), (int) routeLine.getY1(),
				(int) routeLine.getX2(), (int) routeLine.getY2());
		g2.setStroke(saveStroke);
	}

	private Line2D getLine() {
		Point2D.Double startCenter = getStart().getCenter();
		Point2D.Double endCenter = getEnd().getCenter();
		return new Line2D.Double(startCenter, endCenter);
	}

	@Override
	public boolean contains(Point2D p) {
		int boxX = (int) (p.getX() - HIT_BOX_SIZE / 2);
		int boxY = (int) (p.getY() - HIT_BOX_SIZE / 2);
		return getLine().intersects(boxX, boxY, HIT_BOX_SIZE, HIT_BOX_SIZE);
	}

}
