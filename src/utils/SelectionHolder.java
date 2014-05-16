package utils;

import java.util.ArrayList;

public class SelectionHolder extends ArrayList<SelectionHolder.Selectable> {
	private final int MAX_SIZE;
	private boolean allowDuplicates = false;

	public SelectionHolder(int max_size) {
		MAX_SIZE = max_size;
	}
	
	public void allowDuplicates(boolean dups) {
		this.allowDuplicates = dups;
	}

	@Override
	public void clear() {
		for (Selectable selected : this) {
			selected.deselect();
		}
		super.clear();
	}

	@Override
	public boolean add(Selectable s) {
		if (!this.allowDuplicates && this.contains(s)) {
			return false;
		}
		s.select();
		if (this.size() >= MAX_SIZE) {
			this.clear();
		}
		return super.add(s);
	}

	@Override
	public boolean remove(Object o) {
		if (o != null && o instanceof Selectable) {
			Selectable s = (Selectable) o;
			s.deselect();
		}
		return super.remove(o);
	}

	public boolean isFull() {
		return this.size() == MAX_SIZE;
	}

	public interface Selectable {
		public void select();
		public void deselect();
	}


}