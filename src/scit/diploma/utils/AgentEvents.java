package scit.diploma.utils;

import jade.core.AID;

/**
 * Created by scit on 5/14/14.
 */
public interface AgentEvents extends AgentInterface {
    public final static int EVENT_SERVICE_AFTER_MOVE = 1;
    public final static int EVENT_CLIENT_READY = 2;

    public void onEvent(AID aid, int type);
}
