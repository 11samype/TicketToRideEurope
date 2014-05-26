import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import gui.Main;
import gui.panels.MainPanel;
import objects.interfaces.IPlayer;

import org.junit.Before;
import org.junit.Test;


public class MainPanelTest {
	
	private MainPanel panel;

	@Before
	public void setUp() throws Exception {
		this.panel = new MainPanel();
	}

	@Test
	public void testResetGame() {
		assertEquals(6, panel.getComponentCount());
		panel.removeAll();
		assertEquals(0, panel.getComponentCount());
		panel.resetGame();
		assertEquals(6, panel.getComponentCount());
	}

}
