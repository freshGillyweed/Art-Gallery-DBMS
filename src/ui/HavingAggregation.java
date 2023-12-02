package ui;

import delegates.MainWindowDelegate;

import javax.swing.*;
import java.awt.*;
import java.util.Dictionary;
import java.util.Hashtable;

//SELECT column_name(s)
//        FROM table_name
//        WHERE condition
//        GROUP BY column_name(s)
//        HAVING condition
public class HavingAggregation extends JFrame {

    MainWindowDelegate delegate;
    HavingAggregation(MainWindowDelegate del) {
        super("get countries");
        delegate = del;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
    }

    public void showFrame() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        String[] options = new String[]{"over", "under", "equal to"};

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        JLabel prompt = new JLabel("show the countries of donors that have donated");
        JComboBox<String> op = new JComboBox<String>(options);
        JSpinner val = new JSpinner();
        val.setMinimumSize(new Dimension(100, 30));
        val.setPreferredSize(new Dimension(100, 30));
        val.setSize(new Dimension(100, 30));
        inputPanel.add(prompt);
        inputPanel.add(op);
        inputPanel.add(val);

        panel.add(inputPanel);

        panel.add(new JLabel("countries:"));

        JTextArea area = new JTextArea();
        area.setEditable(false);
        JScrollPane results = new JScrollPane(area);

        panel.add(results);

        JButton submit = new JButton("submit");

        Dictionary<String, String> dict = new Hashtable<>();

        dict.put(options[0], ">");
        dict.put(options[1], "<");
        dict.put(options[2], "=");

        submit.addActionListener(e -> {
            try {
                String[] res = delegate.getDonorsValue(dict.get(op.getSelectedItem().toString()), (Integer) val.getValue());
                area.setText("");
                for (int i = 0; i < res.length; i++) {
                    area.append(res[i]);
                    area.append("\n");
                }
                submit.setText("Success!");
            } catch (Exception k) {
                submit.setText("failed to get countries");
            }
        });
        panel.add(submit);

        add(panel);
        setVisible(true);
    }

}
