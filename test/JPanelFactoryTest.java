import static org.junit.Assert.*;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import gui.DeckSizeLabel;
import gui.factory.JPanelFactory;
import objects.TrainCarDeck;

import org.junit.Test;


public class JPanelFactoryTest {
	
	private final Color bg = Color.ORANGE;
	
	
	@Test
	public void testConstructor() {
		JPanelFactory fact = new JPanelFactory();
		assertNotNull(fact);
	}
	
	@Test
	public void testCreateDeckPanel() {
		final TrainCarDeck deck = new TrainCarDeck();
		int cards = deck.size();
		
		JPanel panel = JPanelFactory.createDeckPanel(deck, bg, new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				deck.draw();
			}
		});
		assertEquals(bg, panel.getBackground());
		assertTrue(panel.getComponent(0) instanceof DeckSizeLabel);
		DeckSizeLabel lbl = (DeckSizeLabel) panel.getComponent(0);
		assertEquals(""+cards, lbl.getText());
		assertEquals(1, panel.getMouseListeners().length);
	}

	@Test
	public void testCreatePanel() {
		LayoutManager layout = new CardLayout(0, 0);
		JPanel panel = JPanelFactory.createPanel(layout, bg, null);
		assertEquals(layout, panel.getLayout());
		assertEquals(bg, panel.getBackground());
		assertEquals(0, panel.getMouseListeners().length);
	}
 
}
