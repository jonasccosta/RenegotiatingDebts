/**
 * Class that shows the final result of the renegotiation
 *
 * @author Jonas C. Costa
 */
public class FinalResultScreenScreen extends ResultScreen {

    public FinalResultScreenScreen(String result1Label, String result2Label, Installment[] installments, DebtUpdater debtUpdater) {
        super(result1Label, result2Label);
        super.fillTable(installments, debtUpdater);


    }

}

