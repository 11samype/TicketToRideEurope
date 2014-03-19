package objects;

import java.util.List;

public interface IDeck {
	public void shuffle();

	public void populate(List<ICard> cardList);

	public ICard draw();

	public List<ICard> getCards();

	public int size();

	public boolean isEmpty();
}
