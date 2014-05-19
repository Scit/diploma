package scit.diploma.gui;

import scit.diploma.data.AgentDataContainer;
import scit.diploma.utils.NameTypePair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by scit on 5/10/14.
 */
public class ClientTable extends JTable {
    public static final int TABLE_NAME_COLUMN_INDEX = 3;

    public ClientTable() {
        super();
    }

    public ClientTable(AgentDataContainer agentDataContainer) {
        super();

        fillTable(agentDataContainer);
    }

    public void fillTable(AgentDataContainer agentDataContainer) {
        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn(AgentDataContainer.KEY_CONTAINER_NAME);
        for(NameTypePair mdata : agentDataContainer.getMetadata()) {
            tableModel.addColumn(mdata.getName());
        }

        for(Object[] dataRow : agentDataContainer.getData()) {
            List<Object> exDataRow = new ArrayList<Object>(Arrays.asList(dataRow));
            exDataRow.add(0, agentDataContainer.getParam(AgentDataContainer.KEY_CONTAINER_NAME));
            tableModel.addRow(exDataRow.toArray());
        }

        this.setModel(tableModel);
    }

    public Object[] getCleanRowAt(int row) {
        int columnCount =  getColumnCount();
        Object[] result = new Object[columnCount - 1]; // we don't use first column

        for(int col=1; col < columnCount; col++) {
            result[col-1] = this.getModel().getValueAt(row, col);
        }

        return result;
    }
}
