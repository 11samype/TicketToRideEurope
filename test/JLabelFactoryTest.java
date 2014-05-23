import static org.junit.Assert.*;

import java.awt.Font;

import gui.DeckSizeLabel;
import gui.factory.JLabelFactory;

import javax.swing.JLabel;

import objects.TrainCarCard;
import objects.TrainCarDeck;
import objects.TrainColor;

import org.junit.Test;


public class JLabelFactoryTest {

	@Test
	public void testCreateJLabel() {
		JLabel lbl = JLabelFactory.createJLabel("hello world!");
		assertEquals("hello world!", lbl.getText());
		assertEquals(16, lbl.getFont().getSize());
		assertEquals("Tahoma", lbl.getFont().getFontName());
		assertTrue(lbl.getFont().isPlain());
	}

	@Test
	public void testCreateDeckSizeLabel() {
		TrainCarDeck deck = new TrainCarDeck();
		int cards = deck.size();
	
		DeckSizeLabel lbl = JLabelFactory.createDeckSizeLabel(deck);
		assertEquals(Integer.toString(cards), lbl.getText());
		assertEquals(24, lbl.getFont().getSize());
		assertEquals("Tahoma", lbl.getFont().getFontName());
		assertTrue(lbl.getFont().isPlain());
		
		int draw = cards / 2;
		for (int i = 0; i < draw ; i++) {
			deck.draw();
		}
		
		assertEquals(Integer.toString(cards - draw), lbl.getText());
	}

}
