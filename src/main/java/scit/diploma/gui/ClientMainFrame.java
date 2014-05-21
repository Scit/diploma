package scit.diploma.gui;

import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import scit.diploma.ctrl.ContainerHolder;
import scit.diploma.ctrl.ContainerHoldersManager;
import scit.diploma.data.AgentDataContainer;
import scit.diploma.data.QueryMaker;
import scit.diploma.utils.CHMListener;
import scit.diploma.utils.NameTypePair;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by scit on 5/10/14.
 */
public class ClientMainFrame extends JFrame implements CHMListener, TableModelListener, ListSelectionListener, ActionListener {
    private static ClientTable table = null;
    private static JButton back = null;
    private static JButton insert = null;
    private static ContainerHoldersList containersList = null;
    private static ContainerController containerController = null;

    private static String currentDataType = null;
    private static ContainerHolder currentContainerHolder = null;
    private static String currentTableName = null;
    private static AgentDataContainer currentAgentDataContainer = null;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientMainFrame();
            }
        });
    }

    public void initGui() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        table = new ClientTable();
        table.getSelectionModel().addListSelectionListener(this);
        containersList = new ContainerHoldersList();
        containersList.addListSelectionListener(this);
        containersList.addContainer(null);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridy = 0;
        add(containersList, c);

        add(new JSeparator(JSeparator.VERTICAL));

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 4.0;
        c.weighty = 1.0;
        c.gridy = 0;
        add(new JScrollPane(table), c);

        back = new JButton("Назад");
        back.addActionListener(this);
        insert = new JButton("Вставить");
        insert.addActionListener(this);

        JPanel bottomBar = new JPanel();
        bottomBar.setBackground(Color.DARK_GRAY);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        bottomBar.add(back, c);
        bottomBar.add(insert, c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 1;

        add(bottomBar, c);


        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = ((int) tk.getScreenSize().getWidth());
        int ySize = ((int) tk.getScreenSize().getHeight());
        setSize(xSize, ySize);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    public ClientMainFrame() {
        initGui();

        ContainerHoldersManager.addListener(this);
        ContainerHoldersManager.init();
    }

    public void onEvent(ContainerHolder containerHolder, int event, AgentDataContainer data) {
        System.out.println("onEVent: " + containerHolder.getName() + "; " + event);
        switch(event) {
            case CHMListener.EVENT_ADDED:
                containersList.addContainer(containerHolder);
                break;
            case CHMListener.EVENT_READY:
                containerHolder.doExecute(QueryMaker.selectTables());
                break;
            case CHMListener.EVENT_ON_DATA:
                if( ! data.getParam(AgentDataContainer.KEY_DATA_TYPE).equals(AgentDataContainer.VALUE_DATA_TYPE_EMPTY)) {
                    // not insert/update operation
                    table.fillTable(data);
                    currentDataType = data.getParam(AgentDataContainer.KEY_DATA_TYPE);
                    currentAgentDataContainer = data;
                }
                break;
        }
    }

    @Override
    public void tableChanged(TableModelEvent event) {
        System.out.println("VALUE: " + table.getValueAt(event.getFirstRow(), event.getColumn()));
        System.out.println(event.getType());
        currentContainerHolder.doExecute(QueryMaker.updateData(table.getCleanRowAt(event.getFirstRow()), currentAgentDataContainer));
    }

    @Override
    public void valueChanged(ListSelectionEvent event) {
        boolean adjust = event.getValueIsAdjusting();
        if(adjust) {
            return;
        }

        Object source = event.getSource();
        if (source == containersList) {
            ContainerHoldersList list = (ContainerHoldersList) event.getSource();
            int[] selections = list.getSelectedIndices();
            for(int selection : selections) {
                // activate
                currentContainerHolder = (ContainerHolder) list.getModel().getElementAt(selection);
                try {
                    currentContainerHolder.doActivate();
                } catch (ControllerException e) {
                    e.printStackTrace();
                }
            }
        } else if (source == table.getSelectionModel()) {
            System.out.println(currentDataType + ": " + adjust);
            if(currentDataType.equals(AgentDataContainer.VALUE_DATA_TYPE_TABLES)) {
                if(table.getSelectedRow() >= 0) {
                    String tableName = (String) table.getValueAt(table.getSelectedRow(), ClientTable.TABLE_NAME_COLUMN_INDEX);
                    currentTableName = tableName;
                    currentContainerHolder.doExecute(QueryMaker.selectTableContent(tableName));
                }
            } else if(currentDataType.equals(AgentDataContainer.VALUE_DATA_TYPE_CONTENT)) {
                table.getModel().removeTableModelListener(this);
                table.getModel().addTableModelListener(this);
            } else if(currentDataType.equals(AgentDataContainer.VALUE_DATA_TYPE_EMPTY)) {

            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if(source == back) {
            currentContainerHolder.doExecute(QueryMaker.selectTables());
        } else if (source == insert) {
            NameTypePair[] ntps = new NameTypePair[3];
            for(int i=0; i < ntps.length; i++) {
                ntps[i] = new NameTypePair();
                ntps[i].setName("Name " + i);
                ntps[i].setType(i);
            }

            //System.out.println("Insert: " + currentAgentDataContainer.getMetadata().length);
            //InsertDialog insertDialog = new InsertDialog(currentAgentDataContainer.getMetadata());
            InsertDialog insertDialog = new InsertDialog(ntps);
            insertDialog.setVisible(true);
        }
    }
}
