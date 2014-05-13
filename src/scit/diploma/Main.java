package scit.diploma;

import jade.wrapper.StaleProxyException;
import scit.diploma.ctrl.Container;
import scit.diploma.ctrl.ContainersManager;
import scit.diploma.data.AgentDataContainer;
import scit.diploma.data.QueryMaker;
import scit.diploma.db.DBWorker;

import java.io.IOException;
import java.util.List;

/**
 * Created by scit on 5/2/14.
 */
public class Main {
    public static void main(String[] args) {
        ContainersManager.init();
        try {
            System.in.read(new byte[5]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            List<Container> c = ContainersManager.getContainersList();
            c.get(2).doActivate();
            System.out.println(c.size());
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }

    }
}
