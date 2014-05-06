package gui;

import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import objects.Destination;
import objects.DestinationCard;
import objects.DestinationRoute;
import objects.interfaces.IPlayer;
import utils.MessageHelper;

public class DestinationTable extends JTable implements PlayerInfoListener {

	private TableModel dataModel;

	private static final Object[] columnNames = getColumnNames();

	public DestinationTable() {
		super();
		reset();
		getTableHeader().setReorderingAllowed(false);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public static Object[] getColumnNames() {
		ResourceBundle messages = MessageHelper.getMessages();
		String destStart = MessageHelper.getStringFromBundle(messages, "dest.start");
		String destEnd = MessageHelper.getStringFromBundle(messages, "dest.end");
		String destScore = MessageHelper.getStringFromBundle(messages, "dest.score");
		return new Object[] { destStart, destEnd, destScore };
	}

	public void addDestination(DestinationRoute destRoute) {
		this.getModel().addRow(destinationToTableRow(destRoute));
	}

	private Object[] destinationToTableRow(DestinationRoute destRoute) {
		ResourceBundle cities = MessageHelper.getCityNames();
		String startName = MessageHelper.getStringFromBundle(cities, destRoute.getStart().getName());
		String endName = MessageHelper.getStringFromBundle(cities, destRoute.getEnd().getName());
		return new Object[] { startName, endName, destRoute.getScore() };
	}

	public DestinationRoute getRouteInRow(int row) {
		Destination start, end;
		int score = 0;
		String startName = getModel().getValueAt(row, 0).toString();
		String endName = getModel().getValueAt(row, 1).toString();
		start = new Destination(MessageHelper.getDefaultCityNameFor(startName));
		end = new Destination(MessageHelper.getDefaultCityNameFor(endName));
		score = Integer.parseInt(getModel().getValueAt(row, 2).toString());

		DestinationRoute route = new DestinationRoute(start, end, score);

		return route;
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
		setModel(new DefaultTableModel(getColumnNames(), 0));
		repaint();
		revalidate();
	}

	@Override
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
