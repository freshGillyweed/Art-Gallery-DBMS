package ui;

import delegates.MainWindowDelegate;
import delegates.TerminalTransactionsDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainWindow extends JFrame {

    private MainWindowDelegate delegate;

    public MainWindow() {
        super("Main Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
    }

    public void showFrame(MainWindowDelegate del) {
        this.delegate = del;

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout());

        // Create buttons and implement respective action listeners
        // i.e., create buttons that open new windows
        JButton projectBudgetButton = new JButton("Average Project Budget");
        projectBudgetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NestedGroupByAggregation window = new NestedGroupByAggregation();
                window.setVisible(true);
            }
        });

        // Add buttons to panel
        panel.add(projectBudgetButton);

        // reset database button

        JButton resetDatabaseButton = new JButton("Reset database");

        resetDatabaseButton.addActionListener(e -> {
            ResetDatabase resetDatabaseWindow = new ResetDatabase();
            resetDatabaseWindow.showFrame(delegate);
        });

        panel.add(resetDatabaseButton);

        //

        add(panel);
        setVisible(true);
    }
}
