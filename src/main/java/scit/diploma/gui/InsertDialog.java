package scit.diploma.gui;

import net.miginfocom.swing.MigLayout;
import scit.diploma.utils.NameTypePair;

import javax.swing.*;
import java.awt.*;

/**
 * Created by scit on 5/20/14.
 */
public class InsertDialog extends JDialog {
    InsertDialog(NameTypePair[] metadata) {
        super();
        setLocationRelativeTo(null);//Центрируем окно
        setLayout(new BorderLayout());

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new MigLayout(""));

        for (NameTypePair mdata : metadata) {
            JLabel l = new JLabel(mdata.getName());
            JTextField t = new JTextField();
            fieldsPanel.add(l);
            fieldsPanel.add(t, "span, w 150:200:300");
        }
        add(fieldsPanel,BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel();
        JButton insert = new JButton("Вставить");
        buttonsPanel.add(insert);
        JButton insertWithReplication = new JButton("Вставить с реплицированием");
        buttonsPanel.add(insertWithReplication);
        add(buttonsPanel,BorderLayout.PAGE_END);
        pack();
    }
}
