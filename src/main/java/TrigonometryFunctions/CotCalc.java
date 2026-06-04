package TrigonometryFunctions;

import Interfaces.CalculationFunction;

public class CotCalc implements CalculationFunction {

    private static final double CLOSE_TO_ZERO = 1.0E-12;

    private final CalculationFunction sin;
    private final CalculationFunction cos;

    public CotCalc(CalculationFunction sin, CalculationFunction cos){
        this.sin = sin;
        this.cos = cos;

    }

    @Override
    public double calculate(double x){
        double sinValue = this.sin.calculate(x);
        if (sinValue <= CLOSE_TO_ZERO ){
            throw new IllegalArgumentException("sin(x) too close to zero");
        }

        return this.cos.calculate(x) / sinValue;
    }
}
