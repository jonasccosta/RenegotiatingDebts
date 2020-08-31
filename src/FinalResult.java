public class FinalResult extends Result{

    public FinalResult(String result1Label, String result2Label, Installments[] installments, UpdatedValue updatedValue) {
        super(result1Label, result2Label, updatedValue);
        super.fillTable(installments, updatedValue);


    }

}

