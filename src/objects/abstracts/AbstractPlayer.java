package objects.abstracts;

import java.util.ArrayList;
import java.util.List;

import objects.Destination;
import objects.DestinationCard;
import objects.DestinationDeck;
import objects.DestinationHand;
import objects.TrainCarCard;
import objects.TrainCarDeck;
import objects.TrainCarHand;
import objects.TrainRoute;
import objects.interfaces.IPlayer;

public class AbstractPlayer implements IPlayer {

	public static final int MAX_NUM_STATIONS = 3;
	public static final int MAX_NUM_TRAINS = 45;

	protected final String name;
	protected int numTrains;
	protected int numStations;
	protected TrainCarHand hand = new TrainCarHand();
	protected DestinationHand destinations = new DestinationHand();
	protected int score;
	protected List<AbstractRoute> routes = new ArrayList<AbstractRoute>();

	public AbstractPlayer() {
		this("New Player");
	}

	public AbstractPlayer(String name) {
		this.name = name;
		this.numTrains = MAX_NUM_TRAINS;
		this.numStations = MAX_NUM_STATIONS;
	}

	@Override
	public void drawCardFromDeck(TrainCarDeck deck) {
		this.hand.addCard(deck.draw());
	}

	@Override
	public void drawCardFromDeck(DestinationDeck deck) {
		this.destinations.addCard(deck.draw());
	}

	public void claimRoute(TrainRoute route) {
		if (this.hand.numInHand(route.color) >= route.length) {
			this.routes.add(route);
			this.numTrains -= route.length;
			// TODO: Remove the cards from the players hand
			for (int i = 0; i < route.length; i++) {
				this.hand.removeCard(new TrainCarCard(route.getColor()));
			}
		}
	}

	public Object[][] getDestinationsInJTableFormat() {

		Object[][] rowData = new Object[destinations.size()][3];

		for (int i = 0; i < destinations.size(); i++) {
			Object[] toadd = { destinations.getCard(i).getRoute().getStart().toString() ,
					destinations.getCard(i).getRoute().getEnd().toString() ,
					destinations.getCard(i).getRoute().getScore() };

			rowData[i] = toadd;
			}

		return rowData;

	}

	public boolean placeStationOnDestination(Destination dest) {
		return dest.buildStation();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getScore() {
		return this.score;
	}

	@Override
	public TrainCarHand getHand() {
		return this.hand;
	}

	@Override
	public List<DestinationCard> getDestinations() {
		return this.destinations.hand;
	}

	@Override
	public int getNumTrains() {
		return this.numTrains;
	}

	@Override
	public int getNumStations() {
		return this.numStations;
	}

}
