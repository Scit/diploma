package scit.diploma.gui;

import scit.diploma.ctrl.ContainerHolder;

import javax.swing.*;

/**
 * Created by scit on 5/17/14.
 */
public class ContainerHoldersList extends JList {
    private DefaultListModel<ContainerHolder> model = null;

    public ContainerHoldersList() {
        super();

        model = new DefaultListModel<ContainerHolder>();
        setModel(model);
    }

    public void addContainer(ContainerHolder container) {
        model.addElement(container);
    }
}
