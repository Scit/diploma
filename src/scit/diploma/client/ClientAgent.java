package scit.diploma.client;

import jade.core.Agent;

/**
 * Created by scit on 5/1/14.
 */
public class ClientAgent extends Agent {
    protected void setup() {
        addBehaviour(new ClientBehaviour(this));
    }
}
