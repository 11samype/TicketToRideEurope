import static org.junit.Assert.*;
import gui.PlayerPanel;
import objects.Player;
import objects.interfaces.IPlayer;

import org.junit.Test;


/**
 * TODO Put here a description of what this class does.
 *
 * @author samynpd.
 *         Created Apr 11, 2014.
 */
public class PlayerPanelTest {

	@Test
	public void testMakePlayerPanel() {
		PlayerPanel panel = new PlayerPanel(new Player());
		assertNotNull(panel);
		
	}
	
	@Test
	public void testSetPlayer() {
		PlayerPanel panel = new PlayerPanel(new Player());
		Player bob = new Player("Bob");
		panel.setPlayer(bob);
		assertEquals(bob, panel.getPlayer());
		
	}

}
