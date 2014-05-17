package gui.factory;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import objects.interfaces.IDeck;

public class JPanelFactory {
	public static JPanel createDeckPanel(IDeck<?> deck, Color bg, MouseListener mouseListener) {
		// JPanel panel = new JPanel();
		// panel.setBackground(bg);
		// panel.setLayout(new GridBagLayout());
		// if (mouseListener != null)
		// panel.addMouseListener(mouseListener);
		JPanel panel = createPanel(new GridBagLayout(), bg, mouseListener);
		panel.add(JLabelFactory.createDeckSizeLabel(deck));
		// parent.add(panel, layoutConstraints);
		return panel;
	}

	public static JPanel createPanel(LayoutManager layout, Color bg, MouseListener mouseListener) {
		JPanel panel = new JPanel();
		panel.setBackground(bg);
		panel.setLayout(layout);
		if (mouseListener != null)
			panel.addMouseListener(mouseListener);
		return panel;
	}
}
