package org.usfirst.frc3467;

import org.usfirst.frc3467.commands.CommandBase;

import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class CommandReadyForMatch implements NamedSendable {
	// not actually a command

	public String getSmartDashboardType() {
		return "Command";
	}

	private ITableListener listener = new ITableListener() {
		public void valueChanged(ITable table, String key, Object value,
				boolean isNew) {
			if (((Boolean) value).booleanValue()) {
				FRCNetworkCommunicationsLibrary.FRCNetworkCommunicationObserveUserProgramStarting();
			}
		}
	};
	private ITable table;

	public void initTable(ITable table) {
		if (this.table != null)
			this.table.removeTableListener(listener);
		this.table = table;
		if (table != null) {
			table.putString("name", getName());
			table.putBoolean("running", false);
			table.putBoolean("isParented", false);
			table.addTableListener("running", listener, false);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public ITable getTable() {
		return table;
	}

	@Override
	public String getName() {
		return "Ready For Match";
	}

}