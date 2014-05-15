package scit.diploma.client;

import jade.core.behaviours.CyclicBehaviour;
import scit.diploma.data.AgentDataContainer;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by scit on 5/14/14.
 */
public class O2ABehaviour extends CyclicBehaviour {
    public void action() {
        System.out.println("02ABehaviour: " + myAgent.getName());
        AgentDataContainer agentDataContainer = (AgentDataContainer) myAgent.getO2AObject();
        if(agentDataContainer != null) {
            System.out.println("O2A data: " + agentDataContainer.toString());
            myAgent.addBehaviour(new ClientBehaviour(myAgent, agentDataContainer));
        } else {
            block();
        }
    }
}
