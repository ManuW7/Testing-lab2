package LogarithmicFunctions;

import Interfaces.CalculationFunction;

public class Log10Calc implements CalculationFunction {
    private final CalculationFunction ln;
    private final double ln10;
    public Log10Calc(CalculationFunction ln) {
        this.ln = ln;
        this.ln10 = ln.calculate(10);
    }

    @Override
    public double calculate(double x) {
        return ln.calculate(x) / ln10;
    }
}
