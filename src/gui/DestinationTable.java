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
		this.dataModel = getDefaultModel();
		this.setModel(dataModel);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public void addDestination(DestinationRoute destRoute) {
		DefaultTableModel model = (DefaultTableModel) this.getModel();
		model.addRow(destinationToTableRow(destRoute));
	}

	private Object[] destinationToTableRow(DestinationRoute destRoute) {
		return new Object[] { destRoute.getStart(), destRoute.getEnd(),
				destRoute.getScore() };
	}

	private DefaultTableModel getDefaultModel() {
		return new DefaultTableModel(columnNames, 0);
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
		setModel(getDefaultModel());
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
