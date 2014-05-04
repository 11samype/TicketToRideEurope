package gui;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import objects.Destination;
import objects.DestinationCard;
import objects.DestinationRoute;
import objects.interfaces.IPlayer;

public class DestinationTable extends JTable {

	private TableModel dataModel;

	private static final Object[] columnNames = { "Destination Start",
			"Destination End", "Points" };

	public DestinationTable() {
		super();
		reset();
		getTableHeader().setReorderingAllowed(false);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public void addDestination(DestinationRoute destRoute) {
		this.getModel().addRow(destinationToTableRow(destRoute));
	}

	private Object[] destinationToTableRow(DestinationRoute destRoute) {
		return new Object[] { destRoute.getStart(), destRoute.getEnd(),
				destRoute.getScore() };
	}

	public DestinationRoute getRouteInRow(int row) {
		Destination start, end;
		int score = 0;
		start = new Destination(getModel().getValueAt(row, 0).toString());
		end = new Destination(getModel().getValueAt(row, 1).toString());
		score = Integer.parseInt(getModel().getValueAt(row, 2).toString());
		return new DestinationRoute(start, end, score);
	}

	@Override
	public DefaultTableModel getModel() {
		return (DefaultTableModel) this.dataModel;
	}

	@Override
	public void setModel(TableModel dataModel) {
		this.dataModel = dataModel;
		super.setModel(dataModel);
	}

	private void reset() {
		setModel(new DefaultTableModel(columnNames, 0));
		repaint();
		revalidate();
	}

	public void setPlayer(IPlayer player) {
		this.reset();
		List<DestinationCard> playerDestCards = player.getDestinationHand();
		for (DestinationCard destinationCard : playerDestCards) {
			DestinationRoute route = destinationCard.getRoute();
			getModel().addRow(destinationToTableRow(route));
			for (int i = 0; i < getModel().getColumnCount(); i++) {
				TableColumn column = getColumnModel().getColumn(i);
				column.setCellRenderer(new RouteCompletedCellRenderer(player));
			}
		}
		repaint();
		revalidate();
	}
}
