package scit.diploma.service;

import jade.core.Agent;

/**
 * Created by scit on 5/1/14.
 */
public class ServiceAgent extends Agent {
    protected void setup() {
        addBehaviour(new ServiceBehaviour(this));
    }
}
