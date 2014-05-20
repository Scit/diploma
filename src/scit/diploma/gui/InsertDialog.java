package scit.diploma.gui;

import scit.diploma.utils.NameTypePair;

import javax.swing.*;
import java.awt.*;

/**
 * Created by scit on 5/20/14.
 */
public class InsertDialog extends JDialog {
    InsertDialog(NameTypePair[] metadata) {
        super();
        setLayout(new FlowLayout());
        setSize(100 * metadata.length, 200);

        for(NameTypePair mdata : metadata) {
            Box bh = Box.createHorizontalBox();
            Box bv = Box.createVerticalBox();
            JLabel l = new JLabel(mdata.getName());
            JTextField t = new JTextField();
            bv.add(l);
            bv.add(t);
            bh.add(bv);
            add(bh);
        }
    }
}
