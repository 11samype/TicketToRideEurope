import static org.junit.Assert.*;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.junit.Test;

import utils.MessageHelper;


public class MessageHelperTest {

	@Test
	public void testGetGameLocale() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDefaultCityNames() {
		ResourceBundle bundle = MessageHelper.getDefaultCityNames();
		
		assertEquals("Cadiz", MessageHelper.getStringFromBundle(bundle, "Cadiz"));
		
		assertEquals(47, bundle.keySet().size());
	}

	@Test
	public void testGetCityNames() {
		ResourceBundle bundle = MessageHelper.getCityNames();
		
		assertEquals(47, bundle.keySet().size());
	}

	@Test
	public void testGetMessages() {
		fail("Not yet implemented");
		
	}

	@Test
	public void testSetLocale() {
		
		MessageHelper.setLocale(Locale.US);
		
		assertEquals(Locale.US, MessageHelper.getCurrentLocale());
		
		MessageHelper.setLocale(Locale.FRENCH);
		
		assertEquals(Locale.FRENCH, MessageHelper.getCurrentLocale());
	}

	@Test
	public void testGetCurrentLocale() {
		fail("Not yet implemented");
	}

	@Test
	public void testUseGameLocale() {
		MessageHelper.useGameLocale();
		assertEquals(new Locale("game", "ORIG"), MessageHelper.getCurrentLocale());
	}

	@Test
	public void testGetDefaultCityNameFor() {
		fail("Not yet implemented");
	}

	@Test(expected = MissingResourceException.class)
	public void testGetStringFromBundleResourceBundleString() {
		ResourceBundle bundle = MessageHelper.getDefaultCityNames();
		MessageHelper.getStringFromBundle(bundle, "IDONTEXIST");
	}

	@Test
	public void testGetStringFromBundleResourceBundleStringObjectArray() {
		fail("Not yet implemented");
	}

}
