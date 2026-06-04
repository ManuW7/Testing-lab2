package LogarithmicFunctions;

import Interfaces.CalculationFunction;

public class Log5Calc implements CalculationFunction {
    private final CalculationFunction ln;
    private final double ln5;
    public Log5Calc(CalculationFunction ln) {
        this.ln = ln;
        this.ln5 = ln.calculate(5); // константа
    }

    @Override
    public double calculate(double x) {
        return ln.calculate(x) / ln5;
    }
}