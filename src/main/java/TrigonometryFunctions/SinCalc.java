package TrigonometryFunctions;

import Interfaces.CalculationFunction;

public class SinCalc implements CalculationFunction {

    private final double eps;
    public SinCalc(double eps) { this.eps = eps; }

    public double calculate(double x) {
        x = x % (2 * Math.PI);
        if (x > Math.PI) {
            x -= 2 * Math.PI;
        } else if (x < -Math.PI) {
            x += 2 * Math.PI;
        }

        double term = x;
        double sum = x;
        int n = 1;

        while (Math.abs(term) > this.eps) {
            term *= -1 * x * x / ((2 * n) * (2 * n + 1));
            sum += term;
            n++;
        }

        return sum;
    }
}