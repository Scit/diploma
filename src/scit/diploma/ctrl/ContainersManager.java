package scit.diploma.ctrl;

import jade.core.ContainerID;
import jade.core.Profile;
import jade.core.Runtime;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import scit.diploma.utils.ConditionalVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by scit on 5/12/14.
 */
public class ContainersManager {
    private static ContainerController containerController;
    private static AgentController ac;

    private static HashMap<ContainerID, Container> containers;

    public static List<Container> getContainersList() throws StaleProxyException {
        init();
        ac.putO2AObject("String", AgentController.ASYNC);

        return null;
    }

    private static void init() {
        if(containerController == null ) {
            createProjectContainer("82.209.80.43", "1099");
        }
        if(ac == null) {
            createSearchAgent();
        }
    }

    public static void onSearchAgentResponse(ContainerID containerID) {
        System.out.println(containerID.getName());
    }

    private static void createProjectContainer(String host, String port) {
        Runtime runtime = Runtime.instance();
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, host);
        p.setParameter(Profile.MAIN_PORT, port);
        ContainerController cc = runtime.createAgentContainer(p);

        containerController = cc;
    }

    private static void createSearchAgent() {
        try {
            ConditionalVariable startUpLatch = new ConditionalVariable();
            AgentController ac = containerController.createNewAgent("searchAgent", "scit.diploma.search.ContainersSearchAgent", new Object[] {startUpLatch});
            ContainersManager.ac = ac;
            ac.start();
            startUpLatch.waitOn();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
