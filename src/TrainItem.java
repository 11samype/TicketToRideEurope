import java.awt.Color;


public abstract class TrainItem {

private Color color;
	
	public TrainItem(Color color) {
		this.setColor(color);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
