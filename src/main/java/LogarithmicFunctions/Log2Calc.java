package LogarithmicFunctions;

import Interfaces.CalculationFunction;

public class Log2Calc implements CalculationFunction {
    private final CalculationFunction ln;
    private final double ln2;
    public Log2Calc(CalculationFunction ln) {
        this.ln = ln;
        this.ln2 = ln.calculate(2);
    }

    @Override
    public double calculate(double x) {
        return ln.calculate(x) / ln2;
    }
}
