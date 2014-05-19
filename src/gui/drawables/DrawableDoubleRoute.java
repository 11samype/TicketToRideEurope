package gui.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import objects.abstracts.AbstractColorableRoute;
import objects.interfaces.IDrawable;

public class DrawableDoubleRoute extends DrawableRoute implements IDrawable {

	private final static double LINE_GAP = DrawableDestination.DOT_RADIUS;
	

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
	
	public static DrawableDoubleRoute construct(DrawableRoute top, DrawableRoute bottom) {
		DrawableDoubleRoute route = new DrawableDoubleRoute(top.getStart(), top.getEnd(), top.getLength());
		route.topRoute = top;
		route.bottomRoute = bottom;
		return route;
	}

//	@Override
//	public int getScore() {
//		return getTopRoute().getScore() + getBottomRoute().getScore();
//	}

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
		
		g2.setStroke(dashed);
		
		if (_topLine == null)
			_topLine = getTopLine();
		if (_bottomLine == null)
			_bottomLine = getBottomLine();
		
		Color save = g2.getColor();

		if (topRoute != null) {
			g2.setColor(topRoute.getAwtColor());
		}
		g2.draw(_topLine);
		g2.setColor(save);
		

		if (bottomRoute != null) {
			g2.setColor(bottomRoute.getAwtColor());
		}
		g2.draw(_bottomLine);
		g2.setColor(save);
		
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
		
		if (topRoute.contains(p)) {
			return true;
		} else if (bottomRoute.contains(p)){
			return true;
		}
		this.unhighlight();
		return false;
	
	}
	
	@Override
	public void unhighlight() {
		super.unhighlight();
		getTopRoute().unhighlight();
		getBottomRoute().unhighlight();
	}

}
