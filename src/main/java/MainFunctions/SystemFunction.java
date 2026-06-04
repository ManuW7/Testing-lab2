package MainFunctions;

import Interfaces.CalculationFunction;

public class SystemFunction implements CalculationFunction {
    private final CalculationFunction trigonometryBranch;
    private final CalculationFunction logarithmicBranch;

    public SystemFunction(CalculationFunction trigonometryBranch, CalculationFunction logarithmicBranch) {
        if (trigonometryBranch == null || logarithmicBranch == null) {
            throw new IllegalArgumentException("system branches must not be null");
        }
        this.trigonometryBranch = trigonometryBranch;
        this.logarithmicBranch = logarithmicBranch;
    }

    @Override
    public double calculate(double x) {
        if (!Double.isFinite(x)) {
            throw new IllegalArgumentException("x must be finite");
        }
        if (x <= 0.0) {
            return trigonometryBranch.calculate(x);
        }
        return logarithmicBranch.calculate(x);
    }
}
