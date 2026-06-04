package TrigonometryFunctions;

import Interfaces.CalculationFunction;

public class TanCalc implements CalculationFunction {

    private static final double CLOSE_TO_ZERO = 1.0E-12;

    private final CalculationFunction sin;
    private final CalculationFunction cos;

    public TanCalc(CalculationFunction sin, CalculationFunction cos){
        this.sin = sin;
        this.cos = cos;

    }

    @Override
    public double calculate(double x){
        double cosValue = this.cos.calculate(x);
        if (cosValue <= CLOSE_TO_ZERO ){
            throw new IllegalArgumentException("cos(x) too close to zero");
        }

        return this.sin.calculate(x) / cosValue;
    }
}
