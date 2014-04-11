import static org.junit.Assert.*;
import gui.MapPanel;

import org.junit.Test;


public class MapPanelTest {

	@Test
	public void testConstructMapPanel() {
		MapPanel map = new MapPanel();
		
		assertNotNull(map);
	}
	
	@Test
	public void testSetMapName() {
		MapPanel map = new MapPanel();
		map.setMapName("Mappy");
		assertEquals("Mappy", map.getMapName());
	}

}
