package ui;

import delegates.MainWindowDelegate;

import javax.swing.*;
public class Update extends JFrame {
    MainWindowDelegate delegate;
    Update(MainWindowDelegate del) {
        super("Update an artwork");
        delegate = del;

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JLabel prompt = new JLabel("Select an artwork to update:");

        JComboBox<String> artworks = new JComboBox<String>();

        JTextField entry = new JTextField();

        JButton submit = new JButton("submit");
        submit.addActionListener(e -> {

        });

    }
}
