package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

import objects.Destination;
import objects.Player;
import utils.SelectionHolder.Selectable;

public class DrawableDestination extends Destination implements IDrawable, Selectable {

	public final static int DOT_RADIUS = 10;
	private final static Color DOT_COLOR = Color.GRAY;
	private final static Color OUTLINE_COLOR = Color.BLACK;
	private Color stationColor = null;

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
		if (this.hasStation()) {
			drawSquare(g, (int) location.getX(), (int) location.getY(), 2*DOT_RADIUS, this.hasStation(), stationColor);
		}
		drawCircle(g, (int) location.getX(), (int) location.getY(), DOT_RADIUS, isSelected, DOT_COLOR);
		if (isSelected) {
			drawCircle(g, (int) location.getX(), (int) location.getY(), DOT_RADIUS, !isSelected, DOT_COLOR);
			drawCircle(g, (int) location.getX(), (int) location.getY(), DOT_RADIUS, !isSelected, OUTLINE_COLOR);
		}
	}

	@Override
	public boolean buildStation(Player player) {
		boolean built = super.buildStation(player);
		if (built)
			this.stationColor = player.getColor().getAwtColor();
		return built;
	}

	private void drawCircle(Graphics g, int x, int y, int radius,
			boolean filled, Color color) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		if (filled) {
			g2.fillOval(x, y, 2 * radius, 2 * radius);
		} else {
			g2.drawOval(x, y, 2 * radius, 2 * radius);
		}

	}

	private void drawSquare(Graphics g, int x, int y, int side_length,
			boolean filled, Color color) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		if (filled) {
			g2.fillRect(x, y, side_length, side_length);
		} else {
			g2.drawRect(x, y, side_length, side_length);
		}
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
