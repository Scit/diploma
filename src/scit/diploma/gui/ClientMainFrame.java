package scit.diploma.gui;

import jade.core.ContainerID;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import scit.diploma.ctrl.ContainerHolder;
import scit.diploma.ctrl.ContainerHoldersManager;
import scit.diploma.data.AgentDataContainer;
import scit.diploma.data.QueryMaker;
import scit.diploma.utils.CHMListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by scit on 5/10/14.
 */
public class ClientMainFrame extends JFrame implements CHMListener, ActionListener, ListSelectionListener {
    private static ClientTable table = null;
    private static ContainerHoldersList containersList = null;
    private static ContainerHolder selectedContainerHolder;

    private static ContainerController containerController = null;

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
        add(containersList, c);

        add(new JSeparator(JSeparator.VERTICAL));

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 4.0;
        c.weighty = 1.0;
        add(new JScrollPane(table), c);

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
                table.fillTable(data);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {

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
                selectedContainerHolder = (ContainerHolder) list.getModel().getElementAt(selection);
                try {
                    selectedContainerHolder.doActivate();
                } catch (ControllerException e) {
                    e.printStackTrace();
                }
            }
        } else if (source == table.getSelectionModel()) {
            //String tableName = (String) table.getValueAt(table.getSelectedRow(), ClientTable.TABLE_NAME_COLUMN_INDEX);
            String tableName = "users";
            selectedContainerHolder.doExecute(QueryMaker.selectTableContent(tableName));
        }
    }
}
