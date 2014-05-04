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
	}

	@Override
	public Component getTableCellRendererComponent(JTable table,
			Object obj, boolean isSelected, boolean hasFocus, int row,
			int column) {
		DestinationTable destTable = (DestinationTable) table;
		final Component cell = super.getTableCellRendererComponent(destTable, obj, isSelected, hasFocus, row, column);
		DestinationRoute routeInTable = destTable.getRouteInRow(row);

		boolean inCorrectRow = route.equals(routeInTable);

		if (inCorrectRow && GraphHelper.hasPlayerCompletedDestinationRoute(player, route)) {
			cell.setBackground(Color.green);
			System.out.println("Coloring row: " + row);
			System.out.println("[Route] " + route);
		}

		return cell;
	}
}