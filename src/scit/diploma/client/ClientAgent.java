package scit.diploma.client;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import scit.diploma.ctrl.Container;
import scit.diploma.data.AgentDataContainer;
import scit.diploma.utils.AgentData;
import scit.diploma.utils.AgentInterface;
import scit.diploma.utils.ConditionalVariable;

import static scit.diploma.service.ServiceAgent.SERVICE_TYPE;

import java.lang.reflect.Method;
import java.util.Vector;

/**
 * Created by scit on 5/1/14.
 */
public class ClientAgent extends Agent {
    private AgentInterface agentInterface = null;
    private AID serviceAID = null;

    protected void setup() {
        setEnabledO2ACommunication(true, 0);
        Object[] args = getArguments();

        switch(args.length) {
            case 3:
                serviceAID = (AID) args[2];
            case 2:
                agentInterface = (AgentInterface) args[1];
            case 1:
                ConditionalVariable startUpLatch = (ConditionalVariable) args[0];
                startUpLatch.signal();
        }

        addBehaviour(new O2ABehaviour());
    }

    public AID getServiceAID() {
        return serviceAID;
    }

    public Vector<AID> searchServices() {
        Vector<AID> services = new Vector<AID>();

        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();

        sd.setType(SERVICE_TYPE);
        template.addServices(sd);

        try {
            DFAgentDescription[] results = DFService.search(this, template);
            for(DFAgentDescription result : results) {
                services.add(result.getName());
            }

        } catch (FIPAException e) {
            e.printStackTrace();
        }

        return services;
    }

    public AgentInterface getAgentInterface() {
        return agentInterface;
    }
}
