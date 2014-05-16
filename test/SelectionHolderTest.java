import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gui.DrawableDestination;

import java.awt.Point;

import org.junit.Test;

import utils.SelectionHolder;

public class SelectionHolderTest {

	@Test
	public void testAdd() {
		SelectionHolder holder = new SelectionHolder(2);
		DrawableDestination dest1 = new DrawableDestination("here", new Point());
		DrawableDestination dest2 = new DrawableDestination("there",
				new Point());

		assertTrue(holder.isEmpty());

		assertTrue(holder.add(dest1));

		assertFalse(holder.isEmpty());
		assertFalse(holder.isFull());

		assertTrue(holder.add(dest2));

		assertTrue(holder.isFull());
	}

	@Test
	public void testManualClear() {
		SelectionHolder holder = new SelectionHolder(1);
		DrawableDestination dest1 = new DrawableDestination("here", new Point());

		assertTrue(holder.isEmpty());
		holder.add(dest1);
		assertFalse(holder.isEmpty());
		assertTrue(holder.isFull());

		holder.clear();
		assertTrue(holder.isEmpty());
		assertFalse(holder.isFull());
	}

	@Test
	public void testOverflowClear() {
		SelectionHolder holder = new SelectionHolder(2);
		DrawableDestination dest1 = new DrawableDestination("here", new Point());
		DrawableDestination dest2 = new DrawableDestination("there",
				new Point());
		DrawableDestination dest3 = new DrawableDestination("somewhere",
				new Point());

		assertTrue(holder.isEmpty());
		assertTrue(holder.add(dest1));assertTrue(holder.add(dest2));
		assertTrue(holder.isFull());

		assertTrue(holder.add(dest3));
		assertFalse(holder.isEmpty());
		assertFalse(holder.isFull());
		assertTrue(holder.add(dest1));
		assertTrue(holder.isFull());
	}

	@Test
	public void testRemove() {
		SelectionHolder holder = new SelectionHolder(2);
		DrawableDestination dest1 = new DrawableDestination("here", new Point());
		DrawableDestination dest2 = new DrawableDestination("there",
				new Point());

		assertTrue(holder.add(dest1));
		assertTrue(holder.add(dest2));

		assertTrue(holder.isFull());

		assertTrue(holder.remove(dest2));

		assertFalse(holder.isFull());
		assertFalse(holder.isEmpty());

		assertTrue(holder.remove(dest1));

		assertFalse(holder.isFull());
		assertTrue(holder.isEmpty());

		assertFalse(holder.remove(null));
		assertFalse(holder.remove(new Integer(2)));

	}

	@Test
	public void testAddWithDuplicates() {
		SelectionHolder holder = new SelectionHolder(3);
		holder.allowDuplicates(true);
		DrawableDestination dest1 = new DrawableDestination("here", new Point());

		assertTrue(holder.isEmpty());
		assertTrue(holder.add(dest1));
		assertFalse(holder.isEmpty());
		assertFalse(holder.isFull());

		assertTrue(holder.add(dest1));
		assertTrue(holder.add(dest1));
		assertFalse(holder.isEmpty());
		assertTrue(holder.isFull());
		assertTrue(holder.add(dest1));
		assertFalse(holder.isFull());
	}

	@Test
	public void testAddWithoutDuplicates() {
		SelectionHolder holder = new SelectionHolder(2);
		holder.allowDuplicates(false);
		DrawableDestination dest1 = new DrawableDestination("here", new Point());
		DrawableDestination dest2 = new DrawableDestination("there",
				new Point());
		DrawableDestination dest3 = new DrawableDestination("somewhere",
				new Point());

		assertTrue(holder.isEmpty());
		assertTrue(holder.add(dest1));
		assertFalse(holder.isEmpty());
		assertFalse(holder.isFull());

		assertFalse(holder.add(dest1));
		assertTrue(holder.add(dest2));
		assertFalse(holder.isEmpty());
		assertTrue(holder.isFull());
		
		assertFalse(holder.add(dest1));
		assertFalse(holder.add(dest2));
		assertTrue(holder.isFull());
		
		assertTrue(holder.add(dest3));
		assertFalse(holder.isFull());

	}

}
