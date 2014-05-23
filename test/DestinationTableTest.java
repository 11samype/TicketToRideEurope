import static org.junit.Assert.*;

import java.util.Locale;
import java.util.ResourceBundle;

import gui.panels.DestinationTable;
import objects.Destination;
import objects.DestinationCard;
import objects.DestinationRoute;
import objects.Player;

import org.junit.Test;

import utils.MessageHelper;


public class DestinationTableTest {

	@Test
	public void testDestinationTable() {
		DestinationTable tbl = new DestinationTable();
		DestinationRoute route = new DestinationRoute(new Destination("Here"), new Destination("there"));
		tbl.addDestination(route);
		for (int i = 0; i < tbl.getRowCount(); i++) {
			for (int j = 0; j < tbl.getColumnCount(); j++) {
				assertFalse(tbl.isCellEditable(i, j));
			}
		}
	}

	@Test
	public void testGetColumnNames() {
		DestinationTable tbl = new DestinationTable();
		MessageHelper.setLocale(Locale.US);
		
		ResourceBundle messages = MessageHelper.getMessages();
		String destStart = MessageHelper.getStringFromBundle(messages, "dest.start");
		String destEnd = MessageHelper.getStringFromBundle(messages, "dest.end");
		String destScore = MessageHelper.getStringFromBundle(messages, "dest.score");
		Object[] expected =  new Object[] { destStart, destEnd, destScore };
		
		Object[] names = tbl.getColumnNames();
		assertEquals(3, names.length);
		assertArrayEquals(expected, names);
		
		// test locale change
		MessageHelper.setLocale(Locale.FRANCE);
		tbl.notifyLocaleChange();
		messages = MessageHelper.getMessages();
		destStart = MessageHelper.getStringFromBundle(messages, "dest.start");
		destEnd = MessageHelper.getStringFromBundle(messages, "dest.end");
		destScore = MessageHelper.getStringFromBundle(messages, "dest.score");
		expected =  new Object[] { destStart, destEnd, destScore };
		assertArrayEquals(expected, tbl.getColumnNames());
	}

	@Test
	public void testAddDestination() {
		DestinationRoute route = new DestinationRoute(new Destination("Here"), new Destination("there"));
		DestinationRoute route2 = new DestinationRoute(new Destination("Here"), new Destination("there"), 8);
		
		DestinationTable tbl = new DestinationTable();
		tbl.addDestination(route);
		tbl.addDestination(route2);
		assertEquals(2, tbl.getRowCount());
	}

	@Test
	public void testGetRouteInRow() {
		DestinationRoute route = new DestinationRoute(new Destination("Here"), new Destination("there"));
		DestinationRoute route2 = new DestinationRoute(new Destination("Here"), new Destination("there"), 8);
		
		DestinationTable tbl = new DestinationTable();
		tbl.addDestination(route);
		tbl.addDestination(route2);
		assertSame(route, tbl.getRouteInRow(0));
		assertSame(route2, tbl.getRouteInRow(1));
	}
 
	@Test
	public void testSetPlayer() {
		Player p = new Player();
		DestinationRoute route = new DestinationRoute(new Destination("Here"), new Destination("there"));
		DestinationRoute route2 = new DestinationRoute(new Destination("Here"), new Destination("there"), 8);
		p.getDestinationHand().add(new DestinationCard(route));
		p.getDestinationHand().add(new DestinationCard(route2));
		DestinationTable tbl = new DestinationTable();
		tbl.setPlayer(p);
		assertEquals(2, tbl.getRowCount());
		assertSame(route, tbl.getRouteInRow(0));
		assertSame(route2, tbl.getRouteInRow(1));
		
		p.getDestinationHand().clear();
		tbl.setPlayer(p);
		assertEquals(0, tbl.getRowCount());
	}

}
