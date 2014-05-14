package scit.diploma.ctrl;

import jade.core.AID;
import jade.core.ContainerID;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import scit.diploma.data.AgentDataContainer;
import scit.diploma.utils.AgentData;
import scit.diploma.utils.AgentEvents;
import scit.diploma.utils.ConditionalVariable;
import scit.diploma.utils.PrefixGenerator;

/**
 * Created by scit on 5/12/14.
 */
public class Container implements AgentEvents, AgentData {
    private static final String SERVICE_NAME_TEMPLATE = "-serviceAgent";
    private static final String CLIENT_NAME_TEMPLATE = "-clientAgent";

    private AgentContainer client;
    private AID serviceAID;
    private ContainerID containerID;

    public Container(AgentContainer client, AID serviceAID, ContainerID containerID) {
        this.client = client;
        this.serviceAID = serviceAID;
        this.containerID = containerID;
    }

    public Container(ContainerID containerID) {
        this.containerID = containerID;
    }

    public boolean isActive() {
        if (serviceAID == null) {
            return false;
        } else {
            return true;
        }
    }

    public AID getServiceAID() {
        return this.serviceAID;
    }

    public void doActivate() throws StaleProxyException {
        if (isActive()) { return; }

        ConditionalVariable startUpLatch = new ConditionalVariable();
        ContainerController cc = ContainersManager.getProjectContainerController();

        String agentName = PrefixGenerator.getUniquePrefix() + SERVICE_NAME_TEMPLATE;
        AgentController ac = cc.createNewAgent(agentName, "scit.diploma.serviceAID.ServiceAgent", new Object[] {startUpLatch, this});
        System.out.println(containerID);
        System.out.println(ac);
        ac.start();
        ac.move(containerID);
    }

    public void doExecute(AgentDataContainer agentDataContainer) {
        // push o2a to o2aAgent
    }

    @Override
    public void onEvent(AID aid, int type) {
        switch (type) {
            case AgentEvents.EVENT_SERVICE_AFTER_MOVE:
                this.serviceAID = aid;
                ConditionalVariable startUpLatch = new ConditionalVariable();
                ContainerController cc = ContainersManager.getProjectContainerController();

                String agentName = PrefixGenerator.getUniquePrefix() + SERVICE_NAME_TEMPLATE;
                try {
                    AgentController ac = cc.createNewAgent(agentName, "scit.diploma.client.ClientAgent", new Object[] {startUpLatch, this, serviceAID});
                    ac.start();
                } catch (StaleProxyException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onData(AID aid, AgentDataContainer agentDataContainer) {

    }
}
