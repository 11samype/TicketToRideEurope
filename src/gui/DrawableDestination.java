package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import objects.Destination;
import objects.Player;
import objects.interfaces.IDrawable;
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
		Graphics2D g2 = (Graphics2D) g.create();
		int x = (int) location.getX();
		int y = (int) location.getY();
		if (this.hasStation()) {
			drawStation(x, y, g2);
		}

		drawCircle(g2, x, y, DOT_RADIUS, isSelected, DOT_COLOR);
		if (isSelected) {
			drawCircle(g2, x, y, DOT_RADIUS, !isSelected, DOT_COLOR);
			drawCircle(g2, x, y, DOT_RADIUS, !isSelected, OUTLINE_COLOR);
		}
		g2.dispose();
	}

	private void drawStation(int x, int y, Graphics2D g2) {
		Stroke saveStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(2.0f));
		drawSquare(g2, x,  y, 2*DOT_RADIUS, this.hasStation(), stationColor);
		drawSquare(g2, x, y, 2*DOT_RADIUS, !this.hasStation(), OUTLINE_COLOR);

		// spin here
		g2.translate(x + DOT_RADIUS, y + DOT_RADIUS);
		g2.rotate(Math.toRadians(45));
		g2.translate(-1*(x+DOT_RADIUS), -1*(y+DOT_RADIUS));

		drawSquare(g2, x,  y, 2*DOT_RADIUS, this.hasStation(), stationColor);
		drawSquare(g2, x, y, 2*DOT_RADIUS, !this.hasStation(), OUTLINE_COLOR);
		g2.setStroke(saveStroke);
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
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setColor(color);
		if (filled) {
			g2.fillOval(x, y, 2 * radius, 2 * radius);
		} else {
			g2.drawOval(x, y, 2 * radius, 2 * radius);
		}
		g2.dispose();
	}

	private void drawSquare(Graphics g, int x, int y, int side_length,
			boolean filled, Color color) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setColor(color);
		if (filled) {
			g2.fillRect(x, y, side_length, side_length);
		} else {
			g2.drawRect(x, y, side_length, side_length);
		}
		g2.dispose();
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
