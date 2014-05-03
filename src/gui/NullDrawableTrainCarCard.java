package gui;

import java.awt.image.BufferedImage;

import objects.TrainColor;

public class NullDrawableTrainCarCard extends DrawableTrainCarCard {
	private final BufferedImage image = null;

	public NullDrawableTrainCarCard() {
		super((TrainColor) null);
	}

	@Override
	public BufferedImage getImage() {
		return image;
	}
}
