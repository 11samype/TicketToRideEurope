package gui.drawables;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import objects.interfaces.IDrawable;

public class DrawableDoubleRoute extends DrawableRoute implements IDrawable {

	private final static double LINE_GAP = DrawableDestination.DOT_RADIUS / 2;

	private DrawableRoute topRoute;
	private DrawableRoute bottomRoute;

	public DrawableDoubleRoute(DrawableDestination start,
			DrawableDestination end) {
		this(start, end, 1);
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
	
	private Line2D.Double _topLine;
	private Line2D.Double _bottomLine;

	@Override
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		BasicStroke dashed = new BasicStroke(DrawableDestination.DOT_RADIUS / 2);
		g2.setStroke(dashed);
		
		if (_topLine == null) _topLine = getTopLine();
		if (_bottomLine == null) _bottomLine = getBottomLine();

//		g2.drawLine((int) _topLine.getX1(), (int) _topLine.getY1(),
//				(int) _topLine.getX2(), (int) _topLine.getY2());
		g2.draw(_topLine);
		
//		g2.drawLine((int) bottomRouteLine.getX1(),
//				(int) bottomRouteLine.getY1(), (int) bottomRouteLine.getX2(),
//				(int) bottomRouteLine.getY2());
		g2.draw(_bottomLine);
		g2.dispose();
	}

	private Line2D.Double getTopLine() {
//		Point2D.Double startCenter = getStart().getCenter();
//		Point2D.Double endCenter = getEnd().getCenter();
//		double theta = getReciprocalAngle();
//
//		double xAway = LINE_GAP * Math.cos(theta);
//		double yAway = LINE_GAP * Math.sin(theta);
//
//		startCenter.setLocation(startCenter.x + xAway, startCenter.y + yAway);
//		endCenter.setLocation(endCenter.x + xAway, endCenter.y + yAway);
//		return new Line2D.Double(startCenter, endCenter);
		return getLineOnSide(LineSide.TOP);
	}

	private Line2D.Double getBottomLine() {
//		Point2D.Double startCenter = getStart().getCenter();
//		Point2D.Double endCenter = getEnd().getCenter();
//		double theta = getReciprocalAngle();
//
//		double xAway = LINE_GAP * Math.cos(theta);
//		double yAway = LINE_GAP * Math.sin(theta);
//
//		startCenter.setLocation(startCenter.x - xAway, startCenter.y - yAway);
//		endCenter.setLocation(endCenter.x - xAway, endCenter.y - yAway);
//		return new Line2D.Double(startCenter, endCenter);
		return getLineOnSide(LineSide.BOTTOM);
	}
	
	private enum LineSide {
		TOP(1), BOTTOM(-1), EXACT(0);
		
		int value;
		LineSide(int side) {
			this.value = side;
		}
		
	}
	
	private Line2D.Double getLineOnSide(LineSide side) {
		Point2D.Double startCenter = getStart().getCenter();
		Point2D.Double endCenter = getEnd().getCenter();
		double theta = getReciprocalAngle();
		
		double xAway = side.value * LINE_GAP * Math.cos(theta);
		double yAway = side.value * LINE_GAP * Math.sin(theta);
		
		startCenter.setLocation(startCenter.x + xAway, startCenter.y + yAway);
		endCenter.setLocation(endCenter.x + xAway, endCenter.y + yAway);
		return new Line2D.Double(startCenter, endCenter);
	}

	@Override
	@Deprecated
	public boolean contains(Point2D p) {
		return topLineContains(p) || bottomLineContains(p) || super.getLine().contains(p);
	}

	public boolean topLineContains(Point2D p) {
		return this.topRoute.contains(p);
	}

	public boolean bottomLineContains(Point2D p) {
		return this.bottomRoute.contains(p);
	}

}
