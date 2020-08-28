import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewInstallments {
    private UpdatedValue firstInstallment;
    private int numberOfInstallments;
    private int daysInstallments;
    private double[] newValues;
    private String[] newDates;
    private double finalValue;

    public NewInstallments(UpdatedValue firstInstallment, int numberOfInstallments, int daysInstallments) {
        this.firstInstallment = firstInstallment;
        this.numberOfInstallments = numberOfInstallments;
        this.daysInstallments = daysInstallments;
    }

    public double firstValue(int numberOfInstallments) {
        //Divides the new value into a specific number of installments
        double newValue = firstInstallment.decideNewValue() / numberOfInstallments;
        return newValue;
    }


    public String toString() {
        return numberOfInstallments + " installments, costing R$" + firstInstallment.formatter(finalValue()) + " each, for the dates:";
    }

    public double[] newValues() {
        /*Update, with interest, the value returned by the method firstValue(int numberOfInstallments)
        for the number of dates and way of install specified by the client*/
        newValues = new double[numberOfInstallments];
        for (int i = 0; i < newValues.length; i++) {
            newValues[i] = firstValue(numberOfInstallments) + firstValue(numberOfInstallments) * i * daysInstallments * firstInstallment.decideInterest() / 30;
        }
        return newValues;
    }

    public double finalValue() {
        //Return the final value of the debt by adding up all the elements of the array newValues()[]
        for (int i = 0; i < newValues().length; i++) {
            finalValue += newValues()[i];
        }
        finalValue = Math.ceil(finalValue / numberOfInstallments);
        return finalValue;

    }

    public String[] setNewDates() {
        /*Return the dates in which the client will make the payments
            being that the difference between the dates will be the
            value specified by the client*/
        newDates = new String[numberOfInstallments];
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
            myCal.setTime(d);
            myCal.add(Calendar.DAY_OF_MONTH, daysInstallments);
            d = myCal.getTime();
            newDates[i] = df.format(d);
        }

        return newDates;
    }

    public String toString2(String[] a) {
        /*Put the elements of a array into a single string
        with correct punctuation and spacing*/
        String b = "";
        for (int i = 0; i < a.length - 1; i++) {
            b += a[i] + ", ";
        }
        b += a[a.length - 1] + ".";

        return b;
    }

    public Installments[] newInst() {
        /*Returns an array with the original value, original date, updated value
        and interest for each of the installments first inputted by the user*/
        Installments[] inst = new Installments[firstInstallment.getInstallmentCount()];
        double difference = (finalValue() * numberOfInstallments - firstInstallment.decideNewValue()) / inst.length;
        for (int i = 0; i < inst.length; i++) {
            inst[i] = new Installments();
            inst[i].setValue(firstInstallment.getInstallments()[i].getValue());
            inst[i].setInterest(firstInstallment.getInstallments()[i].getInterest() + difference);
            inst[i].setNewValue(inst[i].getValue() + inst[i].getInterest());
            inst[i].setDate(firstInstallment.getInstallments()[i].getDate());
        }
        return inst;
    }

}



