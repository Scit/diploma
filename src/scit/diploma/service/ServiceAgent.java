package scit.diploma.service;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

/**
 * Created by scit on 5/1/14.
 */
public class ServiceAgent extends Agent {
    public static final String SERVICE_TYPE = "JADE_DATABASE_ACCESS";

    protected void setup() {
        addBehaviour(new ServiceBehaviour(this));

        register();
    }

    protected void takeDown() {
        unregister();
    }

    protected void register() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(SERVICE_TYPE);
        sd.setName(getLocalName() + "-" + SERVICE_TYPE);
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

    }

    protected void unregister() {
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
