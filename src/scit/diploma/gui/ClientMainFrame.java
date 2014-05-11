package scit.diploma.gui;

import com.sun.javafx.scene.layout.region.BorderImage;
import jade.core.*;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import scit.diploma.data.*;
import scit.diploma.data.Container;
import scit.diploma.db.DBWorker;
import scit.diploma.utils.ConditionalVariable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by scit on 5/10/14.
 */
public class ClientMainFrame extends JFrame implements ActionListener {
    private static ClientTable table = null;

    private static ContainerController containerController = null;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                drawGui();

                createMainContainer("192.168.1.4", "1099");
                createClientAgent();

            }
        });
    }

    public static void drawGui() {
        ClientMainFrame mainFrame = new ClientMainFrame();
        mainFrame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        table = new ClientTable();

        JPanel servicesPanel = new JPanel();
        servicesPanel.setBackground(Color.BLUE);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        mainFrame.add(servicesPanel, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 3.0;
        c.weighty = 1.0;
        mainFrame.add(table, c);


        mainFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        GuiEvent guiEvent = new GuiEvent(this, 0);
    }

    private static void createMainContainer(String host, String port) {
        Runtime runtime = Runtime.instance();
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, host);
        p.setParameter(Profile.MAIN_PORT, port);
        ContainerController cc = runtime.createAgentContainer(p);

        containerController = cc;
    }

    public static AgentController createClientAgent() {
        try {
            ConditionalVariable startUpLatch = new ConditionalVariable();
            Method m = ClientMainFrame.class.getMethod("onContainerFromAgent", Object.class);
            m.invoke(null, "s");
            AgentController ac = containerController.createNewAgent("newClientAgent", "scit.diploma.client.ClientAgent", new Object[] {startUpLatch, m});

            ac.start();
            startUpLatch.waitOn();

            ac.putO2AObject("String", AgentController.ASYNC);
        } catch (StaleProxyException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ClientMainFrame() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = ((int) tk.getScreenSize().getWidth());
        int ySize = ((int) tk.getScreenSize().getHeight());
        setSize(xSize, ySize);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void onContainerFromAgent(Object o) {
        System.out.println(o.toString());
    }
}
