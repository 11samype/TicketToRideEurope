package gui.drawables;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import objects.interfaces.IDrawable;
import objects.interfaces.IRoute;

public class DrawableDoubleRoute extends DrawableRoute implements IDrawable {

	private final static double LINE_GAP = DrawableDestination.DOT_RADIUS;
	
	private DrawableRoute topRoute;
	private DrawableRoute bottomRoute;
	
	public DrawableDoubleRoute(DrawableRoute top, DrawableRoute bottom) {
		super(top.getStart(), top.getEnd(), top.getRoute());
		this.topRoute = top;
		this.bottomRoute = bottom;
	}
	
	/**
	 * Using this always returns the "top route"
	 */
	@Override
	@Deprecated
	public IRoute getRoute() {
		return getTopRoute().getRoute();
	}

	public DrawableRoute getTopRoute() {
		return this.topRoute;
	}

	public DrawableRoute getBottomRoute() {
		return this.bottomRoute;
	}
	
	private final Line2D.Double _topLine = getTopLine();
	private final Line2D.Double _bottomLine = getBottomLine();

	@Override
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		
		double theta = getReciprocalAngle();
		
		double xAway = 1 * LINE_GAP * Math.cos(theta);
		double yAway = 1 * LINE_GAP * Math.sin(theta);
		
		g2.translate(xAway,  yAway);
		topRoute.drawOn(g2);
		g2.translate(-2*xAway, -2*yAway);
		bottomRoute.drawOn(g2);
		
		g2.dispose();
	}

	private Line2D.Double getTopLine() {
		return getLineOnSide(LineSide.TOP);
	}

	private Line2D.Double getBottomLine() {
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
	public boolean contains(Point2D p) {
		if (topLineContains(p)) {
			topRoute.highlight();
			return true;
		}
		else if (bottomLineContains(p)) {
			bottomRoute.highlight();
			return true;
		}
		this.unhighlight();
		return false;
	}
	
	public boolean topLineContains(Point2D p) {
		return _topLine.intersects(getHitBox(p));
	}
	
	public boolean bottomLineContains(Point2D p) {
		return _bottomLine.intersects(getHitBox(p));
	}
	
	@Override
	public void unhighlight() {
		super.unhighlight();
		this.topRoute.unhighlight();
		this.bottomRoute.unhighlight();
	}
}
