package scit.diploma.service;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Created by scit on 5/1/14.
 */
public class ServiceBehaviour extends CyclicBehaviour {
    private Agent agent;

    public ServiceBehaviour(Agent agent) {
        this.agent = agent;
    }

    public void action() {
        ACLMessage message = agent.receive();
        if (message != null) {
            ACLMessage reply = message.createReply();
            reply.setPerformative(ACLMessage.INFORM);
            reply.setContent(message.getContent() + "321");
            agent.send(reply);
        } else {
            block();
        }
    }
}
