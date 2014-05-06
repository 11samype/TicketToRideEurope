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

	public DrawableTrainCarCard(TrainCarCard card) {
		super(card.getColor());
	}

	public BufferedImage getImage() {
		if (this.image == null) {
			this.image = CardImageFlyweight.getInstance().getImageForColor(
					getValue());
		}
		return this.image;
	}
}
