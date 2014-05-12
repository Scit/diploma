package scit.diploma.search;


import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import scit.diploma.ctrl.ContainersManager;
import scit.diploma.utils.ConditionalVariable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by scit on 5/12/14.
 */
public class ContainersSearchAgent extends Agent {
    protected void setup() {
        setEnabledO2ACommunication(true, 0);
        Object[] args = getArguments();
        if(args.length > 0) {
            ConditionalVariable startUpLatch = (ConditionalVariable) args[0];
            startUpLatch.signal();
        }

        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                String s = (String) myAgent.getO2AObject();
                if(s != null) {
                } else {
                    block();
                }
            }
        });

        addBehaviour(new AMSListenerBehaviour());
    }
}
