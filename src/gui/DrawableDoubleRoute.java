package gui;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class DrawableDoubleRoute extends DrawableRoute implements IDrawable {

	private final static double OFFSET = DrawableDestination.DOT_RADIUS / 2;

	private DrawableRoute topRoute;
	private DrawableRoute bottomRoute;

	public DrawableDoubleRoute(DrawableDestination start,
			DrawableDestination end) {
		super(start, end, 1);
		this.topRoute = new DrawableRoute(start, end);
		this.bottomRoute = new DrawableRoute(start, end);
	}

	public DrawableDoubleRoute(DrawableDestination start,
			DrawableDestination end, int score) {
		super(start, end, score);
		this.topRoute = new DrawableRoute(start, end, score);
		this.bottomRoute = new DrawableRoute(start, end, score);
	}

	@Override
	public int getScore() {
		return getTopRoute().getScore() + getBottomRoute().getScore();
	}

	public DrawableRoute getTopRoute() {
		return this.topRoute;
	}

	public DrawableRoute getBottomRoute() {
		return this.bottomRoute;
	}

	@Override
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Stroke saveStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(DrawableDestination.DOT_RADIUS / 2));
		Line2D topRouteLine = getTopLine();
		Line2D bottomRouteLine = getBottomLine();
		g2.drawLine((int) topRouteLine.getX1(), (int) topRouteLine.getY1(),
				(int) topRouteLine.getX2(), (int) topRouteLine.getY2());
		g2.drawLine((int) bottomRouteLine.getX1(),
				(int) bottomRouteLine.getY1(), (int) bottomRouteLine.getX2(),
				(int) bottomRouteLine.getY2());
		g2.setStroke(saveStroke);
	}

	private double getSlope() {
		Point2D.Double startCenter = getStart().getCenter();
		Point2D.Double endCenter = getEnd().getCenter();
		return (endCenter.getY() - startCenter.getY())
				/ (endCenter.getX() - startCenter.getX());
	}

	private Line2D getTopLine() {
		Point2D.Double startCenter = getStart().getCenter();
		Point2D.Double endCenter = getEnd().getCenter();
		double slope_recip = -1.0 / getSlope();
		// TODO: do some maths
		return new Line2D.Double(0, 0, 0, 0);
	}

	private Line2D getBottomLine() {
		Point2D.Double startCenter = getStart().getCenter();
		Point2D.Double endCenter = getEnd().getCenter();
		double slope_recip = -1.0 / getSlope();
		// TODO: do some maths
		return new Line2D.Double(0, 0, 0, 0);
	}

	@Override
	@Deprecated
	public boolean contains(Point2D p) {
		return topLineContains(p) || bottomLineContains(p);
	}

	public boolean topLineContains(Point2D p) {
		return this.topRoute.contains(p);
	}

	public boolean bottomLineContains(Point2D p) {
		return this.bottomRoute.contains(p);
	}

}
