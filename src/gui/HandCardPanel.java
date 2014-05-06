package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import objects.TrainCarCard;
import objects.TrainColor;

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
		this.repaint();
		this.revalidate();
	}

	public int getNumCards() {
		return this.numCards;
	}

	public synchronized BufferedImage getCardImage() {
		if (this.cardImage == null) {
			this.cardImage = CardImageFlyweight.getInstance().getImageForColor(
					this.card.getColor());
		}
		return this.cardImage;
	}

	public BufferedImage getRotatedCardImage() {
		double rotationRequired = Math.toRadians(90);

		BufferedImage origCardImage = getCardImage();
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
		Graphics2D g2 = (Graphics2D) g.create();

		BufferedImage origCardImage = getCardImage();
		int centerX = getWidth() / 2;
		int centerY = getHeight() / 2;

		int maxImageHeight = getHeight();
		int maxImageWidth = getWidth() / 2;

		int degrees = 90;
		degrees = (degrees / 90) * 90; // round to nearest 90

		g2.translate(centerX, centerY);
		g2.rotate(Math.toRadians(degrees));
		g2.translate(-centerX, -centerY);

		if (degrees % 90 != 0)
			g2.drawImage(origCardImage, (maxImageWidth / 2), 0, maxImageWidth,
					getHeight(), null, null);
		else
			g2.drawImage(origCardImage, 0, 0, maxImageWidth * 2, getHeight(),
					null, null);

		g2.translate(centerX, centerY);
		g2.rotate(Math.toRadians(-degrees));
		g2.translate(-centerX, -centerY);

		g2.setColor(Color.WHITE);
		int radius = 45;
		if (degrees % 90 != 0)
			g2.translate(-maxImageWidth, 0);
		else
			g2.translate(-maxImageHeight / 2, 0);
		g2.fillArc(centerX - (radius / 2), -(radius / 2), radius, radius, 0,
				-90);
		drawCircle(g2, centerX - (radius / 2), -(radius / 2), radius, false,
				Color.black);
		if (degrees % 90 != 0)
			g2.translate(maxImageWidth, 0);
		else
			g2.translate(maxImageHeight / 2, 0);

		g2.setColor(Color.BLACK);
		// g2.drawString(Integer.toString(this.numCards), (int)
		// ((radius*Math.sqrt(2))/2), 15);

		g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		g2.drawLine(0, 0, getWidth(), getHeight());
		g2.drawLine(0, getHeight(), getWidth(), 0);

		g2.dispose();
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

}
