
import static org.junit.Assert.*;

import org.junit.Test;

import utils.MessageHelper;
import utils.exceptions.BuildStationException;
import utils.exceptions.ClaimRouteException;
import utils.exceptions.DestinationAfterTrainException;
import utils.exceptions.DestinationHasStationException;
import utils.exceptions.DrawCardException;
import utils.exceptions.NotEnoughCardsForRouteException;
import utils.exceptions.OutOfStationsException;
import utils.exceptions.RouteOwnedException;
import utils.exceptions.RouteTakenException;

public class LocalizedExceptionTest {

	@Test
	public void testBuildStationTitle() {
		BuildStationException e = new BuildStationException() {

			@Override
			public String getMessage() {
				return null; // don't need it
			}
		};
		assertEquals(getString("build.error.title"), e.getTitle());
	}

	@Test
	public void testHasStationMessage() {
		DestinationHasStationException e = new DestinationHasStationException();
		StringBuilder sb = new StringBuilder();
		sb.append(getString("build.error.unable.message"));
		sb.append(getString("build.error.alreadyTaken.message"));

		assertEquals(sb.toString(), e.getMessage());

	}

	@Test
	public void testClaimRouteTitle() {
		ClaimRouteException e = new ClaimRouteException() {

			@Override
			public String getMessage() {
				return null; // don't need it
			}
		};
		assertEquals(getString("claim.error.title"), e.getTitle());

	}

	@Test
	public void testRouteOwnedMessage() {
		RouteOwnedException e = new RouteOwnedException();
		StringBuilder sb = new StringBuilder();
		sb.append(getString("claim.error.unable.message"));
		sb.append(getString("claim.error.alreadyOwn.message"));

		assertEquals(sb.toString(), e.getMessage());

	}

	@Test
	public void testRouteTakenMessage() {
		RouteTakenException e = new RouteTakenException();
		StringBuilder sb = new StringBuilder();
		sb.append(getString("claim.error.unable.message"));
		sb.append(getString("claim.error.alreadyTaken.message"));

		assertEquals(sb.toString(), e.getMessage());

	}

	@Test
	public void testOutOfStationMessage() {
		OutOfStationsException e = new OutOfStationsException();
		StringBuilder sb = new StringBuilder();
		sb.append(getString("build.error.unable.message"));
		sb.append(getString("build.error.noStations.message"));

		assertEquals(sb.toString(), e.getMessage());

	}

	@Test
	public void testNotEnoughCardsForRouteMessage() {
		NotEnoughCardsForRouteException e = new NotEnoughCardsForRouteException();
		StringBuilder sb = new StringBuilder();
		sb.append(getString("claim.error.unable.message"));
		sb.append(getString("claim.error.notEnoughCards.message"));

		assertEquals(sb.toString(), e.getMessage());

	}

	@Test
	public void testDrawCardTitle() {
		DrawCardException e = new DrawCardException() {

			@Override
			public String getMessage() {
				return null; // don't need it
			}
		};
		assertEquals(getString("draw.error.title"), e.getTitle());
	}
	
	@Test
	public void testDestAfterTrainMessage() {
		DestinationAfterTrainException e = new DestinationAfterTrainException();
		StringBuilder sb = new StringBuilder();
		sb.append(getString("draw.error.unable.message"));
		sb.append(getString("draw.error.destAfterTrain.message"));

		assertEquals(sb.toString(), e.getMessage());

	}

	private String getString(String key) {
		return MessageHelper.getStringFromBundle(MessageHelper.getMessages(),
				key);
	}

}
