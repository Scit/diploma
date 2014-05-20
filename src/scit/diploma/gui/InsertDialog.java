package scit.diploma.gui;

import scit.diploma.utils.NameTypePair;

import javax.swing.*;

/**
 * Created by scit on 5/20/14.
 */
public class InsertDialog extends JDialog {
    InsertDialog(NameTypePair[] metadata) {
        for(NameTypePair mdata : metadata) {
            JPanel p = new JPanel();
            JLabel l = new JLabel(mdata.getName());
            JTextField t = new JTextField();
            p.add(l);
            p.add(t);
            add(p);
        }
    }
}
