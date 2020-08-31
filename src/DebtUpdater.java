import javax.swing.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Class that manipulates the data inputted by the user, calculating the updated value for the debt
 *
 * @author Jonas C. Costa
 */

public class DebtUpdater {
    private Installment[] installments;
    private int installmentCount = 0;
    private String newDate;
    private double interest;

    public DebtUpdater(Installment[] installments, String newDate, double interest) {
        this.installments = installments;
        this.newDate = newDate;
        this.interest = interest;
    }

    /**
     * Add a new installment to the Installments[] array
     *
     * @param newInstallment the installment that will be added to the array
     */
    public void addInstallment(Installment newInstallment) {
        installments[installmentCount] = newInstallment;
        installmentCount++;
    }

    /**
     * Calculate the difference, in days, between the old date and the first date in which the client will make a payment
     *
     * @return the array of installments with the days parameter of each installment updated
     */
    public Installment[] update() {

        DateFormat dg = new SimpleDateFormat("dd/MM/yyyy");
        Date y = null;
        try {
            y = dg.parse(newDate);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Please insert a valid date.");
        }

        assert y != null;
        long b = y.getTime() / 86400000;

        for (int k = 0; k < installmentCount; k++) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date d = null;
            try {
                d = df.parse(installments[k].getDate());

            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Please insert a valid date.");
            }

            assert d != null;
            long a = d.getTime() / 86400000;
            long difference = (b - a);
            int daysDifference = (int) difference;

            installments[k].setDays(daysDifference);
        }

        return installments;

    }

    /**
     * @return the number of days of the oldest installment
     */
    public int maxDays() {
        int max = 0;
        for (int i = 0; i < installmentCount; i++) {
            if (update()[i].getDays() > max) {
                max = update()[i].getDays();
            }
        }
        return max;
    }

    /**
     * Calculates the total value of the debt by adding up the initial value for each of the installments
     *
     * @return the total value of the debt
     */
    public double totalValue() {

        double totalValue = 0.0;
        for (int i = 0; i < installmentCount; i++) {
            totalValue += update()[i].getValue();
        }
        return totalValue;
    }

    /**
     * If the automatic radio button was selected, it calculates the interest automatically based on the algorithm:
     * If the debt is less than 92 days old, interest is 5.0%
     * Else, if the total value of the debt is less than R$3000,00 and the debt is more than 1095 days old, interest is 1.5%
     * Else, if the total value of the debt is greater than R$3000,00 and the debt is more than 1095 days old, interest is 1.0%
     * Else, if the total value of the debt is less than or equals to R$3000,00, interest is 4.5-0.5*((days/182.5)-1)
     * Else, if the total value of the debt is greater than R$3000,00, interest is 4.0-0.5*((days/182.5)-1)
     * <p>
     * If the interest was inputted manually, then it returns that interest
     *
     * @return the interest for the renegotiation
     */
    public double decideInterest() {
        if (interest == 0.0) {
            if (maxDays() < 92) interest = 5;
            else if (totalValue() < 3000 && maxDays() > 1095) interest = 1.5;
            else if (totalValue() > 3000 && maxDays() > 1095) interest = 1;
            else if (totalValue() <= 3000) interest = (4.5 - 0.5 * ((Math.ceil(maxDays() / 182.5)) - 1));
            else if (totalValue() > 3000) interest = (4.0 - 0.5 * ((Math.ceil(maxDays() / 182.5)) - 1));
        }
        return interest / 100;
    }

    /**
     * @return the updated value of the debt
     */
    public double decideNewValue() {

        double newTotalValue = 0.0;
        for (int i = 0; i < installmentCount; i++) {
            //Updates the value and interest for each installment
            update()[i].setInterest(update()[i].getValue() * update()[i].getDays() * decideInterest() / 30);
            update()[i].setNewValue(update()[i].getValue() + update()[i].getInterest());
            //Add all new values
            newTotalValue += update()[i].getNewValue();
        }
        return newTotalValue;
    }

    /**
     * Fills a table with the updated values of the debt
     *
     * @param table table in which the data with be entered
     * @param inst  holds the data
     */
    public void updateTable(JTable table, Installment[] inst) {
        for (int i = 0; i < table.getModel().getRowCount(); i++) {
            if (table.getModel().getValueAt(i, 0) != null && table.getModel().getValueAt(i, 0) != null) {
                inst[i] = new Installment();
                String c = (String) table.getModel().getValueAt(i, 0);
                try {
                    double d = Double.parseDouble(c);
                    inst[i].setValue(d);
                } catch (NumberFormatException excep) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid value!");
                }
                String f = (String) table.getModel().getValueAt(i, 1);
                inst[i].setDate(f);
                this.addInstallment(inst[i]);
            }
        }
    }

    /**
     * @return the value for the interest based on the difference between the new and old values
     */
    public double interestValue() {
        return this.decideNewValue() - this.totalValue();
    }

    /**
     * Returns a numeric string with two decimal places
     *
     * @param a double that will be formatted
     * @return numeric string formatted to two decimal places
     */
    public String formatter(double a) {
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        return numberFormat.format(a);
    }

    /**
     * Rounds up a value
     *
     * @param d value that will be rounded
     * @return rounded value
     */
    public double round(double d) {
        return Math.ceil(d);
    }

    /**
     * Calculates the difference between the rounded value and non-rounded value
     *
     * @return the difference between the rounded value and non-rounded value
     */
    public double setDifference() {
        return Math.ceil(decideNewValue()) - decideNewValue();
    }

    /**
     * Updates the interest with the difference between the rounded value and non-rounded value
     * This needs to be done to account for rounding, since all new values need to be rounded up
     *
     * @return the new interest value
     */
    public double newInterestValue() {
        return interestValue() + this.setDifference();
    }

    /**
     * @return string with the first line of the result
     */
    public String toString1() {
        return "The updated value is R$" + this.formatter(this.round(this.decideNewValue())) + " for payment on ";
    }

    /**
     * @return string with the second line of the result
     */
    public String toString2() {
        return newDate + ". Interest value is R$" + this.formatter(this.newInterestValue()) + " (" + this.formatter(this.decideInterest() * 100) + "%).";
    }

    public int getInstallmentCount() {
        return installmentCount;
    }

    public String getNewDate() {
        return newDate;
    }

    public Installment[] getInstallments() {
        return installments;
    }

}

