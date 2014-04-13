package utils;

import gui.DrawableDestination;

import java.util.ArrayList;

public class SelectionHolder extends ArrayList<DrawableDestination> {
	private final static int MAX_SIZE = 2;

	@Override
	public void clear() {
		for (DrawableDestination selectedDest : this) {
			selectedDest.deselect();
		}
		super.clear();
	}

	@Override
	public boolean add(DrawableDestination d) {
		d.select();
		if (this.size() >= MAX_SIZE) {
			this.clear();
		}
		return super.add(d);
	}

	@Override
	public boolean remove(Object o) {
		if (o != null && o instanceof DrawableDestination) {
			DrawableDestination dest = (DrawableDestination) o;
			dest.deselect();
		}
		return super.remove(o);
	}

	public boolean isFull() {
		return this.size() == MAX_SIZE;
	}

}