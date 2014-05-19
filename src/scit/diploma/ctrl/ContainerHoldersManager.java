package scit.diploma.ctrl;

import jade.core.*;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import scit.diploma.data.AgentDataContainer;
import scit.diploma.utils.CHMListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by scit on 5/12/14.
 */
public final class ContainerHoldersManager {
    public static final String CONTROLLER_AGENT_NAME = "controllerAgent";
    private static List<CHMListener> listeners = new ArrayList<CHMListener>();
    private static ContainerController containerController;
    private static AgentController ac;

    private static HashMap<String, ContainerHolder> containers;

    public static List<ContainerHolder> getContainersList() throws StaleProxyException {
        if(containers != null) {
            return new ArrayList<ContainerHolder>(containers.values());
        } else {
            return new ArrayList<ContainerHolder>();
        }
    }

    public static void addListener(CHMListener listener) {
        listeners.add(listener);
    }

    public static ContainerController getProjectContainerController() {
        return containerController;
    }

    public static void init() {
        if(containerController == null ) {
            createProjectContainer("192.168.111.104", "1099");
        }
        if(ac == null) {
            createControllerAgent();
        }
        if(containers == null) {
            containers = new HashMap<String, ContainerHolder>();
        }
    }

    public static void onServiceAgentMoved(ContainerID containerID, AID serviceAID) {
        containers.get(containerID.getName()).onEvent(serviceAID, ContainerHolder.EVENT_SERVICE_AFTER_MOVE);
    }

    public static void onContainerAdded(ContainerID containerID) {
        System.out.println(containerID.getName() + " - " + containerID.getMain());
        ContainerHolder containerHolder = new ContainerHolder(containerID);
        containers.put(containerID.getName(), containerHolder);

        System.out.println("*** Container has been added: " + containerHolder.getName() + " ***");
        dispatchEvent(containerHolder, CHMListener.EVENT_ADDED, null);
    }

    public static void onContainerHolderTakeData(ContainerHolder containerHolder, AgentDataContainer data) {
        System.out.println("*** Container has been taken the data: " + containerHolder.getName() + " ***");
        System.out.println("*** Container's data: " + data.toString() + " ***");
        dispatchEvent(containerHolder, CHMListener.EVENT_ON_DATA, data);
    }

    public static void onContainerHolderActive(ContainerHolder containerHolder) {
        System.out.println("*** Container is active: " + containerHolder.getName() + " ***");
        dispatchEvent(containerHolder, CHMListener.EVENT_READY, null);
    }

    private static void dispatchEvent(ContainerHolder containerHolder, int event, AgentDataContainer data) {
        for(CHMListener listener : listeners) {
            listener.onEvent(containerHolder, event, data);
        }
    }

    private static void createProjectContainer(String host, String port) {
        Runtime runtime = Runtime.instance();
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, host);
        p.setParameter(Profile.MAIN_PORT, port);
        ContainerController cc = runtime.createAgentContainer(p);
        runtime.createAgentContainer(new ProfileImpl());

        containerController = cc;
    }

    private static void createControllerAgent() {
        try {
            AgentController ac = containerController.createNewAgent(CONTROLLER_AGENT_NAME, "scit.diploma.ctrl.ControllerAgent", null);
            ContainerHoldersManager.ac = ac;
            ac.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
