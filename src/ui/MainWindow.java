package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    public MainWindow() {
        super("Main Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
    }

    public void showFrame() {

        // Create buttons and implement respective action listeners
        // i.e., create buttons that open new windows
        JButton projectBudgetButton = new JButton("Average Project Budget");
        projectBudgetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NestedGroupByAggregation window = new NestedGroupByAggregation();
                window.setVisible(true);
            }
        });

        JPanel panel = new JPanel();

        // Add buttons to panel
        panel.add(projectBudgetButton);

        add(panel);
        setVisible(true);
    }

    // Uncomment to open main window

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            MainWindow mainWindow = new MainWindow();
//            mainWindow.showFrame();
//        });
//    }

}
