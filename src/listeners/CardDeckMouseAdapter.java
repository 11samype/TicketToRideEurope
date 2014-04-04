package listeners;

import java.awt.event.MouseAdapter;

import managers.GameManager;

/**
 * TODO Put here a description of what this class does.
 *
 * @author samynpd.
 *         Created Apr 3, 2014.
 */
public class CardDeckMouseAdapter extends MouseAdapter {

	GameManager gameManager;
	
	/**
	 * TODO Put here a description of what this constructor does.
	 *
	 */
	public CardDeckMouseAdapter(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
}
