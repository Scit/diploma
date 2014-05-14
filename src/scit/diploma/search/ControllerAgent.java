package scit.diploma.search;


import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import scit.diploma.ctrl.ContainersManager;
import scit.diploma.utils.ConditionalVariable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by scit on 5/12/14.
 */
public class ControllerAgent extends Agent {
    public static final String CONTROLLER_AGENT_CONVERSATION_ID = "CONTROLLER_AGENT_CONVERSATION_ID";

    protected void setup() {
        System.out.println("Started: " + getName());
        addBehaviour(new AMSListenerBehaviour());

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.MatchConversationId(CONTROLLER_AGENT_CONVERSATION_ID);
                ACLMessage msg = receive(mt);
                if (msg != null) {
                    try {
                        ContainerID c = (ContainerID) msg.getContentObject();
                        ContainersManager.onServiceAgentMoved(c, msg.getSender());
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
