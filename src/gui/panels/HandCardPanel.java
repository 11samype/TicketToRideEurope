package gui.panels;

import gui.CardImageFlyweight;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import objects.TrainCarCard;
import objects.TrainColor;

public class HandCardPanel extends JPanel {

	private BufferedImage cardImage;
	private TrainCarCard card;
	private int numCards = 0;

	public HandCardPanel(TrainColor trainColor) {
		this.card = new TrainCarCard(trainColor);
		this.setLayout(new CardLayout(5,0));
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
		g2.drawImage(getRotatedCardImage(), 0, 0, getWidth(), getHeight(), Color.GRAY, null);
		
		g2.setColor(Color.WHITE);
		int radius = 45;
		g2.fillArc(0, -25, radius, radius, 0, -90);

		g2.setColor(Color.BLACK);
		g2.drawString(Integer.toString(this.numCards), radius*2/3 - 2, radius*1/3 - 2);
		g2.dispose();
	}
}
