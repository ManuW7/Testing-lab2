package MainFunctions;

import Interfaces.CalculationFunction;

public class SystemFunction implements CalculationFunction {
    private final CalculationFunction trigonometryFunction;
    private final CalculationFunction logarithmicFunction;

    public SystemFunction(CalculationFunction trigonometryBranch, CalculationFunction logarithmicBranch) {
        if (trigonometryBranch == null || logarithmicBranch == null) {
            throw new IllegalArgumentException("system branches must not be null");
        }
        this.trigonometryFunction = trigonometryBranch;
        this.logarithmicFunction = logarithmicBranch;
    }

    @Override
    public double calculate(double x) {
        if (!Double.isFinite(x)) {
            throw new IllegalArgumentException("x must be finite");
        }
        if (x <= 0.0) {
            return trigonometryFunction.calculate(x);
        }
        return logarithmicFunction.calculate(x);
    }
}
