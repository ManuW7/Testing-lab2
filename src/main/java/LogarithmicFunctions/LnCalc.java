package LogarithmicFunctions;

import Interfaces.CalculationFunction;

// Ln.java
public class LnCalc implements CalculationFunction {
    private final double eps;
    public LnCalc(double eps) { this.eps = eps; }

    @Override
    public double calculate(double x) {
        if (x <= 0) throw new IllegalArgumentException("x must be > 0");
        double z = (x - 1) / (x + 1);
        double term = z;
        double result = 0.0;
        int denominator = 1;

        while (Math.abs(term) > eps) {
            result += term / denominator;

            term *= z * z;
            denominator += 2;
        }

        return 2 * result;
    }
}