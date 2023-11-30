package ui;

import javax.swing.*;
public class ResultsWindow extends JFrame {
    ResultsWindow(String[] columnHeaders, String[][] data) {
        super("Result");
        JTable jt=new JTable(data,columnHeaders);
        jt.setBounds(30,40,200,300);
        JScrollPane sp=new JScrollPane(jt);
        this.add(sp);
        this.setSize(300,400);
        this.setVisible(true);
    }
}
