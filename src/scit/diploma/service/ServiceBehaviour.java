package scit.diploma.service;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import scit.diploma.data.Container;
import scit.diploma.db.DBWorker;
import scit.diploma.utils.ObjectIntPair;

import java.io.IOException;

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
        ACLMessage message = myAgent.receive();
        if (message != null) {
            ACLMessage reply = message.createReply();
            reply.setPerformative(ACLMessage.INFORM);
            String request = message.getContent();
            Container container = null;

            // process request
            try {
                container = (Container) message.getContentObject();
            } catch (UnreadableException e) {
                e.printStackTrace();
            }

            container = dbw.execute(container);

            // process response
            try {
                reply.setContentObject(container);
            } catch (IOException e) {
                e.printStackTrace();
            }

            myAgent.send(reply);
        } else {
            block();
        }
    }
}
