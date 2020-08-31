import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Holds information and manipulates the new installments, in case the client wants to divide their debts again
 *
 * @author Jonas C. Costa
 */

public class NewInstallmentsGenerator {
    private DebtUpdater firstInstallment;
    private int numberOfInstallments;
    private int daysInstallments;
    private double finalValue;

    public NewInstallmentsGenerator(DebtUpdater firstInstallment, int numberOfInstallments, int daysInstallments) {
        this.firstInstallment = firstInstallment;
        this.numberOfInstallments = numberOfInstallments;
        this.daysInstallments = daysInstallments;
    }

    /**
     * Divides the new value into a specific number of installments
     * @param numberOfInstallments number of new installments
     * @return the value of each new intallment
     */
    public double firstValue(int numberOfInstallments) {
        return firstInstallment.decideNewValue() / numberOfInstallments;
    }

    public String toString() {
        return numberOfInstallments + " installments, costing R$" + firstInstallment.formatter(finalValue()) + " each, for the dates:";
    }

    /**
     * Updates, with interest, the value returned by the method firstValue(int numberOfInstallments) for the number of
     * dates and way of install specified by the user
     * @return array containing the new values
     */
    public double[] newValues() {
        double[] newValues = new double[numberOfInstallments];
        for (int i = 0; i < newValues.length; i++) {
            newValues[i] = firstValue(numberOfInstallments) + firstValue(numberOfInstallments) * i * daysInstallments * firstInstallment.decideInterest() / 30;
        }
        return newValues;
    }

    /**
     * Returns the final value of the debt by adding up all the elements of the array newValues()[]
     * @return the total value of the debt
     */
    public double finalValue() {
        for (int i = 0; i < newValues().length; i++) {
            finalValue += newValues()[i];
        }
        finalValue = Math.ceil(finalValue / numberOfInstallments);
        return finalValue;

    }

    /**
     * Return the dates in which the client will make the payments being that the difference between the dates will be the
     * value specified by the user
     * @return an array containing the new dates
     */
    public String[] setNewDates() {

        String[] newDates = new String[numberOfInstallments];
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            d = df.parse(firstInstallment.getNewDate());

        } catch (ParseException e) {
            e.getMessage();
        }

        Calendar myCal = Calendar.getInstance();

        newDates[0] = df.format(d);
        for (int i = 1; i < numberOfInstallments; i++) {
            assert d != null;
            myCal.setTime(d);
            myCal.add(Calendar.DAY_OF_MONTH, daysInstallments);
            d = myCal.getTime();
            newDates[i] = df.format(d);
        }

        return newDates;
    }

    /**
     * Put the elements of a array of string into a single string with correct punctuation and spacing
     * @param a array of strings
     * @return single string representing the elements of the array
     */
    public String toString2(String[] a) {

        StringBuilder b = new StringBuilder();
        for (int i = 0; i < a.length - 1; i++) {
            b.append(a[i]).append(", ");
        }
        b.append(a[a.length - 1]).append(".");

        return b.toString();
    }

    /**
     * Returns an array with the original value, original date, updated value and interest for each of the installments
     * first inputted by the user.
     * @return an array of installments containing the new information about the debt
     */
    public Installment[] newInst() {
        Installment[] inst = new Installment[firstInstallment.getInstallmentCount()];
        double difference = (finalValue() * numberOfInstallments - firstInstallment.decideNewValue()) / inst.length;
        for (int i = 0; i < inst.length; i++) {
            inst[i] = new Installment();
            inst[i].setValue(firstInstallment.getInstallments()[i].getValue());
            inst[i].setInterest(firstInstallment.getInstallments()[i].getInterest() + difference);
            inst[i].setNewValue(inst[i].getValue() + inst[i].getInterest());
            inst[i].setDate(firstInstallment.getInstallments()[i].getDate());
        }
        return inst;
    }

}



