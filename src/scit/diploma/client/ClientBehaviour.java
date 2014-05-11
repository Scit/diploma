package scit.diploma.client;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import scit.diploma.data.Container;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by scit on 5/1/14.
 */
public class ClientBehaviour extends Behaviour {
    private static final int SENDING = 0;
    private static final int RECEIVING = 1;

    private boolean done = false;
    private int state = SENDING;
    private Container container;
    private AID aid;

    public ClientBehaviour(Agent agent, AID aid, Container container) {
        super(agent);

        this.aid = aid;
        this.container = container;
    }

    public void action() {
        ACLMessage message = null;

        switch (state) {
            case SENDING:
                message = new ACLMessage(ACLMessage.REQUEST);
                message.addReceiver(aid);

                try {
                    message.setContentObject(container);
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
                        container = (Container) message.getContentObject();
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }

                    ((ClientAgent) myAgent).onData(container);
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
