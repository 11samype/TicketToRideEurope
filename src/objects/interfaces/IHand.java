package objects.interfaces;

public interface IHand<K> {

	public void addCard(K card);

	public void removeCard(K card);

	public void removeCard(int index);

	// public void playCard(K card);

	// public void playCard(int index);

	public int size();
	
	public K getCard(int index);

}
