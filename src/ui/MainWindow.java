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
        super("Art Gallery Operations");
        this.delegate = del;
        this.dbHandler = dbHandler;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
    }

    public void showFrame() {

        JPanel panel = new JPanel();
        GridLayout mainLayout = new GridLayout(5, 2, 5, 10);
        panel.setLayout(mainLayout);

        // Create buttons and implement respective action listeners
        // i.e., create buttons that open new windows

        JButton generalMenuButton = new JButton("General Menu");
        generalMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BasicOperationsWindow window = new BasicOperationsWindow(delegate, dbHandler);
                window.showFrame();
            }
        });

        JButton donationSummaryButton = new JButton("View Donor Donation Value Summary");
        donationSummaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GroupByAggregation window = new GroupByAggregation(delegate, dbHandler);
                window.showFrame();
            }
        });

        panel.add(donationSummaryButton);

        JButton projectBudgetButton = new JButton("Average Project Budget");
        projectBudgetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NestedGroupByAggregation window = new NestedGroupByAggregation(dbHandler);
                window.showFrame();
                //window.setVisible(true);
            }
        });

        panel.add(generalMenuButton);
        // average project budget button
        panel.add(projectBudgetButton);

        // reset database button

        JButton resetDatabaseButton = new JButton("Reset database");

        resetDatabaseButton.addActionListener(e -> {
            ResetDatabase resetDatabaseWindow = new ResetDatabase();
            resetDatabaseWindow.showFrame(delegate);
        });

        panel.add(resetDatabaseButton);

        // update attribute button

        JButton updateButton = new JButton("Update an Artwork");

        updateButton.addActionListener(e -> {
            Update win = new Update(delegate);
            win.showUpdateWindow();
        });

        panel.add(updateButton);

        // division

        JButton DivisionButton = new JButton("see every loyal customer");

        DivisionButton.addActionListener(e -> {
            Division juh = new Division(delegate);
            juh.showframe();
        });

        panel.add(DivisionButton);

        // JOIN

        JButton joinButton = new JButton("search the value of artists");

        joinButton.addActionListener(e -> {
            Join yuh = new Join(delegate);
            yuh.showFrame();
        });

        panel.add(joinButton);
        
        // HAVING AGGREGATION

        JButton havingButton = new JButton("get countries by donations");

        havingButton.addActionListener(e -> {
            HavingAggregation ok = new HavingAggregation(delegate);
            ok.showFrame();
        });


        panel.add(havingButton);
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

        //projection button
        JButton projectionButton = new JButton("Access Information Across All Categories");
        projectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Projection window = new Projection(dbHandler);
                window.showFrame();
            }
        });

        panel.add(projectionButton);

        add(panel);
        setVisible(true);
    }
}
