package scit.diploma.ctrl;

import jade.core.*;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by scit on 5/12/14.
 */
public final class ContainersManager {
    public static final String CONTROLLER_AGENT_NAME = "controllerAgent";
    private static ContainerController containerController;
    private static AgentController ac;

    private static HashMap<String, Container> containers;

    public static List<Container> getContainersList() throws StaleProxyException {
        if(containers != null) {
            return new ArrayList<Container>(containers.values());
        } else {
            return new ArrayList<Container>();
        }
    }

    public static ContainerController getProjectContainerController() {
        return containerController;
    }

    public static void init() {
        if(containerController == null ) {
            createProjectContainer("82.209.80.43", "1099");
        }
        if(ac == null) {
            createControllerAgent();
        }
        if(containers == null) {
            containers = new HashMap<String, Container>();
        }
    }

    public static void onSearchAgentResponse(ContainerID containerID) {
        System.out.println(containerID.getName() + " - " + containerID.getMain());
        containers.put(containerID.getName(), new Container(containerID));
    }

    public static void onServiceAgentMoved(ContainerID containerID, AID serviceAID) {
        System.out.println("Moved: " + containerID.getName() + " " + serviceAID.getName());
        containers.get(containerID.getName()).onEvent(serviceAID, Container.EVENT_SERVICE_AFTER_MOVE);
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
            ContainersManager.ac = ac;
            ac.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }


}
