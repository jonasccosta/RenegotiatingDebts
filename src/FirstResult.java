/**
 * Class that shows the updated value of the debt if the client wants to pay in just one date
 *
 * @author: Jonas C. Costa
 */

import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class FirstResult extends Result {

    private final GridBagConstraints CONSTRAINTS = new GridBagConstraints();
    private UpdatedValue updatedValue;


    public FirstResult(String result1Text, String result2Text, UpdatedValue updatedValue) {
        super(result1Text, result2Text);
        super.fillTable(updatedValue);
        this.updatedValue = updatedValue;
        setUpInstallButton();

    }

    /**
     * Creates a button that allows the user to go to the installment screen
     */
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

}
