package gui.drawables;

import gui.CardImageFlyweight;

import java.awt.image.BufferedImage;

import objects.TrainCarCard;
import objects.TrainColor;

public class DrawableTrainCarCard extends TrainCarCard {
	private BufferedImage image;

	public DrawableTrainCarCard(TrainColor color) {
		super(color);
	}

	public DrawableTrainCarCard(TrainCarCard card) {
		this(card == null ? null : card.getColor());
	}

	public BufferedImage getImage() {
		if (this.image == null) {
			this.image = CardImageFlyweight.getInstance().getImageForColor(
					getValue());
		}
		return this.image;
	}
}
