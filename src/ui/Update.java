package ui;

import delegates.MainWindowDelegate;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Update extends JFrame {
    MainWindowDelegate delegate;
    Update(MainWindowDelegate del) {
        super("Update an artwork");
        delegate = del;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);
    }

    public void showUpdateWindow() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JLabel prompt = new JLabel("Select the title of an artwork you want to update:");


        JComboBox<String> artworks = new JComboBox<String>(delegate.getArtworkTitles());

        JLabel prompt1 = new JLabel("Attribute to change:");

        JComboBox<String> attr = new JComboBox<String>(new String[]{"artistID", "title", "dimensions", "dateCreated",
                "value", "donorID", "featureID", "displayMedium"});

        JLabel prompt2 = new JLabel("Change to:");
        JTextField entry = new JTextField();
        entry.setSize(200, 100);

        JButton submit = new JButton("submit");
        submit.addActionListener(e -> {
            try {
                delegate.update(artworks.getSelectedItem().toString(),
                        attr.getSelectedItem().toString(), entry.getText().toString());
                submit.setText("Success!");
                String[] newTitles = delegate.getArtworkTitles();
                artworks.setModel(new DefaultComboBoxModel(newTitles));
            } catch (Exception k) {
                submit.setText("Error! Could not update entry.");
            }
        });


        panel.add(prompt);
        panel.add(artworks);
        panel.add(prompt1);
        panel.add(attr);
        panel.add(prompt2);
        panel.add(entry);
        panel.add(submit);

        add(panel);


        setVisible(true);
    }
}
