package gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import utils.MessageHelper;

public class LocaleMenuActionListener implements ActionListener {
	
	protected Locale locale;
	protected LocaleChangeListener listener;
	
	public LocaleMenuActionListener(Locale locale, LocaleChangeListener listener) {
		this.locale = locale;
		this.listener = listener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MessageHelper.setLocale(this.locale);
		listener.notifyLocaleChange();
	}

}
