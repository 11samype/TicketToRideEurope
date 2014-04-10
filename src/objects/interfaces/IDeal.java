package objects.interfaces;

public interface IDeal<K> {

	public K removeCard(K card);
	
	public K removeCardAtPosition(int index);

	public void addCard(K card);
	
	public boolean isDealFull();

}
