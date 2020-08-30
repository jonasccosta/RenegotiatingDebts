import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FirstRenegotiation extends JFrame {
    private JTextField manualInterest, newDate;
    private ButtonGroup group = new ButtonGroup();
    private JRadioButton automatic, manual;
    private JTable table;
    private DefaultTableModel model;

    public FirstRenegotiation() throws HeadlessException {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setUpTable(c);
        setUpAddRowsButton(c);
        setUpDate1stPaymentLabel(c);
        setUpNewDate(c);
        setUpInterestLabel(c);
        setUpAutomaticInterestRadioButton(c);
        setUpManualInterestRadioButton(c);
        setUpManualInterestTextField(c);
        setUpExitButton(c);
        setUpOkButton(c);
    }


    private void setUpTable(GridBagConstraints c) {
        String[] columnNames = {"Value (R$)", "Due Date (dd/mm/yyyy)"};
        String[][] data = new String[5][2];
        table = new JTable(new DefaultTableModel(data, columnNames));
        table.setPreferredScrollableViewportSize(new Dimension(300, 80));
        model = (DefaultTableModel) table.getModel();
        table.getTableHeader().setReorderingAllowed(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 4;
        c.insets = new Insets(10, 5, 1, 5);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, c);
    }

    private void setUpAddRowsButton(GridBagConstraints c) {
        JButton addRows = new JButton("Add rows");
        addRows.setMnemonic(KeyEvent.VK_R);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 6;
        c.gridwidth = 1;
        c.insets = new Insets(1, 1, 1, 5);
        add(addRows, c);

        addRows.addActionListener(e -> model.addRow(new String[2]));
    }

    private void setUpDate1stPaymentLabel(GridBagConstraints c) {
        JLabel date1stPayment = new JLabel("First date for payment (dd/mm/yyyy):");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        c.insets = new Insets(10, 5, 1, 1);
        add(date1stPayment, c);
    }

    private void setUpNewDate(GridBagConstraints c) {
        newDate = new JTextField();
        newDate.setHorizontalAlignment(SwingConstants.RIGHT);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 7;
        c.gridwidth = 2;
        c.insets = new Insets(10, 1, 0, 5);
        add(newDate, c);
    }

    private void setUpInterestLabel(GridBagConstraints c) {
        JLabel interest = new JLabel("Interest");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 2;
        c.insets = new Insets(10, 5, 0, 1);
        add(interest, c);
    }

    private void setUpAutomaticInterestRadioButton(GridBagConstraints c) {
        automatic = new JRadioButton("Automatic");
        automatic.setMnemonic(KeyEvent.VK_A);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 1;
        c.insets = new Insets(0, 5, 1, 1);

        group.add(automatic);
        add(automatic, c);

        automatic.addActionListener(e -> {
            if (automatic.isSelected()) {
                manualInterest.setEnabled(false);
            }
        });
    }

    private void setUpManualInterestRadioButton(GridBagConstraints c) {
        manual = new JRadioButton("Manual (%)");
        manual.setMnemonic(KeyEvent.VK_M);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 9;
        c.gridwidth = 1;
        c.insets = new Insets(0, 1, 1, 1);
        group.add(manual);
        add(manual, c);

        manual.addActionListener(e -> {
            if (manual.isSelected()) {
                manualInterest.setEnabled(true);
            }
        });
    }


    private void setUpManualInterestTextField(GridBagConstraints c) {
        manualInterest = new JFormattedTextField();
        manualInterest.setEnabled(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 9;
        c.gridwidth = 2;
        c.insets = new Insets(0, 1, 1, 5);
        add(manualInterest, c);
    }

    private void setUpExitButton(GridBagConstraints c) {
        JButton exit = new JButton("Exit");
        exit.setMnemonic(KeyEvent.VK_E);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 10;
        c.gridwidth = 1;
        c.insets = new Insets(10, 1, 10, 1);
        add(exit, c);

        exit.addActionListener(e -> System.exit(0));

    }

    private void setUpOkButton(GridBagConstraints c) {
        JButton ok = new JButton("OK");
        ok.setMnemonic(KeyEvent.VK_O);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 10;
        c.gridwidth = 1;
        c.insets = new Insets(10, 1, 10, 5);
        add(ok, c);

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

    private void renegotiate(){
        //If everything is filled correctly, open the window with the results

        Installments[] installments = new Installments[table.getModel().getRowCount()];
        double interest = this.setInterest(manualInterest);
        UpdatedValue updated = new UpdatedValue(installments, newDate.getText(), interest);
        updated.update(table, installments);
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
        FirstRenegotiation gui = null;
        try {
            gui = new FirstRenegotiation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        gui.setVisible(true);
        gui.pack();
        gui.setResizable(false);
        gui.setLocationRelativeTo(null);
        gui.setTitle("Renegotiation");
        gui.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

}
