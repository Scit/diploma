package scit.diploma.service;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import scit.diploma.utils.AgentData;
import scit.diploma.utils.AgentEvents;
import scit.diploma.utils.AgentInterface;
import scit.diploma.utils.ConditionalVariable;

/**
 * Created by scit on 5/1/14.
 */
public class ServiceAgent extends Agent {
    public static final String SERVICE_TYPE = "JADE_DATABASE_ACCESS";

    public AgentInterface agentInterface = null;

    protected void setup() {
        setEnabledO2ACommunication(true, 0);
        Object[] args = getArguments();

        switch(args.length) {
            case 2:
                agentInterface = (AgentInterface) args[1];
            case 1:
                ConditionalVariable startUpLatch = (ConditionalVariable) args[0];
                startUpLatch.signal();
        }
    }

    protected void beforeMove() {
        System.out.println("before " + here());
    }

    protected void afterMove() {
        System.out.println("after " + here());
        AID aid = new AID(this.getName(), AID.ISLOCALNAME);
        ((AgentEvents) agentInterface).onEvent(aid, AgentEvents.EVENT_SERVICE_AFTER_MOVE);
    }
}
