package gui;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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
		List<DestinationCard> playerDestCards = player.getDestinations();
		for (DestinationCard destinationCard : playerDestCards) {
			getModel()
					.addRow(destinationToTableRow(destinationCard.getRoute()));
		}
		repaint();
		revalidate();
	}

}
