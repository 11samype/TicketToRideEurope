

import static org.junit.Assert.*;
import objects.Destination;

import org.junit.Test;

public class DestinationTest {

	@Test
	public void testInitDestination() {
		Destination d = new Destination("NonNull");
		assertNotNull(d);
		d = new Destination(null);
		assertNotNull(d);
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

}