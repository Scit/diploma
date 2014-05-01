package scit.diploma.client;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Created by scit on 5/1/14.
 */
public class ClientBehaviour extends Behaviour {
    private static final int SENDING = 0;
    private static final int RECEIVING = 1;

    private Agent agent;

    private boolean done = false;
    private int state = SENDING;

    public ClientBehaviour(Agent agent) {
        this.agent = agent;
    }

    public void action() {
        ACLMessage message = null;

        switch (state) {
            case SENDING:
                message = new ACLMessage(ACLMessage.REQUEST);
                message.addReceiver(new AID("serviceAgent", AID.ISLOCALNAME));
                message.setContent("123");
                agent.send(message);

                state++;
                break;
            case RECEIVING:
                message = agent.receive();
                if(message != null) {
                    String content = message.getContent();
                    System.out.println(content);

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
