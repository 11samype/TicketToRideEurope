package objects.interfaces;

import java.awt.Graphics;
import java.awt.geom.Point2D;

public interface IDrawable {
	public void drawOn(Graphics g);

	public boolean contains(Point2D p);
}
