package ui;

import delegates.MainWindowDelegate;

import javax.swing.*;


// FIND ALL VISITORS WHO HAVE VISITED EVERY EXHIBITION
public class Division extends JFrame {
    MainWindowDelegate delegate;
    Division(MainWindowDelegate del) {
        super("Update an artwork");
        delegate = del;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    public void showframe() {
        String[] loyalVisitors;

        System.out.println("grabbing the visitors...");
        try {
            loyalVisitors = delegate.getAllLoyalVisitors();
        } catch (Exception e) {
            System.out.println("Error getting visitors");
            loyalVisitors = new String[]{"Error getting visitors"};
        }

        System.out.println("visitors grabbed");
        System.out.println(loyalVisitors.length);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("List of all of the visitors who have come to every exhibition:");
        panel.add(title);

        for (String loyalVisitor : loyalVisitors) {
            panel.add(new JLabel(loyalVisitor));
        }

        add(panel);
        setVisible(true);
    }
}
