import static org.junit.Assert.*;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gui.factory.JPanelFactory;
import objects.TrainCarDeck;

import org.junit.Test;


public class JPanelFactoryTest {
	
	private final Color bg = Color.ORANGE;
	private final MouseAdapter adpt = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
		}
	};

	@Test
	public void testCreateDeckPanel() {
		TrainCarDeck deck = new TrainCarDeck();
		int cards = deck.size();
		
		MouseAdapter adpt;
		
		JPanelFactory.createDeckPanel(deck, bg, new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
			}
		});
	}

	@Test
	public void testCreatePanel() {
		JPanelFactory.createPanel(new CardLayout(0, 0), bg, null);
	}

}
