package scit.diploma.ctrl;


import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

/**
 * Created by scit on 5/12/14.
 */
public class ControllerAgent extends Agent {
    public static final String CONTROLLER_AGENT_CONVERSATION_ID = "CONTROLLER_AGENT_CONVERSATION_ID";

    protected void setup() {
        addBehaviour(new AMSListenerBehaviour());

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.MatchConversationId(CONTROLLER_AGENT_CONVERSATION_ID);
                ACLMessage msg = receive(mt);
                if (msg != null) {
                    try {
                        ContainerID c = (ContainerID) msg.getContentObject();
                        ContainerHoldersManager.onServiceAgentMoved(c, msg.getSender());
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                } else {
                    block();
                }
            }
        });
    }
}