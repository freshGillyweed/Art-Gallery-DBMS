package ui;

import database.DatabaseConnectionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NestedGroupByAggregation extends JFrame {
    private JTextField thresholdField;
    //private NestedGroupByAggregationDelegate delegate;
    private DatabaseConnectionHandler dbHandler;
    private static final int TEXT_FIELD_WIDTH = 10;
    private static final int MAX_LOGIN_ATTEMPTS = 3;

    public NestedGroupByAggregation (DatabaseConnectionHandler dbHandler) {
        super("View Average Project Budget Over Status");
        this.dbHandler = dbHandler;
    }

    public void showFrame() {
        JPanel panel = new JPanel();
        this.setContentPane(panel);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        panel.setLayout(gb);
        panel.setBorder(BorderFactory.createEmptyBorder(80, 80, 80, 80));

        JLabel threshold = new JLabel("Enter threshold: ");

        thresholdField = new JTextField(TEXT_FIELD_WIDTH);

        // place the threshold label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(80, 80, 40, 0);
        gb.setConstraints(threshold, c);
        panel.add(threshold);

        // place the text field for the threshold
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(80, 0, 40, 80);
        gb.setConstraints(thresholdField, c);
        panel.add(thresholdField);

        // place resultLabel
        JLabel resultLabel= new JLabel("Enter threshold and press \"Calculate Average Budget Over Status\"");
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 80, 80, 0);
        //c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(resultLabel, c);
        panel.add(resultLabel);

        // place the calculateAverage button
        JButton calculateAverageButton = new JButton("Calculate Average Budget Over Status");
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 80, 80);//80
        //c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(calculateAverageButton, c);
        panel.add(calculateAverageButton);

        calculateAverageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Resolve connection issue!! Make connection accessible from everywhere or pass as parameter
                int threshold = Integer.parseInt(thresholdField.getText());
                double average = dbHandler.getAverageBudgetOverStatus(threshold);
                resultLabel.setText("Average spending for different project(Only projects with budgets over " + threshold + " are considered): " + average);
            }
        });

        this.pack();

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        // make the window visible
        this.setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // place the cursor in the text field for the username
        thresholdField.requestFocus();
    }

}
