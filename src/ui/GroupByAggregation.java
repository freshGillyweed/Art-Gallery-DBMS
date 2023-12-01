package ui;

import database.DatabaseConnectionHandler;
import delegates.MainWindowDelegate;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.Connection;

import model.EventModel;
import util.PrintablePreparedStatement;

import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.xml.transform.Result;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;
import java.util.Vector;

public class GroupByAggregation extends JFrame{
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private MainWindowDelegate delegate;
    private DatabaseConnectionHandler dbHandler;
    private Connection connection;
    private DefaultTableModel model;
    private JTable table;
    public GroupByAggregation(MainWindowDelegate del, DatabaseConnectionHandler dbHandler) {
        super ("View Donor Donation Value Summary");
        this.delegate = del;
        this.dbHandler = dbHandler;
        this.connection = dbHandler.getConnection();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
    }

    public void showFrame() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(900,650));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        DefaultTableModel dModel = new DefaultTableModel();
        JTable donorTable = new JTable(dModel);
        model = new DefaultTableModel();
        table = new JTable(model);
        populateTable("Artwork", model);
        populateTable("Donor", dModel);

        TableColumnModel columnModel = donorTable.getColumnModel();
        columnModel.removeColumn(columnModel.getColumn(2));

        JScrollPane scrollPane = new JScrollPane(table);
        JScrollPane scrollPane2 = new JScrollPane(donorTable);
        String[] aggregationOptions = new String[]{"minimum", "maximum", "average", "count"};

        JLabel title = new JLabel("Use the dropdown menu to select type of summary ");
        JComboBox<String> agg = new JComboBox<String>(aggregationOptions);
        JButton submit = new JButton("Submit");
        JPanel buttons = new JPanel();


        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String option = Objects.requireNonNull(agg.getSelectedItem()).toString();
                try {
                    ResultSet rs = dbHandler.getArtworkValueSummary(option);
                    updateTable(rs);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        buttons.add(title);
        buttons.add(agg);
        buttons.add(submit);
        panel.add(buttons);
        panel.add(scrollPane);
        panel.add(scrollPane2);
        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void updateTable(ResultSet rs) {
        try {
            if (rs != null) {

                model.setRowCount(0);


                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                String[] columnNames = new String[columnCount];

                for (int i = 1; i <= columnCount; i++) {
                    columnNames[i - 1] = metaData.getColumnName(i);
                }
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
        } catch (SQLException e) {
            handleSQLException(e);
        }


    }

    public void populateTable(String tableName, DefaultTableModel model) {
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
            case "Artwork" -> fetchArtworkData();
            case "Donor" -> fetchDonorData();
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

    public ResultSet fetchDonorData() {
        try {
            String query = "SELECT * FROM DONOR";
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

}
