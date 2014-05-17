package scit.diploma.ctrl;

import jade.core.AID;
import jade.core.ContainerID;
import jade.wrapper.*;
import scit.diploma.data.AgentDataContainer;
import scit.diploma.utils.AgentData;
import scit.diploma.utils.AgentEvents;
import scit.diploma.utils.ConditionalVariable;
import scit.diploma.utils.PrefixGenerator;

/**
 * Created by scit on 5/12/14.
 */
public class ContainerHolder implements AgentEvents, AgentData {
    private static final String SERVICE_NAME_TEMPLATE = "-serviceAgent";
    private static final String CLIENT_NAME_TEMPLATE = "-clientAgent";

    private AgentController client;
    private AID serviceAID;
    private ContainerID containerID;
    private boolean isActive = false;

/*    public ContainerHolder(AgentController client, AID serviceAID, ContainerID containerID) {
        this.client = client;
        this.serviceAID = serviceAID;
        this.containerID = containerID;
    }*/

    public ContainerHolder(ContainerID containerID) {
        this.containerID = containerID;
    }

    public String getName() {
        return containerID.getName();
    }

    public boolean isActive() {
        return isActive;
    }

    public AID getServiceAID() {
        return this.serviceAID;
    }

    public void doActivate() throws ControllerException {
        if (isActive()) { return; }

        ConditionalVariable startUpLatch = new ConditionalVariable();
        ContainerController cc = ContainerHoldersManager.getProjectContainerController();

        String agentName = PrefixGenerator.getUniquePrefix() + SERVICE_NAME_TEMPLATE;
        AgentController ac = cc.createNewAgent(agentName, "scit.diploma.service.ServiceAgent", new Object[] {startUpLatch, containerID});
        ac.start();
        try {
            startUpLatch.waitOn();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("From: " + cc.getContainerName());
        System.out.println("To: " + containerID.getName());
    }

    public void doExecute(AgentDataContainer agentDataContainer) {
        // push o2a to o2aAgent
        try {
            client.putO2AObject(agentDataContainer, AgentController.ASYNC);
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEvent(AID aid, int type) {
        switch (type) {
            case AgentEvents.EVENT_SERVICE_AFTER_MOVE:
                this.serviceAID = aid;
                ConditionalVariable startUpLatch = new ConditionalVariable();
                ContainerController cc = ContainerHoldersManager.getProjectContainerController();

                String agentName = PrefixGenerator.getUniquePrefix() + CLIENT_NAME_TEMPLATE;
                try {
                    AgentController ac = cc.createNewAgent(agentName, "scit.diploma.client.ClientAgent", new Object[] {startUpLatch, this, serviceAID});
                    ac.start();
                    client = ac;
                    startUpLatch.waitOn();
                } catch (StaleProxyException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case AgentEvents.EVENT_CLIENT_READY:
                isActive = true;
                ContainerHoldersManager.onContainerHolderActive(this);
        }
    }

    @Override
    public void onData(AID aid, AgentDataContainer agentDataContainer) {
        ContainerHoldersManager.onContainerHolderTakeData(this, agentDataContainer);
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
