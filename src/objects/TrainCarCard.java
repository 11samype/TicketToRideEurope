package objects;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import objects.abstracts.AbstractCard;
import objects.interfaces.ITrainItem;

public class TrainCarCard extends AbstractCard<TrainColor> implements
		ITrainItem {
	
	private BufferedImage carImage;
	
	private BufferedImage getCarImage() {
		if (this.carImage == null) {
			try {
				this.carImage = ImageIO.read(new File("img//" + getValue().toString().toLowerCase() + "card.png"));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return this.carImage;
	}

	public TrainCarCard(TrainColor color) {
		this.value = color;
//		getCarImage();
	}

	@Override
	public TrainColor getColor() {
		return getValue();
	}

	@Override
	public Color getAwtColor() {
		return getValue().getAwtColor();
	}

}
