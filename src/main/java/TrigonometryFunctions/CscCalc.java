package TrigonometryFunctions;

import Interfaces.CalculationFunction;

public class CscCalc implements CalculationFunction {

    private static final double CLOSE_TO_ZERO = 1.0E-12;

    private final CalculationFunction sin;

    public CscCalc(CalculationFunction sin){
        this.sin = sin;
    }

    @Override
    public double calculate(double x){
        double sinValue = this.sin.calculate(x);
        if (sinValue <= CLOSE_TO_ZERO ){
            throw new IllegalArgumentException("sin(x) too close to zero");
        }

        return 1.0 / sinValue;
    }
}
