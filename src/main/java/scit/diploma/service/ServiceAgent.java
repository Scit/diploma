package scit.diploma.service;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ContainerID;
import jade.lang.acl.ACLMessage;
import scit.diploma.ctrl.ContainerHoldersManager;
import scit.diploma.ctrl.ControllerAgent;
import scit.diploma.utils.AgentInterface;
import scit.diploma.utils.ConditionalVariable;

import java.io.IOException;

/**
 * Created by scit on 5/1/14.
 */
public class ServiceAgent extends Agent {
    public static final String SERVICE_TYPE = "JADE_DATABASE_ACCESS";

    public AgentInterface agentInterface = null;

    protected void setup() {
        setEnabledO2ACommunication(true, 0);
        Object[] args = getArguments();
        ContainerID dest = null;

        switch(args.length) {
            case 2:
                dest = (ContainerID) args[1];
            case 1:
                ConditionalVariable startUpLatch = (ConditionalVariable) args[0];
                startUpLatch.signal();
        }

        doMove(dest);
    }

    protected void beforeMove() {
        System.out.println("Before move: " + here());
    }

    protected void afterMove() {
        addBehaviour(new ServiceBehaviour(this));

        System.out.println("After move: " + here());
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setConversationId(ControllerAgent.CONTROLLER_AGENT_CONVERSATION_ID);
        AID dest = new AID(ContainerHoldersManager.CONTROLLER_AGENT_NAME, AID.ISLOCALNAME);
        msg.setContent(getName());
        try {
            msg.setContentObject(here());
        } catch (IOException e) {
            e.printStackTrace();
        }
        msg.addReceiver(dest);
        send(msg);
        //System.out.println(agentInterface);
        //((AgentEvents) agentInterface).onEvent(aid, AgentEvents.EVENT_SERVICE_AFTER_MOVE);

    }
}
