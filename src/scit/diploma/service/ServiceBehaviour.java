package scit.diploma.service;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import scit.diploma.data.AgentDataContainer;
import scit.diploma.db.DBWorker;

import java.io.IOException;
import java.security.acl.Acl;

/**
 * Created by scit on 5/1/14.
 */
public class ServiceBehaviour extends CyclicBehaviour {
    DBWorker dbw;

    public ServiceBehaviour(Agent agent) {
        super(agent);

        dbw = new DBWorker();
    }

    public void action() {
        ACLMessage msg = myAgent.receive();
        if (msg != null) {
            switch (msg.getPerformative()) {
                case ACLMessage.REQUEST:
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    AgentDataContainer agentDataContainer = null;

                    // process request
                    try {
                        agentDataContainer = (AgentDataContainer) msg.getContentObject();
                        System.out.print(agentDataContainer);
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }

                    agentDataContainer = dbw.execute(agentDataContainer);

                    // process response
                    try {
                        reply.setContentObject(agentDataContainer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    myAgent.send(reply);
                    break;
                case ACLMessage.CANCEL:
                    myAgent.doDelete();
                    break;
            }
        } else {
            block();
        }
    }
}
