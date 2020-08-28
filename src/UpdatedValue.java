import javax.swing.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class UpdatedValue {
    private Installments[] installments;
    private int installmentCount = 0;
    private String newDate;
    private double interest, difference;

    public void addInstallment(Installments newInstallment){
        //Add a new installment to the Installments[] array
        installments[installmentCount] = newInstallment;
        installmentCount++;
    }

    public Installments[] update() {
        /*Calculate the difference, in days, between the old date and the first date
          that the client wants to pay*/
        DateFormat dg = new SimpleDateFormat("dd/MM/yyyy");
        Date y = null;
        try {
            y = dg.parse(newDate);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null,"Please insert a valid date.");
        }

        long b = y.getTime() / 86400000;

        for (int k = 0; k < installmentCount; k++) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date d = null;
            try {
                d = df.parse(installments[k].getDate());

            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null,"Please insert a valid date.");
            }

            long a = d.getTime() / 86400000;
            long difference = (b - a);
            int daysDifference = (int) difference;

            installments[k].setDays(daysDifference);
        }

        return installments;

    }

    public int maxDays(){
        //Returns the number of days of the oldest installment
        int max = 0;
        for(int i = 0; i < installmentCount; i++){
            if(update()[i].getDays() > max){
                max = update()[i].getDays();
            }
        }
        return max;
    }

    public double totalValue(){
        /*Calculates the total value of the debt by adding up the initial value
        for each of the installments*/
        double totalValue = 0.0;
        for(int i = 0; i < installmentCount; i++){
            totalValue += update()[i].getValue();
        }
        return totalValue;
    }

    public double decideInterest(){
        /*Return the interest based on the total value of the debt and the value returned by the
        method maxDays()*/
        if (interest == 0.0){
            if (maxDays() < 92) interest = 5;
            else if (totalValue() < 3000 && maxDays() > 1095) interest = 1.5;
            else if (totalValue() > 3000 && maxDays() > 1095) interest = 1;
            else if (totalValue() <= 3000) interest = (4.5-0.5*((Math.ceil(maxDays()/182.5))-1));
            else if (totalValue() > 3000) interest = (4.0-0.5*((Math.ceil(maxDays()/182.5))-1));
            return interest/100;
        }
        else return interest/100;
    }

    public double decideNewValue(){
        //Return the updated value of the debt
        double newTotalValue = 0.0;
        for(int i = 0; i < installmentCount; i++){
            //Updates the value and interest for each installment
            update()[i].setInterest(update()[i].getValue()*update()[i].getDays()*decideInterest()/30);
            update()[i].setNewValue(update()[i].getValue() + update()[i].getInterest());
            //Add all new values
            newTotalValue += update()[i].getNewValue();
        }
        return newTotalValue;
    }

    public double interestValue(){
        /*Return the value for the interest based on the difference
        between the new and old values*/
        double interestValue = this.decideNewValue() - this.totalValue();
        return interestValue;
    }

    public String formatter(double a){
        //Returns a numeric string with two decimal places
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        return numberFormat.format(a);
    }

    public double round(double d){
        //Round up a value
        return Math.ceil(d);
    }

    public double setDifference(){
        //Calculates the difference between the rounded value and non-rounded value
        difference = Math.ceil(decideNewValue()) - decideNewValue();
        return difference;
    }

    public double newInterestValue(){
        //Updates the interest with the difference between the rounded value and non-rounded value
        double d = interestValue()+ this.setDifference();
        return d;

    }

    public String toString1(){
        String one = "The updated value is R$" + this.formatter(this.round(this.decideNewValue())) + " for payment on " ;
        return one;
    }

    public String toString2(){
        String two = newDate +  ". Interest value is R$"+ this.formatter(this.newInterestValue()) + " ("+ this.formatter(this.decideInterest()*100) +"%).";
        return two;
    }

    public int getInstallmentCount() {
        return installmentCount;
    }

    public String getNewDate() {
        return newDate;
    }

    public Installments[] getInstallments() {
        return installments;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public UpdatedValue(Installments[] installments, String newDate) {
        this.installments = installments;
        this.newDate = newDate;
    }
}

