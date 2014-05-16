import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import objects.Destination;
import objects.Player;

import org.junit.Test;

import utils.exceptions.DestinationHasStationException;
import utils.exceptions.OutOfStationsException;

public class DestinationTest {

	@Test
	public void testInitDestination() {
		Destination d = new Destination("NonNull");
		assertNotNull(d);

		d = new Destination(null);
		assertNull(d.getName());
	}

	@Test
	public void testGetDestinationName() {
		Destination d = new Destination("Budapest");
		assertEquals("Budapest", d.getName());
	}

	@Test
	public void testDestinationToString() {
		Destination d = new Destination("Paris");
		assertEquals("Paris", d.toString());
	}

	@Test
	public void testDestinationEquals() {
		Destination paris = new Destination("Paris");
		Destination budapset = new Destination("Budapest");
		Destination paris_copy = new Destination("Paris");
		assertNotEquals(budapset, paris);
		assertEquals(paris, paris_copy);

		assertNotEquals(paris, null);
		assertNotEquals(budapset, new Integer(2));

		Destination nullName = new Destination(null);
		assertNotEquals(paris, nullName);
		assertNotEquals(nullName, paris);
	}

	@Test
	public void testBuildStation() {
		Destination d = new Destination("place");
		Player builder = new Player("Bob");
		assertFalse(d.hasStation());
		try {
			d.buildStation(builder);
			assertTrue(d.hasStation());
		} catch (OutOfStationsException e) {
			// nothing
		} catch (DestinationHasStationException e) {
			// nothing
		}
	}

	@Test(expected=DestinationHasStationException.class)
	public void testBuildStationOnDestinationWithStation() throws DestinationHasStationException {
		Destination d = new Destination("place");
		Player builder = new Player("Bob");
		
		try {
			boolean tryBuild = d.buildStation(builder);
			assertTrue(tryBuild);
			tryBuild = d.buildStation(builder);
			assertFalse(tryBuild); // throws
		} catch (OutOfStationsException e) {
			// nothing
		} catch (DestinationHasStationException e) {
			throw e;
		}
	}
}
