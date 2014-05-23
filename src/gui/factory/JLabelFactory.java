package gui.factory;

import gui.DeckSizeLabel;

import java.awt.Font;

import javax.swing.JLabel;

import objects.interfaces.IDeck;

public class JLabelFactory {

	public static JLabel createJLabel(String str) {
		JLabel lbl = new JLabel(str);
		setFont(lbl, "Tahoma", Font.PLAIN, 16);
		return lbl;
	}

	public static DeckSizeLabel createDeckSizeLabel(IDeck<?> deck) {
		DeckSizeLabel lbl = new DeckSizeLabel(deck);
		setFont(lbl, "Tahoma", Font.PLAIN, 24);
		return lbl;
	}
	
	public static void setFont(JLabel lbl, String fontName, int fonttype, int size) {
		lbl.setFont(new Font(fontName, fonttype, size));
	}

}
