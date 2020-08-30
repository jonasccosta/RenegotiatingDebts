import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import java.awt.*;

public class FinalResult extends JFrame{
    private JTable tableResults;
    private GridBagConstraints CONSTRAINTS = new GridBagConstraints();

    public FinalResult(String result1Label, String result2Label, Installments[] installments, UpdatedValue updatedValue) {
        setLayout(new GridBagLayout());
        setUpResult1Label(result1Label);
        setUpResult2Label(result2Label);
        setUpTable(installments, updatedValue);
        setUpExitButton();
        setUpReturnButton();
        setUpSaveButton();

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

    private void setUpTable(Installments[] installments, UpdatedValue updatedValue) {
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
        fillTable(tableResults, installments, updatedValue);

    }

    private void setUpExitButton() {
        JButton exit = new JButton("Exit");
        exit.setMnemonic(KeyEvent.VK_E);
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 1;
        CONSTRAINTS.gridy = 9;
        CONSTRAINTS.gridwidth = 1;
        CONSTRAINTS.insets = new Insets(10, 5, 10, 1);
        add(exit, CONSTRAINTS);
        exit.addActionListener(e-> System.exit(0));

    }

    private void setUpReturnButton() {
        JButton goBack = new JButton("Return");
        goBack.setMnemonic(KeyEvent.VK_R);
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 2;
        CONSTRAINTS.gridy = 9;
        CONSTRAINTS.gridwidth = 1;
        CONSTRAINTS.insets = new Insets(10, 1, 10, 1);
        add(goBack, CONSTRAINTS);

        goBack.addActionListener(e-> FinalResult.this.setVisible(false));
    }

    private void setUpSaveButton() {

        JButton save = new JButton("Save");
        save.setMnemonic(KeyEvent.VK_S);
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 3;
        CONSTRAINTS.gridy = 9;
        CONSTRAINTS.gridwidth = 1;
        CONSTRAINTS.insets = new Insets(10, 1, 10, 1);
        add(save, CONSTRAINTS);

        save.addActionListener(e-> {
            DefaultTableModel m = (DefaultTableModel) tableResults.getModel();
            ExportToExcel eTE = new ExportToExcel();
            eTE.toExcel(m);
        });
    }


    private void fillTable(JTable table, Installments[] installments, UpdatedValue updatedValue){
        System.out.println(installments.length);
        for(int i = 0; i < installments.length; i++){
            //Put the information from an array of installments into a table
            table.getModel().setValueAt("R$ " + updatedValue.formatter(installments[i].getValue()), i, 0);
            table.getModel().setValueAt(installments[i].getDate(), i, 1);
            table.getModel().setValueAt("R$ " + updatedValue.formatter(installments[i].getInterest()),i, 2);
            table.getModel().setValueAt("R$ " + updatedValue.formatter(installments[i].getNewValue()), i, 3);
        }
    }


}

