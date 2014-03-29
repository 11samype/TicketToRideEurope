package gui;

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

	public HandCardPanel(TrainColor trainColor) {
		this.card = new TrainCarCard(trainColor);
	}

	public synchronized BufferedImage getCardImage() {
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
		System.out.printf("%d %d\n", getWidth(), getHeight());
		g.drawImage(getRotatedCardImage(), 0, 0, getWidth(), getHeight(),
				Color.BLACK, null);
	}

}
