package scit.diploma.gui;

import jade.core.ContainerID;
import jade.wrapper.ContainerController;
import scit.diploma.ctrl.ContainerHolder;
import scit.diploma.ctrl.ContainerHoldersManager;
import scit.diploma.data.AgentDataContainer;
import scit.diploma.utils.CHMListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created by scit on 5/10/14.
 */
public class ClientMainFrame extends JFrame implements CHMListener {
    private static ClientTable table = null;
    private static ContainerHoldersList containersList = null;

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
        containersList = new ContainerHoldersList();
        containersList.addContainer(null);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(containersList, c);

        add(new JSeparator(JSeparator.VERTICAL));

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 4.0;
        c.weighty = 1.0;
        add(table, c);

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
                break;
            case CHMListener.EVENT_ON_DATA:
                break;
        }
    }
}
