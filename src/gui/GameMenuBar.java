package gui;

import gui.listeners.LocaleMenuActionListener;
import gui.listeners.NumPlayerActionListener;
import gui.panels.MainPanel;

import java.util.Locale;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import utils.MessageHelper;

public class GameMenuBar extends JMenuBar {
	
	private MainPanel panel;

	public GameMenuBar(MainPanel panel) {
	this.panel = panel;
	add(getLocaleMenu());
	add(getNumPlayersMenu());
	}

	protected JMenu getLocaleMenu() {
		JMenu localeMenu = new JMenu(MessageHelper.getStringFromBundle(MessageHelper.getMessages(), "menu.locale.title"));
		//		ButtonGroup group = new ButtonGroup();
		String [] lblNames = new String[] {"English", "French", "German", "Multi-National"};
		Locale[] locales = new Locale[] {Locale.US, Locale.FRANCE, Locale.GERMANY, MessageHelper.getGameLocale()};
		for (int i = 0; i < lblNames.length ; i++ ) {
			JMenuItem item = new JMenuItem(lblNames[i]);
			item.addActionListener(new LocaleMenuActionListener(locales[i], panel));
			//			group.add(item);
			localeMenu.add(item);
		}
		return localeMenu;
	}

	protected JMenu getNumPlayersMenu() {
		JMenu numPlayerMenu = new JMenu("Players");
		ButtonGroup numPlayerGroup = new ButtonGroup();
		String[] lblNumbers = new String[] {"1", "2", "3", "4", "5"};

		for (int i = 0; i < lblNumbers.length; i++) {
			JRadioButtonMenuItem button = new JRadioButtonMenuItem(lblNumbers[i]);
			button.addActionListener(new NumPlayerActionListener(Integer.parseInt(lblNumbers[i]), panel));
			numPlayerGroup.add(button);
			numPlayerMenu.add(button);
			if (lblNumbers[i] == "4") {
				button.setSelected(true);;
			}
		}
		return numPlayerMenu;
	}
}
