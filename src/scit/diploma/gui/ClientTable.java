package scit.diploma.gui;

import scit.diploma.data.Container;
import scit.diploma.utils.NameTypePair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by scit on 5/10/14.
 */
public class ClientTable extends JTable {
    public ClientTable() {
        super();
    }

    public ClientTable(Container container) {
        super();

        fillTable(container);
    }

    public void fillTable(Container container) {
        DefaultTableModel tableModel=new DefaultTableModel();

        for(NameTypePair mdata : container.getMetadata()) {
            tableModel.addColumn(mdata.getName());
        }

        for(Object[] dataRow : container.getData()) {
            tableModel.addRow(dataRow);
        }

        this.setModel(tableModel);
    }
}
