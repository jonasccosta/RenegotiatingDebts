import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FirstResult extends JFrame {

    private final GridBagConstraints CONSTRAINTS = new GridBagConstraints();

    private JTable tableResults;
    private UpdatedValue updatedValue;


    public FirstResult(String result1Text, String result2Text, UpdatedValue updatedValue) {
        setLayout(new GridBagLayout());
        this.updatedValue = updatedValue;
        setUpResult1Label(result1Text);
        setUpResult2Label(result2Text);
        setUpTable(updatedValue);
        setUpExitButton();
        setUpReturnButton();
        setUpSaveButton();
        setUpInstallButton();

    }

    private void setUpResult1Label(String text) {
        JLabel result1 = new JLabel(text);
        CONSTRAINTS.fill = GridBagConstraints.CENTER;
        CONSTRAINTS.gridx = 0;
        CONSTRAINTS.gridy = 0;
        CONSTRAINTS.gridwidth = 4;
        CONSTRAINTS.gridheight = 1;
        CONSTRAINTS.insets = new Insets(10, 5, 1, 5);
        add(result1, CONSTRAINTS);
    }

    private void setUpResult2Label(String text) {
        JLabel result2 = new JLabel(text);
        CONSTRAINTS.fill = GridBagConstraints.CENTER;
        CONSTRAINTS.gridx = 0;
        CONSTRAINTS.gridy = 1;
        CONSTRAINTS.gridwidth = 4;
        CONSTRAINTS.gridheight = 1;
        CONSTRAINTS.insets = new Insets(1, 5, 1, 5);
        add(result2, CONSTRAINTS);
    }

    private void setUpTable(UpdatedValue updatedValue) {
        String[] columnNames = {"Old Value", "Old Date", "Interest", "New Value"};
        String[][] data = new String[5][4];
        tableResults = new JTable(new DefaultTableModel(data, columnNames));
        tableResults.setPreferredScrollableViewportSize(new Dimension(300, 80));
        DefaultTableModel model = (DefaultTableModel) tableResults.getModel();
        tableResults.setEnabled(false);
        tableResults.getTableHeader().setReorderingAllowed(false);
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 0;
        CONSTRAINTS.gridy = 3;
        CONSTRAINTS.gridwidth = 4;
        CONSTRAINTS.insets = new Insets(10, 5, 1, 5);
        JScrollPane scrollPane = new JScrollPane(tableResults);
        add(scrollPane, CONSTRAINTS);
        fillTable(updatedValue, tableResults);
    }


    private void setUpExitButton() {
        JButton exit = new JButton("Exit");
        exit.setMnemonic(KeyEvent.VK_E);
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 0;
        CONSTRAINTS.gridy = 8;
        CONSTRAINTS.gridwidth = 1;
        CONSTRAINTS.insets = new Insets(10, 5, 10, 1);
        add(exit, CONSTRAINTS);

        exit.addActionListener(e -> System.exit(0));

    }

    private void setUpReturnButton() {
        JButton returnButton = new JButton("Return");
        returnButton.setMnemonic(KeyEvent.VK_R);
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 1;
        CONSTRAINTS.gridy = 8;
        CONSTRAINTS.gridwidth = 1;
        CONSTRAINTS.insets = new Insets(10, 1, 10, 1);
        add(returnButton, CONSTRAINTS);
        returnButton.addActionListener(e -> FirstResult.this.setVisible(false));

    }

    private void setUpSaveButton() {
        JButton save = new JButton("Save");
        save.setMnemonic(KeyEvent.VK_S);
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 2;
        CONSTRAINTS.gridy = 8;
        CONSTRAINTS.gridwidth = 1;
        CONSTRAINTS.insets = new Insets(10, 1, 10, 1);
        add(save, CONSTRAINTS);
        save.addActionListener(e -> {
            DefaultTableModel m = (DefaultTableModel) tableResults.getModel();
            ExportToExcel eTE = new ExportToExcel();
            eTE.toExcel(m);
        });

    }

    private void setUpInstallButton() {

        JButton install = new JButton("Install");
        install.setMnemonic(KeyEvent.VK_I);
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 3;
        CONSTRAINTS.gridy = 8;
        CONSTRAINTS.gridwidth = 1;
        CONSTRAINTS.insets = new Insets(10, 1, 10, 5);
        add(install, CONSTRAINTS);

        install.addActionListener(e -> {
            Installment gui = new Installment();
            gui.setDefaultCloseOperation(HIDE_ON_CLOSE);
            gui.setVisible(true);
            gui.pack();
            gui.setResizable(false);
            gui.setLocationRelativeTo(null);
            gui.setTitle("Renegotiation");
            gui.setUpdatedValue(updatedValue);
        });

    }

    private void fillTable(UpdatedValue updatedValue, JTable table) {
        for (int i = 0; i < updatedValue.getInstallmentCount(); i++) {
            //Put the information from an array of installments into a table
            if (updatedValue.getInstallments()[i] != null) {
                double a = updatedValue.setDifference() / updatedValue.getInstallmentCount();
                double d = updatedValue.getInstallments()[i].getInterest() + a;
                double w = updatedValue.getInstallments()[i].getNewValue() + a;
                table.getModel().setValueAt("R$ " + updatedValue.formatter(updatedValue.getInstallments()[i].getValue()), i, 0);
                table.getModel().setValueAt(updatedValue.getInstallments()[i].getDate(), i, 1);
                table.getModel().setValueAt("R$ " + updatedValue.formatter(d), i, 2);
                table.getModel().setValueAt("R$ " + updatedValue.formatter(w), i, 3);
            }
        }
    }

    public void setUpdatedValue(UpdatedValue updatedValue) {
        this.updatedValue = updatedValue;
    }
}
