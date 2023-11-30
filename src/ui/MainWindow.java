package ui;

import database.DatabaseConnectionHandler;
import delegates.MainWindowDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    private MainWindowDelegate delegate;
    private DatabaseConnectionHandler dbHandler;

    public MainWindow(MainWindowDelegate del, DatabaseConnectionHandler dbHandler) {
        super("Main Window");
        this.delegate = del;
        this.dbHandler = dbHandler;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
    }

    public void showFrame() {

        JPanel panel = new JPanel();
        GridLayout mainLayout = new GridLayout(5, 2, 5, 10);
        panel.setLayout(mainLayout);

        // Create buttons and implement respective action listeners
        // i.e., create buttons that open new windows

        JButton projectBudgetButton = new JButton("Average Project Budget");
        projectBudgetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NestedGroupByAggregation window = new NestedGroupByAggregation(dbHandler);
                window.showFrame();
                //window.setVisible(true);
            }
        });

        // average project budget button
        panel.add(projectBudgetButton);

        // reset database button

        JButton resetDatabaseButton = new JButton("Reset database");

        resetDatabaseButton.addActionListener(e -> {
            ResetDatabase resetDatabaseWindow = new ResetDatabase();
            resetDatabaseWindow.showFrame(delegate);
        });

        panel.add(resetDatabaseButton);

        // update arrtibute button

        JButton updateButton = new JButton("Update an Artwork");

        updateButton.addActionListener(e -> {
            Update win = new Update(delegate);
            win.showUpdateWindow();
        });

        panel.add(updateButton);

        // selection button

        JButton selectionButton = new JButton("Get Project Information");
        selectionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Selection window = new Selection(dbHandler);
                window.showFrame();
                //window.setVisible(true);
            }
        });

        panel.add(selectionButton);

        add(panel);
        setVisible(true);
    }
}
