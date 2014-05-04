package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import objects.DestinationRoute;
import objects.interfaces.IPlayer;
import utils.GraphHelper;

public class RouteCompletedCellRenderer extends DefaultTableCellRenderer {
	IPlayer player;
	DestinationRoute route;


	public RouteCompletedCellRenderer(IPlayer player, DestinationRoute route) {
		this.player = player;
		this.route = route;
		System.out.println(player.getName());
		System.out.println(route);
	}

	public Component getTableCellRendererComponent(JTable table, Object obj,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
		if (GraphHelper.hasPlayerCompletedDestinationRoute(player, route)) {
			cell.setBackground(Color.green);
		} else {
			System.out.println("nope");
		}
		return cell;
	}
}