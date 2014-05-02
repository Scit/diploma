package scit.diploma.client;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

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
    private String request;
    private AID aid;

    public ClientBehaviour(Agent agent, AID aid, String request) {
        super(agent);

        this.aid = aid;
        this.request = request;
    }

    public void action() {
        ACLMessage message = null;

        switch (state) {
            case SENDING:
                message = new ACLMessage(ACLMessage.REQUEST);
                message.addReceiver(aid);
                message.setContent(request);
                myAgent.send(message);

                state++;
                break;
            case RECEIVING:
                message = myAgent.receive();
                if(message != null) {
                    String content = message.getContent();
                    System.out.println(content);

                    List<HashMap<String, Object>> data = null;
                    try {
                        data = (ArrayList<HashMap<String, Object>>) message.getContentObject();
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }

                    ((ClientAgent) myAgent).onData(data);

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
