package unnecessary;

public class CalculatorModel {

    private int calculationvalue;

    public void addTwoNumbers (int firstNumber, int secondNumber){
        calculationvalue = firstNumber + secondNumber;
    }

    public int getCalculationvalue() {
        return calculationvalue;
    }
}
