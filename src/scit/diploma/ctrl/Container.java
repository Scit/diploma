package scit.diploma.ctrl;

import jade.core.AID;
import jade.core.ContainerID;
import jade.wrapper.AgentContainer;
import scit.diploma.data.AgentDataContainer;

/**
 * Created by scit on 5/12/14.
 */
public class Container {
    private AgentContainer client;
    private AID service;
    private ContainerID containerID;

    public Container(AgentContainer client, AID service, ContainerID containerID) {
        this.client = client;
        this.service = service;
        this.containerID = containerID;
    }

    public boolean isActive() {
        if (service == null) {
            return false;
        } else {
            return true;
        }
    }

    public void doActivate() {

    }

    public void doExecute(AgentDataContainer agentDataContainer) {

    }

    public void doHandle(AgentDataContainer agentDataContainer) {

    }
}
