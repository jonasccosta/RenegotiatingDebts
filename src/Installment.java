import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Installment extends JFrame{
    private JLabel numberInstallments, wayOfInstall;
    private JTextField nInstallments, nOther;
    private JRadioButton fifteen, thirty, other;
    private ButtonGroup group;
    private JButton exit, ok, goBack;
    private UpdatedValue k;
    private NewInstallments newInstallments;

    public Installment(){
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        numberInstallments = new JLabel("Nº of new installments");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.insets = new Insets(10, 5, 1, 1);
        add(numberInstallments, c);

        nInstallments = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 2;
        c.insets = new Insets(10, 1, 1, 5);
        add(nInstallments, c);

        wayOfInstall = new JLabel("Way of Install");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.insets = new Insets(10, 5, 1, 1);
        add(wayOfInstall, c);

        fifteen = new JRadioButton("15 days");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.insets = new Insets(1, 5, 1, 1);
        add(fifteen, c);

        disableManualInterest ev = new disableManualInterest();
        fifteen.addActionListener(ev);

        thirty = new JRadioButton("30 days");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 4;
        c.gridwidth = 2;
        c.insets = new Insets(1, 1, 1, 5);
        add(thirty, c);

        thirty.addActionListener(ev);

        other = new JRadioButton("Other (days)");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.insets = new Insets(1, 5, 1, 1);
        add(other, c);

        enableManualInterest e = new enableManualInterest();
        other.addActionListener(e);

        nOther = new JTextField();
        nOther.setEnabled(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 5;
        c.gridwidth = 2;
        c.insets = new Insets(1, 1, 1, 5);
        add(nOther, c);

        group = new ButtonGroup();
        group.add(fifteen);
        group.add(thirty);
        group.add(other);

        goBack = new JButton("Return");
        goBack.setMnemonic(KeyEvent.VK_R);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 6;
        c.gridwidth = 1;
        c.insets = new Insets(10, 1, 10, 1);
        add(goBack, c);

        goBack gb = new goBack();
        goBack.addActionListener(gb);

        exit = new JButton("Exit");
        exit.setMnemonic(KeyEvent.VK_E);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 1;
        c.insets = new Insets(10, 1, 10, 1);
        add(exit, c);

        exit ex = new exit();
        exit.addActionListener(ex);

        ok = new JButton("OK");
        ok.setMnemonic(KeyEvent.VK_O);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 6;
        c.gridwidth = 1;
        c.insets = new Insets(10, 1, 10, 5);
        add(ok, c);

        ok o = new ok();
        ok.addActionListener(o);

    }

    public class goBack implements ActionListener{
        //Go back to the previous window
        public void actionPerformed(ActionEvent e) {
            Installment.this.setVisible(false);
        }
    }

    public class exit implements ActionListener{
        //Exits the program
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public class enableManualInterest implements ActionListener{
        //Enable the text field if the radio button Other is selected
        public void actionPerformed(ActionEvent e) {
            if(other.isSelected()){
                nOther.setEnabled(true);
            }
        }
    }

    public class disableManualInterest implements ActionListener{
        //Disable the text field if the radio button Other is not selected
        public void actionPerformed(ActionEvent e) {
            if(fifteen.isSelected() || thirty.isSelected()){
                nOther.setEnabled(false);
            }
        }
    }

    public class ok implements ActionListener{
        //Check if a string can be parsed into an int
        public boolean isInteger( String input ) {
            try {
                Integer.parseInt( input );
                return true;
            }
            catch( NumberFormatException e ) {
                return false;
            }
        }

        public NewInstallments createNewInstallments(){
            //Creates a NewInstallments class with the values inputted by the user
            int days = 0;
            int nInstall = Integer.parseInt(nInstallments.getText());;
            if(fifteen.isSelected()){
                days = 15;
            }

            else if(thirty.isSelected()){
                days = 30;
            }

            else {
                days = Integer.parseInt(nOther.getText());
            }

            newInstallments = new NewInstallments(k,nInstall, days);

            return newInstallments;
        }

        public void actionPerformed(ActionEvent e) {
            //Check if any of the text fields is filled incorrectly and return a message dialog if yes
            if(!isInteger(nInstallments.getText())){
                JOptionPane.showMessageDialog(null, "Please enter the number of installments.");
            }

            if(group.getSelection() == null){
                JOptionPane.showMessageDialog(null, "Please select the way of install.");
            }

            else if(other.isSelected() && !isInteger(nOther.getText())){
                JOptionPane.showMessageDialog(null, "Please enter a valid way of install");
            }

            else{
                //If everything is filled correctly, open the window with the results
                FinalResult gui = new FinalResult();
                gui.setDefaultCloseOperation(HIDE_ON_CLOSE);
                gui.setVisible(true);
                gui.setLocationRelativeTo(null);
                gui.setSize(330, 245);
                gui.setResizable(false);
                gui.setTitle("Renegotiation");
                gui.setA(k);
                gui.getResult1().setText(this.createNewInstallments().toString());
                gui.getResult2().setText(this.createNewInstallments().toString2(this.createNewInstallments().setNewDates()));

                for(int i = 0; i < this.createNewInstallments().newInst().length; i++){
                    //Put the information from an array of installments into a table
                    if(this.createNewInstallments().newInst() != null){
                        gui.getTableResults().getModel().setValueAt("R$ " + k.formatter(this.createNewInstallments().newInst()[i].getValue()), i, 0);
                        gui.getTableResults().getModel().setValueAt(this.createNewInstallments().newInst()[i].getDate(), i, 1);
                        gui.getTableResults().getModel().setValueAt("R$ " + k.formatter(this.createNewInstallments().newInst()[i].getInterest()),i, 2);
                        gui.getTableResults().getModel().setValueAt("R$ " + k.formatter(this.createNewInstallments().newInst()[i].getNewValue()), i, 3);
                    }
                }
            }
        }
    }

    public void setK(UpdatedValue k) {
        this.k = k;
    }
}
