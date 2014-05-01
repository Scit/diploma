package scit.diploma.client;

import com.sun.org.apache.xml.internal.serializer.utils.SystemIDResolver;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

import java.util.Vector;

/**
 * Created by scit on 5/1/14.
 */
public class WaitingServiceBehaviour extends TickerBehaviour {
    public WaitingServiceBehaviour(Agent agent, long period) {
        super(agent, period);
    }

    @Override
    protected void onTick() {
        Vector<AID> services = ((ClientAgent) myAgent).searchServices();
        if (services.capacity() > 0) {
            stop();
        }
    }
}
