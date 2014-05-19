package gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import utils.MessageHelper;

public class LocaleMenuActionListener implements ActionListener {
	
	protected Locale locale;
	
	public LocaleMenuActionListener(Locale locale) {
		this.locale = locale;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MessageHelper.setLocale(this.locale);
	}

}
