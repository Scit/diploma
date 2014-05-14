package scit.diploma.ctrl;

import jade.core.ContainerID;
import jade.core.Profile;
import jade.core.Runtime;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import scit.diploma.utils.ConditionalVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by scit on 5/12/14.
 */
public final class ContainersManager {
    private static ContainerController containerController;
    private static AgentController ac;

    private static HashMap<ContainerID, Container> containers;

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
            createSearchAgent();
        }
        if(containers == null) {
            containers = new HashMap<ContainerID, Container>();
        }
    }

    public static void onSearchAgentResponse(ContainerID containerID) {
        System.out.println(containerID.getName() + " - " + containerID.getMain());
        if(containers.containsKey(containerID) == false) {
            containers.put(containerID, new Container(containerID));
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

    private static void createSearchAgent() {
        try {
            AgentController ac = containerController.createNewAgent("searchAgent", "scit.diploma.search.ContainersSearchAgent", null);
            ContainersManager.ac = ac;
            ac.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }


}
