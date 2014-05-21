package scit.diploma.ctrl;

import jade.core.ContainerID;
import jade.domain.introspection.AMSSubscriber;
import jade.domain.introspection.AddedContainer;
import jade.domain.introspection.Event;

import java.util.Map;


/**
 * Created by scit on 5/12/14.
 */
public class AMSListenerBehaviour extends AMSSubscriber {
    public void installHandlers(Map handlersTable) {
        handlersTable.put(AddedContainer.NAME, new AddedContainerHandler());
    }

    public final class AddedContainerHandler implements EventHandler {
        public void handle(Event ev) {
            AddedContainer event = (AddedContainer) ev;
            ContainerID addedContainer = event.getContainer();

            ContainerHoldersManager.onContainerAdded(addedContainer);
        }
    }

}
