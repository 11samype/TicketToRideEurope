import static org.junit.Assert.*;

import javax.swing.JMenu;

import gui.GameMenuBar;
import gui.panels.MainPanel;

import org.junit.Before;
import org.junit.Test;


public class GameMenuBarTest {

	private GameMenuBar menuBar;
	
	@Before
	public void setUp() throws Exception {
		MainPanel panel = new MainPanel();
		this.menuBar =  new GameMenuBar(null);
	}

	@Test
	public void testMenuInitialize() {
		assertEquals(2, this.menuBar.getComponentCount());
		JMenu menu1 = (JMenu) this.menuBar.getComponent(0);
		assertEquals(4, menu1.getItemCount());
		JMenu menu2 = (JMenu) this.menuBar.getComponent(1);
		assertEquals(5, menu2.getItemCount());
		
	}

}
