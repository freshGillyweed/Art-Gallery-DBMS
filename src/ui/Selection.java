package ui;

import database.DatabaseConnectionHandler;
import model.ProjectModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Selection extends JFrame {
    DatabaseConnectionHandler dbHandler;
    private JComboBox<String> categoryComboBox;
    private JTextField userInputField;
    private JButton andButton;
    private JButton orButton;
    private JButton saveConditionButton;
    private JButton processButton;
    private JTextArea resultTextArea;
    private StringBuilder whereClause;

    public Selection(DatabaseConnectionHandler dbHandler) {
        super("View Filtered Information For Project");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        this.dbHandler = dbHandler;
        whereClause = new StringBuilder();
    }

    public void showFrame() {
        categoryComboBox = new JComboBox<>(new String[]{"ProjectID", "Title", "Budget", "Status", "Start Date", "End Date"});
        userInputField = new JTextField(15);
        andButton = new JButton("AND");
        orButton = new JButton("OR");
        saveConditionButton = new JButton("Save condition");
        processButton = new JButton("Process");
        resultTextArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel("Category:"));
        panel.add(categoryComboBox);
        panel.add(new JLabel("Input:"));
        panel.add(userInputField);
        panel.add(saveConditionButton);
        panel.add(andButton);
        panel.add(orButton);
        panel.add(processButton);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        saveConditionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String category = (String) categoryComboBox.getSelectedItem();
                String userInput = userInputField.getText();

                if (category.equals("ProjectID")) {
                    whereClause.append("ProjectID = ").append(userInput);
                } else if (category.equals("Title")) {
                    whereClause.append("Title = '").append(userInput).append("'");
                } else if (category.equals("Budget")) {
                    whereClause.append("Budget = ").append(userInput);
                } else if (category.equals("Status")) {
                    whereClause.append("Status = '").append(userInput).append("'");
                } else if (category.equals("Start Date")) {
                    whereClause.append("StartDate = '").append(userInput).append("'");
                } else {
                    whereClause.append("EndDate = '").append(userInput).append("'");
                }
                userInputField.setText(""); // Clear the input field
            }
        });

        andButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                whereClause.append(" AND ");
            }
        });

        orButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                whereClause.append(" OR ");
            }
        });

        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProjectModel[] projects = dbHandler.getProjectSelectionInfo(whereClause.toString(), false);

                resultTextArea.setText("");

                for (ProjectModel project : projects) {
                    resultTextArea.append("ProjectID: " + project.getProjectID() + "\n");
                    resultTextArea.append("Title: " + project.getTitle() + "\n");
                    resultTextArea.append("Budget: " + project.getBudget() + "\n");
                    resultTextArea.append("Status: " + project.getStatus() + "\n");
                    resultTextArea.append("Start Date: " + project.getStartDate() + "\n");
                    resultTextArea.append("End Date: " + project.getEndDate() + "\n\n");
                }
            }
        });

        this.setVisible(true);
    }
}

