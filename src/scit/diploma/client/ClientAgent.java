package scit.diploma.client;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import scit.diploma.data.Container;
import scit.diploma.data.QueryMaker;

import static scit.diploma.service.ServiceAgent.SERVICE_TYPE;

import java.util.Vector;

/**
 * Created by scit on 5/1/14.
 */
public class ClientAgent extends Agent {
    protected void setup() {
        AID aid = searchServices().firstElement();
        //String request = QueryMaker.selectTableContent("users");
        String request = "";

        addBehaviour(new ClientBehaviour(this, aid, request));
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
