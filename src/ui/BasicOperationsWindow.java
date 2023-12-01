package ui;

import database.DatabaseConnectionHandler;
import delegates.MainWindowDelegate;
import model.EventModel;
import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

// Operations for this window:
//  INSERT, DELETE, UPDATE
public class BasicOperationsWindow extends JFrame {
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private MainWindowDelegate delegate;
    private DatabaseConnectionHandler dbHandler;
    private Connection connection;
    DefaultTableModel eventTableModel;
    DefaultTableModel artistTableModel;
    JTable eventTable;
    JTable artistTable;
    BasicOperationsWindow(MainWindowDelegate del, DatabaseConnectionHandler dbHandler) {
        super ("General Art Gallery Options");
        this.delegate = del;
        this.dbHandler = dbHandler;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
    }

    public void showFrame() {
        JTabbedPane tabbedPane = new JTabbedPane();
        eventTableModel = new DefaultTableModel();
        eventTable = new JTable(eventTableModel);
        JScrollPane eventScrollPane = new JScrollPane(eventTable);
        tabbedPane.addTab("Event", eventScrollPane);

        artistTableModel = new DefaultTableModel();
        artistTable = new JTable(artistTableModel);
        JScrollPane artistScrollPane = new JScrollPane(artistTable);
        tabbedPane.addTab("Artist", artistScrollPane);

        add(tabbedPane, BorderLayout.CENTER);

        // adds buttons
        JPanel buttonsPanel = new JPanel();
        add(buttonsPanel, BorderLayout.SOUTH);

        // INSERT BUTTONS
        JButton insertEventButton = new JButton("Insert Event");
        insertEventButton.addActionListener(new InsertEventAction());
        buttonsPanel.add(insertEventButton);

        // TODO: ADD ARTIST ACTION if enough time???
        // JButton insertArtistButton = new JButton("Insert Artist");
        //
       //  buttonsPanel.add(insertArtistButton);


        // TODO: ADD DELETE BUTTONS (ARTIST, maybe event and artwork)
        // display everything
        setVisible(true);
    }


    private void handleSQLException(SQLException e) {
        // potentially throw another exception for the GUI to handle
        switch (e.getErrorCode()) {
            case 1400 -> {
                // insert null
                // specify which value?
                System.out.println("Error: Attempt to insert or update NULL into a NOT NULL column.");
                JOptionPane.showMessageDialog(null, "Error: Attempt to insert or update NULL into a NOT NULL column.", "Error", JOptionPane.ERROR_MESSAGE);
                dbHandler.rollbackConnection();
            }
            case 2291 -> {
                JOptionPane.showMessageDialog(null, "Error: Attempt to insert a foreign key value that does not exist in the referenced table.", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error: Attempt to insert a foreign key value that does not exist in the referenced table.");
                dbHandler.rollbackConnection();
            }
            case 1 -> {
                JOptionPane.showMessageDialog(null, "Error: Attempt to insert a non-unique value into a column with a unique constraint.", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error: Attempt to insert a non-unique value into a column with a unique constraint.");
                dbHandler.rollbackConnection();
            }
            default -> {
                JOptionPane.showMessageDialog(null, EXCEPTION_TAG + " " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println(EXCEPTION_TAG + " " + e.getMessage());
                dbHandler.rollbackConnection();
            }
        }
    }
    public class InsertEventAction extends AbstractAction {

        InsertEventAction() {
            super("Insert Event");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            EventInputDialog eventDialog = new EventInputDialog(BasicOperationsWindow.this);
            eventDialog.setVisible(true);

            // Get the input event from the dialog
            EventModel event = eventDialog.getEvent();
            try {
                dbHandler.insertEvent(event);

            } catch (SQLException ex) {
                handleSQLException(ex);
            }
        }

    }

    // this is for the inputting of new events , provides a user-friendly window with text fields
    public class EventInputDialog extends JDialog {

        private JTextField titleField;
        private JTextField ticketsSoldField;
        private JTextField locationField;
        private JTextField eventDateField;
        private JTextField capacityField;
        private JTextField eventIDField;

        private EventModel event;

        public EventInputDialog(Frame owner) {
            super(owner, "Enter Event Details", true);
            setLayout(new GridLayout(6, 2));

            eventIDField = new JTextField(20);
            titleField = new JTextField(20);
            ticketsSoldField = new JTextField(20);
            locationField = new JTextField(20);
            eventDateField = new JTextField(20);
            capacityField = new JTextField(20);

            JButton okButton = new JButton("OK");
            okButton.addActionListener(e -> {
                try {
                    event = new EventModel(
                            Integer.parseInt(eventIDField.getText()),
                            eventDateField.getText(),
                            locationField.getText(),
                            Integer.parseInt(ticketsSoldField.getText()),
                            Integer.parseInt(capacityField.getText()),
                            titleField.getText()
                    );
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BasicOperationsWindow.this, "Invalid input. Please make sure all required fields are filled.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            });

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> dispose());

            setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

            add(createInputPanel("EventID:", eventIDField));
            add(createInputPanel("Title:", titleField));
            add(createInputPanel("Tickets Sold:", ticketsSoldField));
            add(createInputPanel("Location:", locationField));
            add(createInputPanel("Event Date:", eventDateField));
            add(createInputPanel("Capacity:", capacityField));

            // Add buttons at the bottom
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);
            add(buttonPanel);
            pack();
            setLocationRelativeTo(owner);
        }
        private JPanel createInputPanel(String labelText, JTextField textField) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.add(new JLabel(labelText));
            panel.add(textField);
            return panel;
        }

        public EventModel getEvent() {
            return event;
        }
    }
}
