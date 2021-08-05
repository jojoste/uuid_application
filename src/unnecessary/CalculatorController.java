package unnecessary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorController {

    protected CalculatorView theView;
    protected CalculatorModel theModel;

    public CalculatorController(CalculatorView theView, CalculatorModel theModel) {
        this.theView = theView;
        this.theModel = theModel;

        this.theView.addCalculationListener(new CalculateListener());

    }

    public class CalculateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int firstNumber, secondNumber = 0;

            try {
                firstNumber = theView.getFirstNumber();
                secondNumber = theView.getSecondNUmber();

                theModel.addTwoNumbers(firstNumber, secondNumber);

                theView.setCalcSolution(theModel.getCalculationvalue());
            } catch (Exception e1){
                System.out.println(e1);

                theView.displayErrorMessage("You need to enter 2 Integers");
            }
        }
    }
}


