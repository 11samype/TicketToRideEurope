import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gui.DrawableDestination;

import java.awt.Point;

import org.junit.Test;

import utils.SelectionHolder;


public class SelectionHolderTest {

	@Test
	public void testAdd() {
		SelectionHolder holder = new SelectionHolder();
		DrawableDestination dest1 = new DrawableDestination("here", new Point());
		DrawableDestination dest2 = new DrawableDestination("there", new Point());
		
		assertTrue(holder.isEmpty());
		
		holder.add(dest1);
		
		assertFalse(holder.isEmpty());
		assertFalse(holder.isFull());
		
		holder.add(dest2);
		
		assertTrue(holder.isFull());
		
		DrawableDestination dest3 = new DrawableDestination("somewhere", new Point());
		
		holder.add(dest3);
	}
	
	@Test
	public void testRemove() {
		SelectionHolder holder = new SelectionHolder();
		DrawableDestination dest1 = new DrawableDestination("here", new Point());
		DrawableDestination dest2 = new DrawableDestination("there", new Point());
		
		holder.add(dest1);
		holder.add(dest2);
		
		assertTrue(holder.isFull());
		
		assertTrue(holder.remove(dest2));
		
		assertFalse(holder.isFull());
		assertFalse(holder.isEmpty());
		
		assertTrue(holder.remove(dest1));
		
		assertFalse(holder.isFull());
		assertTrue(holder.isEmpty());

	}

}
