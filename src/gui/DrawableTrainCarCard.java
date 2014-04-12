package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import objects.TrainCarCard;
import objects.TrainColor;

public class DrawableTrainCarCard extends TrainCarCard {
	private BufferedImage image;

	public DrawableTrainCarCard(TrainColor color) {
		super(color);
	}

	public BufferedImage getImage() {
		if (this.image == null) {
			try {
				this.image = ImageIO.read(new File("img//"
						+ getValue().toString().toLowerCase() + "card.png"));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return this.image;
	}
}
