package gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JButton;

public class LanguagePanel extends JPanel{
	public LanguagePanel() {
		setLayout(new MigLayout("", "[][][][][]", "[]"));
		
		JLabel lblLanguage = new JLabel("Language");
		add(lblLanguage, "cell 0 0");
		
		JRadioButton rdbtnEnglish = new JRadioButton("English");
		rdbtnEnglish.setSelected(true);
		add(rdbtnEnglish, "cell 1 0");
		
		JRadioButton rdbtnFrancais = new JRadioButton("Francais");
		add(rdbtnFrancais, "cell 2 0");
		
		JRadioButton rdbtnDeutsch = new JRadioButton("Deutsch");
		add(rdbtnDeutsch, "cell 3 0");
		
		JButton btnOk = new JButton("OK");
		add(btnOk, "cell 4 0");
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				okButtonPressed();
			}
		});
		
		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(rdbtnEnglish);
		radioGroup.add(rdbtnFrancais);
		radioGroup.add(rdbtnDeutsch);
	}

	protected void okButtonPressed() {
		setVisible(false);
		dispose();
		
	}

}
