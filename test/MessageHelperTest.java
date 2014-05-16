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
	public void testGetStringFromBundleResourceBundleString() {
		ResourceBundle bundle = MessageHelper.getDefaultCityNames();
		String key = "IDONTEXIST";
		assertEquals('!' + key + '!',
				MessageHelper.getStringFromBundle(bundle, key));
	}

	@Test
	public void testGetStringFromBundleResourceBundleStringObjectArray() {
		fail("Not yet implemented");
	}

}
