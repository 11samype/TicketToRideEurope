import static org.junit.Assert.*;
import gui.DestinationTable;

import org.junit.Test;


public class DestinationTableTest {

	@Test
	public void testIsCellEditable() {
		DestinationTable table = new DestinationTable();
		
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				assertFalse(table.isCellEditable(i, j));
			}
		}
		
	}

}
