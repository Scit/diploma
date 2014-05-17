package scit.diploma.utils;

import scit.diploma.ctrl.ContainerHolder;
import scit.diploma.data.AgentDataContainer;

/**
 * Created by scit on 5/16/14.
 */
public interface CHMListener {
    public static final int EVENT_ADDED = 0;
    public static final int EVENT_READY = 1;
    public static final int EVENT_ON_DATA = 2;

    public void onEvent(ContainerHolder containerHolder, int Event, AgentDataContainer data);
}
