package objects.interfaces;

public interface IDeal<K> {

	public K removeCard(K card);
	
	public K removeCard(int index);

	public void addCard(K card);
	
	public boolean full();

}
