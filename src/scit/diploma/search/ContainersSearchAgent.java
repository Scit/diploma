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
        addBehaviour(new AMSListenerBehaviour());
    }
}
