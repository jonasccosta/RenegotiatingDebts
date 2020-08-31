/**
 * Represents a single installment
 *
 * @author Jonas C. Costa
 */

import java.text.DecimalFormat;

public class Installments {
    private double value, newValue, interest;
    private String date;
    private int days;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getNewValue() {
        return newValue;
    }

    public void setNewValue(double newValue) {
        this.newValue = newValue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public Installments() {
    }

    public String toString() {
        return "Old value: R$ " + this.getValue() + "   New value: R$ " + this.formatter(this.getNewValue()) + "   Interest: R$ " + this.formatter(this.getInterest()) + "   Old date: " + this.getDate();
    }

    public String formatter(double a) {
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        return numberFormat.format(a);
    }

}

