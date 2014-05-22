package gui.drawables;

import gui.interfaces.Highlightable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import objects.TrainColor;
import objects.abstracts.AbstractColorableRoute;
import objects.interfaces.IDrawable;
import objects.interfaces.IRoute;

public class DrawableRoute extends AbstractColorableRoute implements IDrawable,
Highlightable {

	// Width and height of rectangular region around mouse
	// pointer to use for hit detection on lines
	protected static final int HIT_BOX_SIZE = 10;
	protected boolean highlighted;
	private boolean isTaken;
	protected static final double GAP_SIZE = 10;
	public static final float LINE_WIDTH = DrawableDestination.DOT_RADIUS / 2;

	public static DrawableRoute construct(IRoute iroute,
			TrainColor currentPlayerColor,
			HashMap<String, DrawableDestination> destMap) {
		TrainColor color = TrainColor.RAINBOW;
		DrawableDestination drawStart = destMap.get(iroute.getStart().getName());
		DrawableDestination drawEnd = destMap.get(iroute.getEnd().getName());
		if (currentPlayerColor != null) {
			DrawableRoute route = new DrawableRoute(drawStart, drawEnd, iroute.getLength(), currentPlayerColor);
			route.isTaken = true;
			return route;
		} else {
			if (iroute instanceof AbstractColorableRoute) {
				AbstractColorableRoute route = (AbstractColorableRoute) iroute;
				return new DrawableRoute(drawStart, drawEnd, route.getLength(), route.getColor());
			} else
				return new DrawableRoute(drawStart, drawEnd, iroute.getLength(), color);
		}
	}

	public DrawableRoute(DrawableDestination start, DrawableDestination end, int length) {
		super(start, end, length);
	}

	public DrawableRoute(DrawableDestination start, DrawableDestination end) {
		this(start, end, 1);
	}

	public DrawableRoute(DrawableDestination start, DrawableDestination end,
			int length, TrainColor color) {
		super(start, end, color, length);

	}

	@Override
	public DrawableDestination getStart() {
		return (DrawableDestination) this.start;
	}

	@Override
	public DrawableDestination getEnd() {
		return (DrawableDestination) this.end;
	}

	protected Double getSlope() {
		Point2D.Double startCenter = getStart().getCenter();
		Point2D.Double endCenter = getEnd().getCenter();
		double denom = endCenter.getX() - startCenter.getX();

		if (denom == 0)
			return null;
		return (endCenter.getY() - startCenter.getY()) / denom;
	}
	
	protected void drawLineOutline(Graphics2D g2) {
		double delta = LINE_WIDTH;
		
		Point2D start = getStart().getCenter();
		Point2D end = getEnd().getCenter();
		
		Point2D.Double mid = new Point.Double((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
		
		double length = start.distance(end);
		Rectangle2D.Double rect = new Rectangle2D.Double(-length / 2, -delta, length, delta * 2);
		
		
		double theta = getReciprocalAngle() - Math.PI / 2;
		g2.translate(mid.getX(), mid.getY());
		g2.rotate(theta);
		
		g2.draw(rect);
		
		g2.rotate(-theta);
		g2.translate(-mid.getX(), -mid.getY());
	}

	/**
	 * Note: in radians
	 *
	 * @return
	 */
	protected double getReciprocalAngle() {
		Double slope = getSlope();
		if (slope == null) // null if 0
			return Math.toRadians(90);
		return Math.atan(-1.0 / slope);
	}

	private Line2D.Double _line;
	protected final double singleTrainLength = getTrainLength();

	@Override
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();

//		if (isTaken) {
//			g2.setStroke(new BasicStroke(1.0f));
//			g2.setColor(Color.BLACK);
//			g2.draw(g2.getStroke().createStrokedShape(getLine()));
//		} else {
			g2.setStroke(getDashedStoke());
//		}
		
		if (this.highlighted) {
			g2.setColor(Color.CYAN);
		} else if (this.color != null) {
			g2.setColor(this.getAwtColor());
		}


		if (_line == null)
			_line = getLine();
		g2.draw(_line);
		
		if (isTaken) {
			g2.setStroke(getTakenStoke());
			g2.draw(_line);
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(2.0f));
			drawLineOutline(g2);
//			g2.draw(g2.getStroke().
		}
	

		g2.dispose();
	}

	private BasicStroke getTakenStoke() {
		BasicStroke stroke =  new BasicStroke(LINE_WIDTH + 4);
		
		return stroke;
	}

	protected BasicStroke getDashedStoke() {
		BasicStroke dashed = new BasicStroke(
				LINE_WIDTH, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL, 0, new float[] {
						(float) singleTrainLength, (float) GAP_SIZE }, 0);
		return dashed;
	}

	protected double getTrainLength() {
		double totalLineDist = getStart().getCenter().distance(getEnd().getCenter());
		totalLineDist -= GAP_SIZE * (length - 1);
		double singleTrainLineLength = totalLineDist / length;
		return singleTrainLineLength;
	}

	protected Line2D.Double getLine() {
		Point2D.Double startCenter = getStart().getCenter();
		Point2D.Double endCenter = getEnd().getCenter();
		return new Line2D.Double(startCenter, endCenter);
	}
	
	protected Rectangle2D.Double getHitBox(Point2D p) {
		int boxX = (int) (p.getX() - HIT_BOX_SIZE / 2);
		int boxY = (int) (p.getY() - HIT_BOX_SIZE / 2);

		return new Rectangle2D.Double(boxX, boxY, HIT_BOX_SIZE, HIT_BOX_SIZE);
	}

	@Override
	public boolean contains(Point2D p) {
	

		if (_line == null)
			_line = getLine();
		
		if (_line.intersects(getHitBox(p))) {
			this.highlight();
			return true;
		}
		
		this.unhighlight();
		return false;
	}

	@Override
	public void highlight() {
		this.highlighted = true;
	}

	@Override
	public void unhighlight() {
		this.highlighted = false;
	}

}
