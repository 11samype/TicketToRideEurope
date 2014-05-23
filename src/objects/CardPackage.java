package objects;

public class CardPackage {
	private TrainCarCard card;
	private int source;
	public static final int DEAL = 0;
	public static final int DECK = 1;
	
	public CardPackage(TrainCarCard card, int source) {
		this.source = source;
		this.card = card;
	}
	
	public TrainCarCard card() {
		return this.card;
	}
	
	public int source() {
		return this.source;
	}
}
