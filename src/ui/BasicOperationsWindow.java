package ui;

import database.DatabaseConnectionHandler;
import delegates.MainWindowDelegate;
import model.ArtistModel;
import model.ArtworkModel;
import model.EventModel;
import util.PrintablePreparedStatement;

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
    DefaultTableModel artworkTableModel;
    DefaultTableModel artistTableModel;
    JTable eventTable;
    JTable artworkTable;
    JTable artistTable;
    BasicOperationsWindow(MainWindowDelegate del, DatabaseConnectionHandler dbHandler) {
        super ("General Art Gallery Options");
        this.delegate = del;
        this.dbHandler = dbHandler;
        this.connection = dbHandler.getConnection();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
    }

    public void showFrame() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setSize(300,300);

        eventTableModel = new DefaultTableModel();
        eventTable = new JTable(eventTableModel);

        artistTableModel = new DefaultTableModel();
        artistTable = new JTable(artistTableModel);

        artworkTableModel = new DefaultTableModel();
        artworkTable = new JTable(artworkTableModel);

        JPanel eventPanel = new JPanel();
        JPanel artistPanel = new JPanel();
        JPanel artworkPanel = new JPanel();

        tabbedPane.addTab("Event", eventPanel);
        tabbedPane.addTab("Artist", artistPanel);
        tabbedPane.addTab("Artwork", artworkPanel);

        JScrollPane eventScrollPane = new JScrollPane(eventTable);
        JScrollPane artistScrollPane = new JScrollPane(artistTable);
        JScrollPane artworkScrollPane = new JScrollPane(artworkTable);

        updateTable("Event", eventTableModel, eventTable);
        updateTable("Artist", artistTableModel, artistTable);
        updateTable("Artwork", artworkTableModel, artworkTable);

        // adds buttons
        JPanel eventButtonsPanel = new JPanel();
        JPanel artistButtonsPanel = new JPanel();
        JPanel artworkButtonsPanel = new JPanel();

        // INSERT BUTTONS
        JButton insertEventButton = new JButton("Insert Event");
        insertEventButton.addActionListener(new InsertEventAction());

        JButton deleteEventButton = new JButton("Delete Event");
        deleteEventButton.addActionListener(new DeleteAction("Event", eventTable, eventTableModel));

        JButton insertArtistButton = new JButton("Insert Artist");
        JButton deleteArtistButton = new JButton("Delete Artist");
        deleteArtistButton.addActionListener(new DeleteAction("Artist", artistTable, artistTableModel));

        JButton insertArtworkButton = new JButton("Insert Artwork: ");
        insertArtworkButton.addActionListener(new InsertArtworkAction());

        eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.PAGE_AXIS ));
        artistPanel.setLayout(new BoxLayout(artistPanel, BoxLayout.PAGE_AXIS ));
        artworkPanel.setLayout(new BoxLayout(artworkPanel, BoxLayout.PAGE_AXIS ));

        eventButtonsPanel.add(insertEventButton);
        eventButtonsPanel.add(deleteEventButton);
        artistButtonsPanel.add(insertArtistButton);
        artistButtonsPanel.add(deleteArtistButton);
        artworkButtonsPanel.add(insertArtworkButton);

        eventPanel.add(eventButtonsPanel);
        artistPanel.add(artistButtonsPanel);
        artworkPanel.add(artworkButtonsPanel);

        artworkPanel.add(artworkScrollPane);
        eventPanel.add(eventScrollPane);
        artistPanel.add(artistScrollPane);

        add(tabbedPane, BorderLayout.CENTER);

        // display everything
        setVisible(true);
    }


    // HELPER METHODS
    public void updateTable(String tableName, DefaultTableModel model, JTable table) {
        try {
            ResultSet rs = fetchTableData(tableName);
            if (rs != null) {
                // Clear existing table data and fetch metadata
                model.setRowCount(0);
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                // Create column names array
                String[] columnNames = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    columnNames[i - 1] = metaData.getColumnName(i);
                }

                // Set column names to the model
                model.setColumnIdentifiers(columnNames);


                while (rs.next()) {
                    Object[] rowData = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        rowData[i - 1] = rs.getObject(i);
                    }
                    model.addRow(rowData);
                }
                rs.close();
            }
        } catch (SQLException ex) {
            handleSQLException(ex);
        }

    }
    private ResultSet fetchTableData(String tableName) {
        return switch (tableName) {
            case "Event" -> fetchEventData();
            case "Employee" -> fetchEmployeeData();
            case "Artwork" -> fetchArtworkData();
            case "Artist" -> fetchArtistData();
            default -> null;
        };
    }

    public ResultSet fetchEventData() {
        try {
            String query = "SELECT * FROM Event";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            return ps.executeQuery();
        }catch (SQLException ex) {
            handleSQLException(ex);
        }
        return null;
    }

    public ResultSet fetchEmployeeData() {
        try {
            String query = "SELECT * FROM Employees";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            return ps.executeQuery();
        }catch (SQLException ex) {
            handleSQLException(ex);
        }
        return null;
    }

    public ResultSet fetchArtworkData() {
        try {
            String query = "SELECT * FROM ARTWORK";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            return ps.executeQuery();
        }catch (SQLException ex) {
            handleSQLException(ex);
        }
        return null;
    }

    public ResultSet fetchArtistData()  {
        try {
            String query = "SELECT * FROM ARTIST";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            return ps.executeQuery();
        }catch (SQLException ex) {
            handleSQLException(ex);
        }
        return null;
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

    // HELPER CLASSES FOR GUI
    private class InsertEventAction extends AbstractAction {

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
                JOptionPane.showMessageDialog(BasicOperationsWindow.this,
                        "Event inserted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                updateTable("Event", eventTableModel, eventTable);
            } catch (SQLException ex) {
                handleSQLException(ex);
            }
        }

    }

    private class InsertArtworkAction extends AbstractAction {

        InsertArtworkAction() {
            super("Insert Artwork");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ArtworkInputDialog artworkInputDialog = new ArtworkInputDialog(BasicOperationsWindow.this);
            artworkInputDialog.setVisible(true);
            // Get the input event from the dialog
            ArtworkModel artwork = artworkInputDialog.getArtwork();
            try {
                dbHandler.insertArtwork(artwork);
                JOptionPane.showMessageDialog(BasicOperationsWindow.this,
                        "Event inserted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                updateTable("Event", eventTableModel, eventTable);
            } catch (SQLException ex) {
                handleSQLException(ex);
            }
        }

    }
    private class DeleteAction extends AbstractAction {
        private DefaultTableModel model;
        private JTable table;
        private String name;
        DeleteAction(String name, JTable table, DefaultTableModel model) {
            super("Delete " + name);
            this.model = model;
            this.table = table;
            this.name = name;
        }

        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(JOptionPane.showInputDialog(
                    BasicOperationsWindow.this,
                    "Please enter the id of the row you want to delete:"));
            if (name.equals("Event")) {
                try {
                    dbHandler.deleteEvent(id);
                } catch (SQLException ex) {
                    handleSQLException(ex);
                }
            } else if (name.equals("Artist")) {
                try {
                    dbHandler.deleteArtist(id);
                } catch (SQLException ex) {
                    handleSQLException(ex);
                }
            }

            JOptionPane.showMessageDialog(BasicOperationsWindow.this,
                    "Deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            updateTable(name, model, table);
        }
    }
    // this is for the inputting of new events , provides a user-friendly window with text fields
    private class EventInputDialog extends JDialog {

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

    private class ArtworkInputDialog extends JDialog{
        private  JTextField artworkID;
        private  JTextField artistID;
        private  JTextField title;
        private  JTextField dimensions;
        private  JTextField dateCreated;
        private  JTextField displayMedium;
        private JTextField donorID;
        private JTextField featureID;
        private JTextField value;

        private ArtworkModel artwork;

        public ArtworkInputDialog(Frame owner) {
            super(owner, "Enter Artwork Details", true);
            setLayout(new GridLayout(6, 2));

            artworkID = new JTextField(10);
            artistID = new JTextField(10);
            title = new JTextField(10);
            dimensions = new JTextField(10);
            dateCreated = new JTextField(10);
            displayMedium = new JTextField(10);
            donorID = new JTextField(10);
            featureID = new JTextField(10);
            value = new JTextField(10);

            JButton okButton = new JButton("OK");
            okButton.addActionListener(e -> {
                try {
                    artwork = new ArtworkModel(
                            Integer.parseInt(artistID.getText()),
                            Integer.parseInt(artworkID.getText()),
                            title.getText(),
                            dimensions.getText(),
                            dateCreated.getText(),
                            displayMedium.getText(),
                            Integer.parseInt(donorID.getText()),
                            Integer.parseInt(featureID.getText()),
                            Integer.parseInt(value.getText())

                    );
                    //this
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BasicOperationsWindow.this, "Invalid input. Please make sure all required fields are filled.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            });

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> dispose());
            setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

            add(createInputPanel("ArtworkID:", artworkID));
            add(createInputPanel("ArtistID:", artistID));
            add(createInputPanel("Title:", title));
            add(createInputPanel("Dimensions:", dimensions));
            add(createInputPanel("Date Created:", dateCreated));
            add(createInputPanel("Display Medium:", displayMedium));
            add(createInputPanel("DonorID:", donorID));
            add(createInputPanel("FeatureID:", featureID));
            add(createInputPanel("Value ($):", value));


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

        public ArtworkModel getArtwork() {
            return artwork;
        }
    }
}
