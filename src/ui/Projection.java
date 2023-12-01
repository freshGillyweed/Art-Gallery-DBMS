package ui;

import database.DatabaseConnectionHandler;
import util.PrintablePreparedStatement;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class Projection extends JFrame {
    private DatabaseConnectionHandler dbHandler;
    private JComboBox<String> tableComboBox;
    private ArrayList<JCheckBox> attributeCheckBoxes;
    private JButton displayButton;
    private JTextArea resultTextArea;
    private Connection connection;
    private JPanel mainPanel;
    public Projection(DatabaseConnectionHandler dbHandler) {
        super("Access Information Across All Categories");
        this.dbHandler = dbHandler;
        this.connection = dbHandler.getConnection();

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        populateTableComboBox();
    }
    public void showFrame() {
        this.setVisible(true);
    }

    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new GridLayout(0, 1));

        tableComboBox = new JComboBox<>();
        tableComboBox.addActionListener(e -> populateAttributeCheckBoxes());

        selectionPanel.add(new JLabel("Select Table:"));
        selectionPanel.add(tableComboBox);

        displayButton = new JButton("Display");
        displayButton.addActionListener(e -> generateSQLQuery());
        selectionPanel.add(displayButton, BorderLayout.SOUTH);//

        mainPanel.add(selectionPanel, BorderLayout.NORTH);

        attributeCheckBoxes = new ArrayList<>();
        JScrollPane attributeScrollPane = new JScrollPane(createAttributeCheckBoxPanel());
        mainPanel.add(attributeScrollPane, BorderLayout.CENTER);


        resultTextArea = new JTextArea(10, 30);
        JScrollPane resultScrollPane = new JScrollPane(resultTextArea);
        mainPanel.add(resultScrollPane, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createAttributeCheckBoxPanel() {
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new GridLayout(0, 1));
        return checkBoxPanel;
    }

    private void populateTableComboBox() {
        try {
            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet resultSet = connection.createStatement().executeQuery("SELECT USER FROM DUAL");
            resultSet.next();
            String currentUser = resultSet.getString(1);

            ResultSet tables = metaData.getTables(null, currentUser, "%", new String[]{"TABLE"});

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                tableComboBox.addItem(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void populateAttributeCheckBoxes() {
        String selectedTable = (String) tableComboBox.getSelectedItem();

        if (selectedTable != null) {
            try {
                ResultSetMetaData metaData = connection.prepareStatement("SELECT * FROM " + selectedTable).getMetaData();
                int columnCount = metaData.getColumnCount();

                JPanel checkBoxPanel = createAttributeCheckBoxPanel();
                attributeCheckBoxes.clear();
                checkBoxPanel.removeAll();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    JCheckBox checkBox = new JCheckBox(columnName);

                    checkBox.setPreferredSize(new Dimension(100, 20));

                    attributeCheckBoxes.add(checkBox);
                    checkBoxPanel.add(checkBox);
                }

                JScrollPane scrollPane = (JScrollPane) mainPanel.getComponent(1);
                scrollPane.setViewportView(checkBoxPanel);

                revalidate();
                repaint();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void generateSQLQuery() {
        StringBuilder queryBuilder = new StringBuilder("SELECT ");

        for (JCheckBox checkBox : attributeCheckBoxes) {
            if (checkBox.isSelected()) {
                queryBuilder.append(checkBox.getText()).append(", ");
            }
        }

        if (queryBuilder.toString().equals("SELECT ")) {
            JOptionPane.showMessageDialog(this, "Please select at least one attribute!");
            return;
        }

        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length()); // Remove last comma
        queryBuilder.append(" FROM ").append(tableComboBox.getSelectedItem());

        String finalQuery = queryBuilder.toString();
        System.out.println("Generated SQL Query: " + finalQuery);

        ResultSet rs;
        try {
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(finalQuery), finalQuery, false);
            rs = ps.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // display tuples returned by getProjectionResults
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Display column names
            for (int i = 1; i <= columnCount; i++) {
                resultTextArea.append(metaData.getColumnName(i) + "\t");
            }
            resultTextArea.append("\n");

            // Display data as strings
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = rs.getString(i);
                    resultTextArea.append(columnValue + "\t");
                }
                resultTextArea.append("\n");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
