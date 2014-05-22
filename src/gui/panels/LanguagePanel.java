package gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JButton;

import utils.MessageHelper;

public class LanguagePanel extends JPanel{
	public LanguagePanel() {
		setLayout(new MigLayout("", "[][][][][]", "[]"));
		
		JLabel lblLanguage = new JLabel("menu.locale.title");
		add(lblLanguage, "cell 0 0");
//
//		JRadioButton rdbtnEnglish = new JRadioButton("English");
//		rdbtnEnglish.setSelected(true);
//		add(rdbtnEnglish, "cell 1 0");
//
//		JRadioButton rdbtnFrancais = new JRadioButton("Francais");
//		add(rdbtnFrancais, "cell 2 0");
//
//		JRadioButton rdbtnDeutsch = new JRadioButton("Deutsch");
//		add(rdbtnDeutsch, "cell 3 0");
		
		ButtonGroup radioGroup = new ButtonGroup();
		Locale save = MessageHelper.getCurrentLocale();
		int i = 0;
		for (Locale	loc : MessageHelper.AVAILABLE_LOCALES) {
			MessageHelper.setLocale(loc);
			JRadioButton button = new JRadioButton(MessageHelper.getStringFromBundle(MessageHelper.getMessages(), "language.name"));
			radioGroup.add(button);
			add(button, "cell " + (i++) + " 0");
		}
		MessageHelper.setLocale(save);
		
		JButton btnOk = new JButton("OK");
		add(btnOk, "cell 4 0");
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}

}
