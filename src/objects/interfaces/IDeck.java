package objects.interfaces;

import java.util.List;

public interface IDeck<K> {
	public void shuffle();

	public void populate(List<K> cardList);

	public K draw();

	public List<K> getCards();

	public int size();

	public boolean isEmpty();

	public int numInDeck(K card);

}
