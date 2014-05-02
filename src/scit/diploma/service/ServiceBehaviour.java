package scit.diploma.service;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import scit.diploma.utils.SerializableStorage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

            SerializableStorage serializableStorage = dbw.execute(request);
            reply.setContent(request +  " - OK");

            try {
                reply.setContentObject(serializableStorage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            myAgent.send(reply);
        } else {
            block();
        }
    }
}
