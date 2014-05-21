package scit.diploma.client;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import scit.diploma.data.AgentDataContainer;
import scit.diploma.utils.AgentData;

import java.io.IOException;

/**
 * Created by scit on 5/1/14.
 */
public class ClientBehaviour extends Behaviour {
    private static final int SENDING = 0;
    private static final int RECEIVING = 1;

    private boolean done = false;
    private int state = SENDING;
    private AgentDataContainer agentDataContainer;
    private AID aid;

    public ClientBehaviour(Agent agent, AgentDataContainer agentDataContainer) {
        super(agent);

        this.aid = ((ClientAgent) myAgent).getServiceAID();
        this.agentDataContainer = agentDataContainer;
    }

    public void action() {
        ACLMessage message = null;

        switch (state) {
            case SENDING:
                message = new ACLMessage(ACLMessage.REQUEST);
                message.addReceiver(aid);

                try {
                    message.setContentObject(agentDataContainer);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                myAgent.send(message);
                state++;
                break;
            case RECEIVING:
                message = myAgent.receive();
                if(message != null) {
                    try {
                        agentDataContainer = (AgentDataContainer) message.getContentObject();
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }

                    AgentData agentData = (AgentData) ((ClientAgent) myAgent).getAgentInterface();
                    AID aid = new AID(myAgent.getName(), AID.ISLOCALNAME);
                    agentData.onData(aid, agentDataContainer);
                    done = true;
                } else {
                    block();
                }

                break;
        }
    }

    public boolean done() {
        return done;
    }
}
