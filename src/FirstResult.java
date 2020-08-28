import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FirstResult extends JFrame{

    private JLabel result1, result2;
    private JTable tableResults;
    private JButton save, exit, goBack, install;
    private JScrollPane scrollPane;
    private GridBagConstraints c;
    private DefaultTableModel model;
    private UpdatedValue k;

    public FirstResult(){
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();

        result1 = new JLabel("result");
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.insets = new Insets(10, 5, 1, 5);
        add(result1, c);

        result2 = new JLabel("result");
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.insets = new Insets(1, 5, 1, 5);
        add(result2, c);

        String[] columnNames = {"Old Value", "Old Date", "Interest", "New Value"};
        String[][] data = new String[5][4];
        tableResults = new JTable(new DefaultTableModel(data, columnNames));
        tableResults.setPreferredScrollableViewportSize(new Dimension(300,80));
        model = (DefaultTableModel)tableResults.getModel();
        tableResults.setEnabled(false);
        tableResults.getTableHeader().setReorderingAllowed(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 4;
        c.insets = new Insets(10, 5, 1, 5);
        scrollPane = new JScrollPane(tableResults);
        add(scrollPane, c);

        exit = new JButton("Exit");
        exit.setMnemonic(KeyEvent.VK_E);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        c.insets = new Insets(10, 5, 10, 1);
        add(exit, c);

        exit ex = new exit();
        exit.addActionListener(ex);

        goBack = new JButton("Return");
        goBack.setMnemonic(KeyEvent.VK_R);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 8;
        c.gridwidth = 1;
        c.insets = new Insets(10, 1, 10, 1);
        add(goBack, c);

        goBack gb = new goBack();
        goBack.addActionListener(gb);

        save = new JButton("Save");
        save.setMnemonic(KeyEvent.VK_S);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 8;
        c.gridwidth = 1;
        c.insets = new Insets(10, 1, 10, 1);
        add(save, c);

        save s = new save();
        save.addActionListener(s);

        install = new JButton("Install");
        install.setMnemonic(KeyEvent.VK_I);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 8;
        c.gridwidth = 1;
        c.insets = new Insets(10, 1, 10, 5);
        add(install, c);

        install ins = new install();
        install.addActionListener(ins);

    }

    public class goBack implements ActionListener{
        //Go back to the previous window
        public void actionPerformed(ActionEvent e) {
            FirstResult.this.setVisible(false);
        }
    }

    public class exit implements ActionListener {
        //Exits the program
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public class save implements ActionListener{
        //Open the window to save the table into a xls file
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel m = (DefaultTableModel)tableResults.getModel();
            ExportToExcel eTE = new ExportToExcel();
            eTE.toExcel(m);
        }
    }

    public class install implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            Installment gui = new Installment();
            gui.setDefaultCloseOperation(HIDE_ON_CLOSE);
            gui.setVisible(true);
            gui.pack();
            gui.setResizable(false);
            gui.setLocationRelativeTo(null);
            gui.setTitle("Renegotiation");
            gui.setK(k);
        }
    }

    public JLabel getResult1() {
        return result1;
    }

    public JLabel getResult2() {
        return result2;
    }

    public JTable getTableResults() {
        return tableResults;
    }

    public void setK(UpdatedValue k) {
        this.k = k;
    }
}
