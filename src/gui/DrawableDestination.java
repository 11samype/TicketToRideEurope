package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;

import objects.Destination;

public class DrawableDestination extends Destination implements IDrawable {

	public final static int DOT_RADIUS = 10;
	private final static Color DOT_COLOR = Color.RED;
	private final static Color SELECTED_COLOR = Color.BLACK;

	private final Point location;
	private boolean isSelected;

	public DrawableDestination(String name, Point loc) {
		super(name);
		this.location = loc;
	}

	public Point getLocation() {
		return location;
	}

	public Point2D.Double getCenter() {
		return new Point2D.Double(this.location.getX() + DOT_RADIUS,
				this.location.getY() + DOT_RADIUS);
	}

	@Override
	public void drawOn(Graphics g) {
		drawCircle(g, (int) location.getX(), (int) location.getY(), DOT_RADIUS,
				isSelected, DOT_COLOR);
		if (isSelected) {
			drawCircle(g, (int) location.getX(), (int) location.getY(),
					DOT_RADIUS, !isSelected, DOT_COLOR);
			drawCircle(g, (int) location.getX(), (int) location.getY(),
					DOT_RADIUS, !isSelected, SELECTED_COLOR);

		}
	}

	private void drawCircle(Graphics g, int x, int y, int radius,
			boolean filled, Color color) {
		Color saveColor = g.getColor();
		g.setColor(color);
		if (filled) {
			g.fillOval(x, y, 2 * radius, 2 * radius);
		} else {
			g.drawOval(x, y, 2 * radius, 2 * radius);
		}
		g.setColor(saveColor);

	}

	@Override
	public boolean contains(Point2D p) {
		double dist = getCenter().distance(p);
		return DOT_RADIUS + 0.5 >= dist;
	}

	public void select() {
		this.isSelected = true;
	}

	public void deselect() {
		this.isSelected = false;
	}

}
