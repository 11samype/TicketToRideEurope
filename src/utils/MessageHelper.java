package utils;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageHelper {
	private static final String _cityBundleName = "Cities";

	public static Locale CURRENT_LOCALE = getGameLocale();
	public static ResourceBundle CITIES_BUNDLE = ResourceBundle.getBundle(_cityBundleName, CURRENT_LOCALE);

	public static Locale getGameLocale() {
		return new Locale("game", "ORIG");
	}

	public static ResourceBundle getDefaultCityNames() {
		return ResourceBundle.getBundle(_cityBundleName, getGameLocale());
	}

	public static void setLocale(Locale locale) {
		CURRENT_LOCALE = locale;
		CITIES_BUNDLE = ResourceBundle.getBundle(_cityBundleName, CURRENT_LOCALE);
	}

	public static void useGameLocale() {
		setLocale(getGameLocale());
	}

	public static String getDefaultCityNameFor(String cityNameInOtherLocale) {
		for (Iterator<String> iterator = CITIES_BUNDLE.keySet().iterator(); iterator.hasNext();) {
			String originalName = iterator.next();
			if (cityNameInOtherLocale.equals(getStringFromBundle(CITIES_BUNDLE, originalName)))
					return originalName;
		}
		return null;
	}

	public static String getStringFromBundle(ResourceBundle bundle, String key) {
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			e.printStackTrace();
			return '!' + key + '!';
		}
	}

	public static String getStringFromBundle(ResourceBundle bundle, String key,
			Object... params) {
		try {
			MessageFormat formatter = new MessageFormat(bundle.getString(key),
					CURRENT_LOCALE);
			return formatter.format(params);
		} catch (MissingResourceException e) {
			e.printStackTrace();
			return '!' + key + '!';
		}
	}
}
