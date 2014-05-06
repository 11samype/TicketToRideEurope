package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import objects.TrainColor;

public class CardImageFlyweight {

	private static CardImageFlyweight _flyweight;
	private HashMap<TrainColor, BufferedImage> imageMap = new HashMap<TrainColor, BufferedImage>();

	public static CardImageFlyweight getInstance() {
		if (_flyweight == null) {
			_flyweight = new CardImageFlyweight();
		}
		return _flyweight;
	}

	private CardImageFlyweight() {
		//
	}

	private BufferedImage _getImageForColor(TrainColor color) throws IOException {
		return ImageIO.read(new File("img//" + color.toString().toLowerCase()
				+ "card.png"));
	}

	public BufferedImage getImageForColor(TrainColor color) {
		BufferedImage img = null;
		if (!imageMap.containsKey(color) || imageMap.get(color) == null) {
			try {
				img = _getImageForColor(color);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				imageMap.put(color, img);
			}
		}
		return imageMap.get(color);
	}

}
