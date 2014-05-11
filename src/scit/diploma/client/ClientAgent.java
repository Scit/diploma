package scit.diploma.client;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import scit.diploma.data.Container;
import scit.diploma.data.QueryMaker;
import scit.diploma.utils.ConditionalVariable;

import static scit.diploma.service.ServiceAgent.SERVICE_TYPE;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

/**
 * Created by scit on 5/1/14.
 */
public class ClientAgent extends Agent {
    Method m = null;

    protected void setup() {
        //AID aid = searchServices().firstElement();

        //Container container = QueryMaker.selectTables();

        //addBehaviour(new ClientBehaviour(this, aid, container));
        setEnabledO2ACommunication(true, 0);
        Object[] args = getArguments();
        if(args.length > 0) {
            ConditionalVariable startUpLatch = (ConditionalVariable) args[0];
            startUpLatch.signal();

            m = (Method) args[1];
        }

        System.out.println("Ku");
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                String s = (String) myAgent.getO2AObject();
                if(s != null) {
                    System.out.println(s);

                } else {
                    block();
                }
            }
        });
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

    public void onData(Container container) {
        //TODO
    }
}
