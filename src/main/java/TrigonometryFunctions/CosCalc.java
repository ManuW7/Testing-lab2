package TrigonometryFunctions;

import Interfaces.CalculationFunction;

public class CosCalc implements CalculationFunction {

    private final CalculationFunction sin;
    public CosCalc(CalculationFunction sin) { this.sin = sin; }

    @Override
    public double calculate(double x){
        return sin.calculate(Math.PI / 2 - x);
    }
}
