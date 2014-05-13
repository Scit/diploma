package scit.diploma.ctrl;

import jade.content.AgentAction;
import jade.core.AID;
import jade.core.ContainerID;
import jade.core.event.AgentListener;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import scit.diploma.data.AgentDataContainer;
import scit.diploma.utils.AgentEvents;
import scit.diploma.utils.PrefixGenerator;

/**
 * Created by scit on 5/12/14.
 */
public class Container implements AgentEvents {
    private AgentContainer client;
    private AID service;
    private ContainerID containerID;

    public Container(AgentContainer client, AID service, ContainerID containerID) {
        this.client = client;
        this.service = service;
        this.containerID = containerID;
    }

    public Container(ContainerID containerID) {
        this.containerID = containerID;
    }

    public boolean isActive() {
        if (service == null) {
            return false;
        } else {
            return true;
        }
    }

    public void doActivate() throws StaleProxyException {
        ContainerController cc = ContainersManager.getProjectContainerController();

        String serviceName = PrefixGenerator.getUniquePrefix() + "-serviceAgent";
        AgentController ac = cc.createNewAgent(serviceName, "scit.diploma.service.ServiceAgent", null);
        System.out.println(containerID);
        System.out.println(ac);
        ac.start();
        ac.move(containerID);
    }

    public void doExecute(AgentDataContainer agentDataContainer) {

    }

    public void doHandle(AgentDataContainer agentDataContainer) {

    }

    @Override
    public void onEvent(AID aid, int type) {

    }
}
