import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JLabel;

import gui.panels.PlayerPanel;
import objects.Player;
import objects.interfaces.IPlayer;

import org.junit.Test;

import utils.GameState;
import utils.MessageHelper;


public class PlayerPanelTest {

	@Test
	public void testPlayerPanel() {
		List<Player> players = new ArrayList<Player>();
		players.add(new Player("Bob"));
		GameState.withPlayers(players);
		Player player = GameState.getCurrentPlayer();
		PlayerPanel pnl = new PlayerPanel(player);
		assertEquals(4, pnl.getComponentCount());
		
		try {
			Field name = pnl.getClass().getDeclaredField("lblName");
			name.setAccessible(true);
			Field stations = pnl.getClass().getDeclaredField("lblStations");
			stations.setAccessible(true);
			Field trains =  pnl.getClass().getDeclaredField("lblTrainCars");
			trains.setAccessible(true);
			Field points = pnl.getClass().getDeclaredField("lblPoints");
			points.setAccessible(true);
			
			JLabel lblName = (JLabel) name.get(pnl);
			JLabel lblStations = (JLabel) stations.get(pnl);
			JLabel lblTrains = (JLabel) trains.get(pnl);
			JLabel lblPoints = (JLabel) points.get(pnl);
			
		
			ResourceBundle messages = MessageHelper.getMessages();
			String strStations = MessageHelper.getStringFromBundle(messages, "player.numStations", player.getNumStations());
			String strTrains = MessageHelper.getStringFromBundle(messages, "player.numTrains", player.getNumTrains());
			String strScore = MessageHelper.getStringFromBundle(messages, "player.score", player.getScore());
			
			assertEquals(player.getName(), lblName.getText());
			assertEquals(strStations, lblStations.getText());
			assertEquals(strTrains, lblTrains.getText());
			assertEquals(strScore, lblPoints.getText());
			
		} catch (Exception e) {
			fail();
		}
		
	}

}
