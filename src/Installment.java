import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Installment extends JFrame {
    private final GridBagConstraints CONSTRAINTS = new GridBagConstraints();
    private JTextField nInstallments, nOther;
    private JRadioButton fifteen, thirty, other;
    private ButtonGroup group = new ButtonGroup();
    private UpdatedValue updatedValue;

    public Installment() {
        setLayout(new GridBagLayout());
        setNumberOfInstallmentsLabel();
        setUpNumberOfInstallmentsTextField();
        setUpWayOfInstallLabel();
        setUpFifteenRadioButton();
        setUpThirtyRadioButton();
        setUpOtherRadioButton();
        setUpOtherTextField();
        setUpReturnButton();
        setUpExitButton();
        setUpOkButton();

    }

    private void setNumberOfInstallmentsLabel() {
        JLabel numberInstallments = new JLabel("Nº of new installments");
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 0;
        CONSTRAINTS.gridy = 1;
        CONSTRAINTS.gridwidth = 2;
        CONSTRAINTS.insets = new Insets(10, 5, 1, 1);
        add(numberInstallments, CONSTRAINTS);
    }

    private void setUpNumberOfInstallmentsTextField() {
        nInstallments = new JTextField();
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 2;
        CONSTRAINTS.gridy = 1;
        CONSTRAINTS.gridwidth = 2;
        CONSTRAINTS.insets = new Insets(10, 1, 1, 5);
        add(nInstallments, CONSTRAINTS);
    }

    private void setUpWayOfInstallLabel() {
        JLabel wayOfInstall = new JLabel("Way of Install");
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 0;
        CONSTRAINTS.gridy = 2;
        CONSTRAINTS.gridwidth = 2;
        CONSTRAINTS.insets = new Insets(10, 5, 1, 1);
        add(wayOfInstall, CONSTRAINTS);
    }

    private void setUpFifteenRadioButton() {
        fifteen = new JRadioButton("15 days");
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 0;
        CONSTRAINTS.gridy = 4;
        CONSTRAINTS.gridwidth = 2;
        CONSTRAINTS.insets = new Insets(1, 5, 1, 1);
        add(fifteen, CONSTRAINTS);
        group.add(fifteen);
        fifteen.addActionListener(e -> {
            if (fifteen.isSelected() || thirty.isSelected()) {
                nOther.setEnabled(false);
            }
        });

    }

    private void setUpThirtyRadioButton() {
        thirty = new JRadioButton("30 days");
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 2;
        CONSTRAINTS.gridy = 4;
        CONSTRAINTS.gridwidth = 2;
        CONSTRAINTS.insets = new Insets(1, 1, 1, 5);
        add(thirty, CONSTRAINTS);
        group.add(thirty);

        thirty.addActionListener(e -> {
            if (fifteen.isSelected() || thirty.isSelected()) {
                nOther.setEnabled(false);
            }
        });

    }

    private void setUpOtherRadioButton() {
        other = new JRadioButton("Other (days)");
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 0;
        CONSTRAINTS.gridy = 5;
        CONSTRAINTS.gridwidth = 2;
        CONSTRAINTS.insets = new Insets(1, 5, 1, 1);
        add(other, CONSTRAINTS);
        group.add(other);
        other.addActionListener(e -> {
            if (other.isSelected()) {
                nOther.setEnabled(true);
            }
        });

    }

    private void setUpOtherTextField() {
        nOther = new JTextField();
        nOther.setEnabled(false);
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 2;
        CONSTRAINTS.gridy = 5;
        CONSTRAINTS.gridwidth = 2;
        CONSTRAINTS.insets = new Insets(1, 1, 1, 5);
        add(nOther, CONSTRAINTS);
    }

    private void setUpReturnButton() {

        JButton returnButton = new JButton("Return");
        returnButton.setMnemonic(KeyEvent.VK_R);
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 2;
        CONSTRAINTS.gridy = 6;
        CONSTRAINTS.gridwidth = 1;
        CONSTRAINTS.insets = new Insets(10, 1, 10, 1);
        add(returnButton, CONSTRAINTS);
        returnButton.addActionListener(e -> Installment.this.setVisible(false));

    }

    private void setUpExitButton() {

        JButton exit = new JButton("Exit");
        exit.setMnemonic(KeyEvent.VK_E);
        CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
        CONSTRAINTS.gridx = 1;
        CONSTRAINTS.gridy = 6;
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
        CONSTRAINTS.gridy = 6;
        CONSTRAINTS.gridwidth = 1;
        CONSTRAINTS.insets = new Insets(10, 1, 10, 5);
        add(ok, CONSTRAINTS);
        ok.addActionListener(e -> check());

    }

    private boolean isInteger(String input) {
        //Check if a string can be parsed into an int
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public NewInstallments createNewInstallments() {
        //Creates a NewInstallments class with the values inputted by the user
        int days;
        int nInstall = Integer.parseInt(nInstallments.getText());

        if (fifteen.isSelected()) {
            days = 15;
        } else if (thirty.isSelected()) {
            days = 30;
        } else {
            days = Integer.parseInt(nOther.getText());
        }

        return new NewInstallments(updatedValue, nInstall, days);
    }

    public void check() {
        //Check if any of the text fields is filled incorrectly and return a message dialog if yes
        if (!isInteger(nInstallments.getText())) {
            JOptionPane.showMessageDialog(null, "Please enter the number of installments.");
        }

        if (group.getSelection() == null) {
            JOptionPane.showMessageDialog(null, "Please select the way of install.");
        } else if (other.isSelected() && !isInteger(nOther.getText())) {
            JOptionPane.showMessageDialog(null, "Please enter a valid way of install");
        } else {
            reinstall();
        }
    }

    private void reinstall() {
        NewInstallments newInstallments = this.createNewInstallments();
        FinalResult gui = new FinalResult(newInstallments.toString(), newInstallments.toString2(newInstallments.setNewDates()), newInstallments.newInst(), updatedValue);
        gui.setDefaultCloseOperation(HIDE_ON_CLOSE);
        gui.setVisible(true);
        gui.setLocationRelativeTo(null);
        gui.setSize(330, 245);
        gui.setResizable(false);
        gui.setTitle("Renegotiation");

    }


    public void setUpdatedValue(UpdatedValue updatedValue) {
        this.updatedValue = updatedValue;
    }
}
