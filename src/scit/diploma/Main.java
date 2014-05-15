package scit.diploma;

import jade.wrapper.ControllerException;
import scit.diploma.ctrl.ContainerHolder;
import scit.diploma.ctrl.ContainerHoldersManager;
import scit.diploma.data.QueryMaker;

import java.io.*;
import java.util.List;

/**
 * Created by scit on 5/2/14.
 */
public class Main {
    public static void main(String[] args) {
        ContainerHoldersManager.init();
        try {
            System.in.read(new byte[5]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            List<ContainerHolder> c = ContainerHoldersManager.getContainersList();
            c.get(1).doActivate();
            System.in.read(new byte[5]);
            c.get(1).doExecute(QueryMaker.selectTables());
        } catch (ControllerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
