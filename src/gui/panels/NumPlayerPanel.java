package gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;

import utils.GameState;

public class NumPlayerPanel extends JPanel {
	public NumPlayerPanel() {
		setLayout(new MigLayout("", "[][][]", "[]"));
		
		JLabel lblNumberOfPlayers = new JLabel("Number of Players");
		add(lblNumberOfPlayers, "cell 0 0");
		
		final JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 1, 5, 1));
		add(spinner, "cell 1 0");
		
		JButton btnOk = new JButton("OK");
		add(btnOk, "cell 2 0");
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				okButtonPressed(spinner.getValue());
			}
		});
	}

	protected void okButtonPressed(Object object) {
		int val = (int)object;
		
		GameState.numPlayers = val;
		
	}

}
