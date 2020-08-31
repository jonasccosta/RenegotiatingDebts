/**
 * Class that shows the final result of the renegotiation
 *
 * @author Jonas C. Costa
 */
public class FinalResult extends Result {

    public FinalResult(String result1Label, String result2Label, Installments[] installments, UpdatedValue updatedValue) {
        super(result1Label, result2Label);
        super.fillTable(installments, updatedValue);


    }

}

