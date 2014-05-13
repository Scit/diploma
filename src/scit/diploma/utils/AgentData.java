package scit.diploma.utils;

import jade.core.AID;
import scit.diploma.data.AgentDataContainer;

/**
 * Created by scit on 5/14/14.
 */
public interface AgentData {
    public void onData(AID aid, AgentDataContainer agentDataContainer);
}
