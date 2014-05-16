package utils;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageHelper {
	private static final String _cityBundleName = "Cities";
	private static final String _messagesBundleName = "Messages";

	private static Locale CURRENT_LOCALE = getGameLocale();

	public static Locale getGameLocale() {
		return new Locale("game", "ORIG");
	}

	public static ResourceBundle getDefaultCityNames() {
		return ResourceBundle.getBundle(_cityBundleName, getGameLocale());
	}

	public static ResourceBundle getCityNames() {
		return ResourceBundle.getBundle(_cityBundleName, getCurrentLocale());
	}

	public static ResourceBundle getMessages(){
		return ResourceBundle.getBundle(_messagesBundleName, getCurrentLocale());
	}

	public static void setLocale(Locale locale) {
		CURRENT_LOCALE = locale;
	}

	public static Locale getCurrentLocale() {
		return CURRENT_LOCALE;
	}

	public static void useGameLocale() {
		setLocale(getGameLocale());
	}

	public static String getDefaultCityNameFor(String cityNameInOtherLocale) {
		if (CURRENT_LOCALE.equals(getGameLocale()))
			return cityNameInOtherLocale;
		ResourceBundle defaultNames = getCityNames();
		for (Iterator<String> iterator = defaultNames.keySet().iterator(); iterator.hasNext();) {
			String originalName = iterator.next();
			String translated = getStringFromBundle(defaultNames, originalName);
			if (cityNameInOtherLocale.equals(translated))
				return originalName;
		}
		return null;
	}

	public static String getStringFromBundle(ResourceBundle bundle, String key) {
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
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
			return '!' + key + '!';
		}
	}
}
