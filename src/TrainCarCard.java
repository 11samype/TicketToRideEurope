import java.awt.Color;


public class TrainCarCard extends Card {

	// locomotives?
	// maybe extend trainitem?
	
	private Color color;
	
	public TrainCarCard(Color color) {
		this.setColor(color);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}
