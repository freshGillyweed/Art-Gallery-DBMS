package ui;

import delegates.MainWindowDelegate;

import javax.swing.*;
import java.awt.*;

public class ResetDatabase extends JFrame {

    MainWindowDelegate delegate;

    public ResetDatabase() {
        super("Select an option and hit submit");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(200, 150);
        setLocationRelativeTo(null);
    }

    public void showFrame(MainWindowDelegate del) {
        this.delegate = del;

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JLabel prompt = new JLabel("are you sure?");

        JRadioButton yes = new JRadioButton("yes");
        JRadioButton no = new JRadioButton("no", true);

        ButtonGroup options = new ButtonGroup();
        options.add(yes);
        options.add(no);

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        buttons.add(yes);
        buttons.add(no);

        JButton submit = new JButton("submit");
        submit.addActionListener(e -> {
            if (options.getSelection() == yes.getModel()) {
                delegate.databaseSetup();
            }
            this.dispose();
        });

        panel.add(prompt);
        panel.add(buttons);
        panel.add(submit);

        this.add(panel);
        this.setVisible(true);
    }
}
