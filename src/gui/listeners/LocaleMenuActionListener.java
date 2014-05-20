package gui.listeners;

import gui.interfaces.IRefreshable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import utils.MessageHelper;

public class LocaleMenuActionListener implements ActionListener {
	
	protected Locale locale;
	private LocaleChangeListener comp;
	
	public LocaleMenuActionListener(Locale locale, LocaleChangeListener comp) {
		this.locale = locale;
		this.comp = comp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MessageHelper.setLocale(locale);
		comp.notifyLocaleChange();
	}

}
