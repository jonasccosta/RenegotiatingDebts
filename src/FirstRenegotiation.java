import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FirstRenegotiation extends JFrame {
    private final GridBagConstraints CONSTRAINTS = new GridBagConstraints();
    private JTextField manualInterest, newDate;
    private ButtonGroup group = new ButtonGroup();
    private JRadioButton automatic, manual;
    private JTable table;
    private DefaultTableModel model;

    public FirstRenegotiation() throws HeadlessException {
        setLayout(new GridBagLayout());
        setUpTable();
        setUpAddRowsButton();
        setUpDate1stPaymentLabel();
        setUpNewDate();
        setUpInterestLabel();
        setUpAutomaticInterestRadioButton();
        setUpManualInterestRadioButton();
        setUpManualInterestTextField();
        setUpExitButton();
        setUpOkButton();
    }


    private void setUpTable() {
        String[] columnNames = {"Value (R$)", "Due Date (dd/mm/yyyy)"};
        String[][] data = new String[5][2];
        table = new JTable(new DefaultTableModel(data, columnNames));
        table.setPreferredScrollableViewportSize(new Dimension(300, 80));
        model = (DefaultTableModel) table.getModel();
        table.getTableHeader().setReorderingAllowed(false);
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 0;
        CONSTRAINTS.gridy = 1;
        CONSTRAINTS.gridwidth = 4;
        CONSTRAINTS.insets = new Insets(10, 5, 1, 5);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, CONSTRAINTS);
    }

    private void setUpAddRowsButton() {
        JButton addRows = new JButton("Add rows");
        addRows.setMnemonic(KeyEvent.VK_R);
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 3;
        CONSTRAINTS.gridy = 6;
        CONSTRAINTS.gridwidth = 1;
        CONSTRAINTS.insets = new Insets(1, 1, 1, 5);
        add(addRows, CONSTRAINTS);

        addRows.addActionListener(e -> model.addRow(new String[2]));
    }

    private void setUpDate1stPaymentLabel() {
        JLabel date1stPayment = new JLabel("First date for payment (dd/mm/yyyy):");
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 0;
        CONSTRAINTS.gridy = 7;
        CONSTRAINTS.gridwidth = 2;
        CONSTRAINTS.insets = new Insets(10, 5, 1, 1);
        add(date1stPayment, CONSTRAINTS);
    }

    private void setUpNewDate() {
        newDate = new JTextField();
        newDate.setHorizontalAlignment(SwingConstants.RIGHT);
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 2;
        CONSTRAINTS.gridy = 7;
        CONSTRAINTS.gridwidth = 2;
        CONSTRAINTS.insets = new Insets(10, 1, 0, 5);
        add(newDate, CONSTRAINTS);
    }

    private void setUpInterestLabel() {
        JLabel interest = new JLabel("Interest");
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 0;
        CONSTRAINTS.gridy = 8;
        CONSTRAINTS.gridwidth = 2;
        CONSTRAINTS.insets = new Insets(10, 5, 0, 1);
        add(interest, CONSTRAINTS);
    }

    private void setUpAutomaticInterestRadioButton() {
        automatic = new JRadioButton("Automatic");
        automatic.setMnemonic(KeyEvent.VK_A);
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 0;
        CONSTRAINTS.gridy = 9;
        CONSTRAINTS.gridwidth = 1;
        CONSTRAINTS.insets = new Insets(0, 5, 1, 1);

        group.add(automatic);
        add(automatic, CONSTRAINTS);

        automatic.addActionListener(e -> {
            if (automatic.isSelected()) {
                manualInterest.setEnabled(false);
            }
        });
    }

    private void setUpManualInterestRadioButton() {
        manual = new JRadioButton("Manual (%)");
        manual.setMnemonic(KeyEvent.VK_M);
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 1;
        CONSTRAINTS.gridy = 9;
        CONSTRAINTS.gridwidth = 1;
        CONSTRAINTS.insets = new Insets(0, 1, 1, 1);
        group.add(manual);
        add(manual, CONSTRAINTS);

        manual.addActionListener(e -> {
            if (manual.isSelected()) {
                manualInterest.setEnabled(true);
            }
        });
    }


    private void setUpManualInterestTextField() {
        manualInterest = new JFormattedTextField();
        manualInterest.setEnabled(false);
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 2;
        CONSTRAINTS.gridy = 9;
        CONSTRAINTS.gridwidth = 2;
        CONSTRAINTS.insets = new Insets(0, 1, 1, 5);
        add(manualInterest, CONSTRAINTS);
    }

    private void setUpExitButton() {
        JButton exit = new JButton("Exit");
        exit.setMnemonic(KeyEvent.VK_E);
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 2;
        CONSTRAINTS.gridy = 10;
        CONSTRAINTS.gridwidth = 1;
        CONSTRAINTS.insets = new Insets(10, 1, 10, 1);
        add(exit, CONSTRAINTS);

        exit.addActionListener(e -> System.exit(0));

    }

    private void setUpOkButton() {
        JButton ok = new JButton("OK");
        ok.setMnemonic(KeyEvent.VK_O);
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 3;
        CONSTRAINTS.gridy = 10;
        CONSTRAINTS.gridwidth = 1;
        CONSTRAINTS.insets = new Insets(10, 1, 10, 5);
        add(ok, CONSTRAINTS);

        ok.addActionListener(e -> check());
    }

    private boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDate(JTextField dateTextField) {
        boolean dateIsCorrect = false;
        String date = dateTextField.getText();
        Date d = null;

        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            d = formatter.parse(date);
            assert d != null;
            if (!date.equals(formatter.format(d))) {
                d = null;
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        if (d != null) {
            dateIsCorrect = true;
        }

        return dateIsCorrect;

    }


    private double setInterest(JTextField field) {
        //Set the interest as the value inputted in the text field
        double interest = 0.0;
        if (field.isEnabled() && isDouble(field.getText())) {
            interest = Double.parseDouble(field.getText());
        }
        return interest;
    }


    private void check() {
        //Check if any of the text fields is filled incorrectly and return a message dialog if yes
        if (!isDate(newDate)) {
            JOptionPane.showMessageDialog(null, "Please enter a valid date for payment.");
        }
        if (!manual.isSelected() && !automatic.isSelected()) {
            JOptionPane.showMessageDialog(null, "Please select the interest type.");
        } else if (manual.isSelected() && !isDouble(manualInterest.getText())) {
            JOptionPane.showMessageDialog(null, "Please enter a valid value for the interest.");

        } else {
            renegotiate();
        }


    }

    private void renegotiate() {
        //If everything is filled correctly, open the window with the results

        Installments[] installments = new Installments[table.getModel().getRowCount()];
        double interest = this.setInterest(manualInterest);
        UpdatedValue updated = new UpdatedValue(installments, newDate.getText(), interest);
        updated.updateTable(table, installments);
        FirstResult gui = new FirstResult(updated.toString1(), updated.toString2(), updated);
        gui.setDefaultCloseOperation(HIDE_ON_CLOSE);
        gui.setVisible(true);
        gui.pack();
        gui.setResizable(false);
        gui.setLocationRelativeTo(null);
        gui.setTitle("Renegotiation");
        gui.setUpdatedValue(updated);

    }


    public void go() {
        try {
            FirstRenegotiation gui = new FirstRenegotiation();
            gui.setVisible(true);
            gui.pack();
            gui.setResizable(false);
            gui.setLocationRelativeTo(null);
            gui.setTitle("Renegotiation");
            gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
