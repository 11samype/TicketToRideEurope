import objects.TrainCarDeck;


public class ObjectMother {
	public static TrainCarDeck emptyDeck() {
		TrainCarDeck d = new TrainCarDeck();
		while (!d.isEmpty()) {
			d.draw();
		}
		return d;
	}
}
