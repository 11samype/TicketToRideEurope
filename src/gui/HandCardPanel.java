package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import objects.TrainCarCard;
import objects.TrainColor;

/**
 * TODO Put here a description of what this class does.
 * 
 * @author samynpd. Created Mar 28, 2014.
 */
public class HandCardPanel extends JPanel {

	private BufferedImage cardImage;

	private TrainCarCard card;

	private int numCards = 0;

	public HandCardPanel(TrainColor trainColor) {
		this.card = new TrainCarCard(trainColor);
		this.setLayout(new CardLayout(5, 0));
	}

	public void setNumCards(int num) {
		this.numCards = num;
	}

	public synchronized BufferedImage getCardImage() {
		// loads card image from file
		
		if (this.cardImage == null) {
			try {
				this.cardImage = ImageIO.read(new File("img//"
						+ this.card.getColor().toString().toLowerCase()
						+ "card.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return this.cardImage;
	}

	public BufferedImage getRotatedCardImage() {
		BufferedImage origCardImage = getCardImage();

		double rotationRequired = Math.toRadians(90);
		double locationX = origCardImage.getWidth() / 2;
		double locationY = origCardImage.getHeight() / 2;

		AffineTransform tx = AffineTransform.getRotateInstance(
				rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);

		return op.filter(origCardImage, null);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(getRotatedCardImage(), 0, 0, getWidth(), getHeight(),
				Color.BLACK, null);
		// drawCircle(g, -20, -20, 45, true, Color.WHITE);
		g.setColor(Color.WHITE);
		g.fillArc(0, -25, 45, 45, 0, -90);
		// drawCircle(g, -20, -20, 45, false, Color.black);

		g.setColor(Color.BLACK);
		g.drawString(Integer.toString(this.numCards), 28, 13);
	}

	private void drawCircle(Graphics g, int x, int y, int radius,
			boolean filled, Color color) {
		Color save = g.getColor();
		g.setColor(color);
		if (!filled)
			g.drawOval(x, y, radius, radius);
		else
			g.fillOval(x, y, radius, radius);
		g.setColor(save);
	}

	
	public int getNumCards() {
		return this.numCards;
	}
	
	public BufferedImage cardImage() {
		return this.cardImage;
	}

}
