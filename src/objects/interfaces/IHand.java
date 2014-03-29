package objects.interfaces;

/**
 * TODO Put here a description of what this class does.
 * 
 * @author samynpd. Created Mar 28, 2014.
 */
public interface IHand<K> {

	public void addCard(K card);

	public void removeCard(K card);

	public void removeCard(int index);

	public void playCard(K card);

	public void playCard(int index);

}