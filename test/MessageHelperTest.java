import static org.junit.Assert.*;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Test;

import utils.MessageHelper;

public class MessageHelperTest {

	@Test
	public void testGetGameLocale() {
		assertEquals(new Locale("game", "ORIG"), MessageHelper.getGameLocale());
	}

	@Test
	public void testGetDefaultCityNames() {
		ResourceBundle bundle = MessageHelper.getDefaultCityNames();

		MessageHelper.setLocale(Locale.US);
		assertEquals("Roma", MessageHelper.getDefaultCityNameFor("Rome"));

		assertEquals(47, bundle.keySet().size());
	}

	@Test
	public void testGetCityNames() {
		ResourceBundle bundle = MessageHelper.getCityNames();

		assertEquals(47, bundle.keySet().size());
	}

	@Test
	public void testSetLocale() {

		MessageHelper.setLocale(Locale.US);

		assertEquals(Locale.US, MessageHelper.getCurrentLocale());

		MessageHelper.setLocale(Locale.FRENCH);

		assertEquals(Locale.FRENCH, MessageHelper.getCurrentLocale());
	}

	@Test
	public void testUseGameLocale() {
		Locale game = new Locale("game", "ORIG");
		MessageHelper.setLocale(Locale.FRENCH);
		assertNotEquals(game, MessageHelper.getCurrentLocale());
		
		MessageHelper.useGameLocale();
		assertEquals(game, MessageHelper.getCurrentLocale());
		
		MessageHelper.setLocale(Locale.US);
		assertNotEquals(game, MessageHelper.getCurrentLocale());
	}

	@Test
	public void testGetStringFromBundleResourceBundleString1() {
		ResourceBundle bundle = MessageHelper.getDefaultCityNames();
		String key = "IDONTEXIST";
		assertEquals('!' + key + '!',
				MessageHelper.getStringFromBundle(bundle, key));
	}

	@Test
	public void testGetStringFromBundleResourceBundleStringObjectArray() {
		Object[] messageArguments = {
			    new Integer(7)
		};
		
		ResourceBundle bundle = MessageHelper.getMessages();
		String key = "player.score";
		assertEquals("Points: 7",
				MessageHelper.getStringFromBundle(bundle, key, messageArguments));
	}
	
	@Test
	public void testGetStringFromBundleResourceBundleStringObjectArrayMiss() {
		Object[] messageArguments = {
			    new Integer(7)
		};
		
		ResourceBundle bundle = MessageHelper.getMessages();
		String key = "IDONTEXIST";
		assertEquals('!' + key + '!',
				MessageHelper.getStringFromBundle(bundle, key, messageArguments));
	}


}
