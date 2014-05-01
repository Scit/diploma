package scit.diploma.service;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Created by scit on 5/1/14.
 */
public class ServiceBehaviour extends CyclicBehaviour {
    public ServiceBehaviour(Agent agent) {
        super(agent);
    }

    public void action() {
        ACLMessage message = myAgent.receive();
        if (message != null) {
            ACLMessage reply = message.createReply();
            reply.setPerformative(ACLMessage.INFORM);
            reply.setContent(message.getContent() + "321");
            myAgent.send(reply);
        } else {
            block();
        }
    }
}
