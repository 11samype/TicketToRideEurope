import static org.junit.Assert.assertEquals;
import gui.HandCardPanel;
import objects.TrainColor;

import org.junit.Test;


public class HandCardPanelTest {

	@Test
	public void testSetNumCards() {
		HandCardPanel panel = new HandCardPanel(TrainColor.BLACK);

		assertEquals(0, panel.getNumCards());
		panel.setNumCards(4);
		assertEquals(4, panel.getNumCards());
	}
}
