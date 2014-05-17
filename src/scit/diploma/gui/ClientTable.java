package scit.diploma.gui;

import scit.diploma.data.AgentDataContainer;
import scit.diploma.utils.NameTypePair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by scit on 5/10/14.
 */
public class ClientTable extends JTable {
    public static final int TABLE_NAME_COLUMN_INDEX = 2;

    public ClientTable() {
        super();
    }

    public ClientTable(AgentDataContainer agentDataContainer) {
        super();

        fillTable(agentDataContainer);
    }

    public void fillTable(AgentDataContainer agentDataContainer) {
        DefaultTableModel tableModel = new DefaultTableModel();

        for(NameTypePair mdata : agentDataContainer.getMetadata()) {
            tableModel.addColumn(mdata.getName());
        }

        for(Object[] dataRow : agentDataContainer.getData()) {
            tableModel.addRow(dataRow);
        }

        this.setModel(tableModel);
    }
}
