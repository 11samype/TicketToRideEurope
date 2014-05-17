package gui.panels;

import gui.RouteCompletedCellRenderer;
import gui.interfaces.IRefreshable;
import gui.listeners.LocaleChangeListener;
import gui.listeners.PlayerUpdater;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import objects.DestinationCard;
import objects.DestinationRoute;
import objects.interfaces.IPlayer;
import utils.MessageHelper;

public class DestinationTable extends JTable implements PlayerUpdater, LocaleChangeListener, IRefreshable {

//	private TableModel dataModel;
	private List<DestinationRoute> routes = new ArrayList<DestinationRoute>();

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
		this.routes.add(destRoute);
		this.getModel().addRow(destRoute.toLocalizedArray());
	}

	public DestinationRoute getRouteInRow(int row) {
//		Destination start, end;
//		int score = 0;
//		String startName = getModel().getValueAt(row, 0).toString();
//		String endName = getModel().getValueAt(row, 1).toString();
//		start = new Destination(MessageHelper.getDefaultCityNameFor(startName));
//		end = new Destination(MessageHelper.getDefaultCityNameFor(endName));
//		score = Integer.parseInt(getModel().getValueAt(row, 2).toString());
//
//		return new DestinationRoute(start, end, score);
		return this.routes.get(row);
	}

	@Override
	public DefaultTableModel getModel() {
		return (DefaultTableModel) super.getModel();
	}

	private void reset() {
		this.routes.clear();
		setModel(new DefaultTableModel(getColumnNames(), 0));
		refresh();
	}

	@Override
	public void setPlayer(IPlayer player) {
		this.reset();
		
		List<DestinationCard> playerDestCards = player.getDestinationHand();
		for (DestinationCard destinationCard : playerDestCards) {
			DestinationRoute route = destinationCard.getRoute();
			this.routes.add(route);
			getModel().addRow(route.toLocalizedArray());
			
			for (int i = 0; i < getModel().getColumnCount(); i++) {
				TableColumn column = getColumnModel().getColumn(i);
				column.setCellRenderer(new RouteCompletedCellRenderer(player));
			}
		}
		refresh();
	}

	@Override
	public void refresh() {
		repaint();
		revalidate();
	}

	@Override
	public void notifyLocaleChange() {
		getModel().setColumnIdentifiers(getColumnNames());
	}
}
