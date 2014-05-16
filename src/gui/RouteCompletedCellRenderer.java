package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import objects.DestinationRoute;
import objects.interfaces.IPlayer;
import utils.GraphHelper;

public class RouteCompletedCellRenderer extends DefaultTableCellRenderer {
	private IPlayer player;

	public RouteCompletedCellRenderer(IPlayer player) {
		this.player = player;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table,
			Object obj, boolean isSelected, boolean hasFocus, int row,
			int column) {
		DestinationTable destTable = (DestinationTable) table;
		final Component cell = super.getTableCellRendererComponent(destTable, obj, isSelected, hasFocus, row, column);
		DestinationRoute routeInTable = destTable.getRouteInRow(row);


		if (GraphHelper.hasPlayerCompletedDestinationRoute(player, routeInTable)) {
			cell.setBackground(Color.green);
		} else {
			cell.setBackground(Color.white);
		}

		return cell;
	}
}