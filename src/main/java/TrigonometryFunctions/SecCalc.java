package TrigonometryFunctions;

import Interfaces.CalculationFunction;

public class SecCalc implements CalculationFunction {

    private static final double CLOSE_TO_ZERO = 1.0E-12;

    private final CalculationFunction cos;

    public SecCalc( CalculationFunction cos){
        this.cos = cos;
    }

    @Override
    public double calculate(double x){
        double cosValue = this.cos.calculate(x);
        if (cosValue <= CLOSE_TO_ZERO ){
            throw new IllegalArgumentException("cos(x) too close to zero");
        }

        return 1.0 / cosValue;
    }
}
