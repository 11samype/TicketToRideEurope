package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import objects.TrainCarCard;
import objects.TrainCarDeel;
import objects.TrainColor;

/**
 * TODO Put here a description of what this class does.
 *
 * @author samynpd.
 *         Created Mar 28, 2014.
 */
public class DeelCardPanel extends JPanel {
	
	private BufferedImage cardImage;
	
	private TrainCarCard card = new TrainCarCard(TrainColor.BLACK);
	
	public DeelCardPanel() {
		//this.setPreferredSize(SIZE);
	}
	
	public BufferedImage getCardImage() {
		try {
			this.cardImage = ImageIO.read(new File("img//" + this.card.getValue().toString().toLowerCase() + "card.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.cardImage;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.printf("%d %d\n", getWidth(), getHeight());
		g.drawImage(getCardImage(), 0, 0, getWidth(), getHeight(), Color.BLACK, null);
	}
}
