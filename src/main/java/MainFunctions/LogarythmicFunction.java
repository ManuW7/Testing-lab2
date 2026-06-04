package MainFunctions;


import Interfaces.CalculationFunction;

public class LogarythmicFunction implements CalculationFunction {
    private static final double CLOSE_TO_ZERO = 1.0E-12;

    private final CalculationFunction ln;
    private final CalculationFunction log2;
    private final CalculationFunction log5;
    private final CalculationFunction log10;

    public LogarythmicFunction(
            CalculationFunction ln,
            CalculationFunction log2,
            CalculationFunction log5,
            CalculationFunction log10
    ) {
        if (ln == null || log2 == null || log5 == null || log10 == null) {
            throw new IllegalArgumentException("logarithmic dependencies must not be null");
        }
        this.ln = ln;
        this.log2 = log2;
        this.log5 = log5;
        this.log10 = log10;
    }

    @Override
    public double calculate(double x) {
        if (!Double.isFinite(x)) {
            throw new IllegalArgumentException("x must be finite");
        }
        if (x <= 0.0) {
            throw new IllegalArgumentException("Logarythms require x > 0");
        }

        double lnValue = ln.calculate(x);
        double log2Value = log2.calculate(x);
        double log5Value = log5.calculate(x);
        double log10Value = log10.calculate(x);

        double numerator = (((log5Value * log10Value) + log2Value) + lnValue) * log5Value;
        double denominator = Math.pow(log10Value, 3);

        return divide(numerator, denominator, "log_10(x) ^ 3");
    }

    private double divide(double dividend, double divisor, String divisorLabel) {
        if (Math.abs(divisor) <= CLOSE_TO_ZERO) {
            throw new IllegalArgumentException("division by zero in " + divisorLabel);
        }
        return dividend / divisor;
    }
}
