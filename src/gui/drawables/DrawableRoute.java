package gui.drawables;

import gui.interfaces.Highlightable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.HashMap;

import objects.TrainColor;
import objects.abstracts.AbstractColorableRoute;
import objects.interfaces.IDrawable;
import objects.interfaces.IRoute;

public class DrawableRoute extends AbstractColorableRoute implements IDrawable,
		Highlightable {

	// Width and height of rectangular region around mouse
	// pointer to use for hit detection on lines
	private static final int HIT_BOX_SIZE = 8;
	private boolean highlighted;
	
	public static DrawableRoute constructFromRoute(IRoute iroute, TrainColor currentPlayerColor, HashMap<String, DrawableDestination> destMap) {
		TrainColor color = TrainColor.RAINBOW;
		DrawableDestination drawStart = destMap.get(iroute.getStart().getName());
		DrawableDestination drawEnd = destMap.get(iroute.getEnd().getName());
		if (currentPlayerColor != null) {
			return new DrawableRoute(drawStart, drawEnd, iroute.getLength(),
					currentPlayerColor);
		} else {
			if (iroute instanceof AbstractColorableRoute) {
				AbstractColorableRoute route = (AbstractColorableRoute) iroute;
				return new DrawableRoute(drawStart, drawEnd, route.getLength(),
						route.getColor());
			} else
				return new DrawableRoute(drawStart, drawEnd,
						iroute.getLength(), color);
		}
	}

	public DrawableRoute(DrawableDestination start, DrawableDestination end,
			int length) {
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

	/**
	 * Note: in radians
	 * @return
	 */
	protected double getReciprocalAngle() {
		Double slope = getSlope();
		
		if (slope == null) {
			return 90;
		}
		
		return Math.atan(-1.0 / getSlope());
	}
	
	private Line2D.Double _line;

	@Override
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();

		double totalLineDist = getStart().getCenter().distance(
				getEnd().getCenter());
		double gapSize = 10;
		totalLineDist -= gapSize * (length - 1);
		double singleTrainLineLength = totalLineDist / length;

		if (this.highlighted) {
			g2.setColor(Color.CYAN);
		} else if (this.color != null) {
			g2.setColor(this.color.getAwtColor());
		}

		BasicStroke dashed = new BasicStroke(
				DrawableDestination.DOT_RADIUS / 2, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL, 0, new float[] {
						(float) singleTrainLineLength, (float) gapSize }, 0);
		g2.setStroke(dashed);

		if (_line == null) _line = getLine();
		g2.draw(_line);

		g2.dispose();
	}

	protected Line2D.Double getLine() {
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

	@Override
	public void highlight() {
		this.highlighted = true;

	}

	@Override
	public void unhighlight() {
		this.highlighted = false;

	}

}
