package MainFunctions;

import Interfaces.CalculationFunction;

public class TrigonometryFunction implements CalculationFunction {
    private static final double CLOSE_TO_ZERO = 1.0E-12;

    private final CalculationFunction sin;
    private final CalculationFunction cos;
    private final CalculationFunction tan;
    private final CalculationFunction cot;
    private final CalculationFunction sec;
    private final CalculationFunction csc;
    
    public TrigonometryFunction(
            CalculationFunction sin,
            CalculationFunction cos,
            CalculationFunction tan,
            CalculationFunction cot,
            CalculationFunction sec,
            CalculationFunction csc
    ) {
        if (sin == null || cos == null || tan == null || cot == null || sec == null || csc == null) {
            throw new IllegalArgumentException("trigonometric dependencies must not be null");
        }
        this.sin = sin;
        this.cos = cos;
        this.tan = tan;
        this.cot = cot;
        this.sec = sec;
        this.csc = csc;
    }

    @Override
    public double calculate(double x) {
        if (!Double.isFinite(x)) {
            throw new IllegalArgumentException("x must be finite");
        }
        if (x > 0.0) {
            throw new IllegalArgumentException("TrigBranch is defined only for x <= 0");
        }

        double sinValue = sin.calculate(x);
        double cosValue = cos.calculate(x);
        double tanValue = tan.calculate(x);
        double cotValue = cot.calculate(x);
        double secValue = sec.calculate(x);
        double cscValue = csc.calculate(x);

        double sinDifferenceDivision = divide(sinValue - sinValue, sinValue, "sin(x)");
        double secSubtraction = sinDifferenceDivision - secValue;
        double nestedCube = Math.pow(Math.pow(secSubtraction, 3), 3);
        double squaredDivision = Math.pow(divide(nestedCube + cosValue, cscValue, "csc(x)"), 2);

        double tanCube = Math.pow(tanValue, 3);
        double tanNestedCube = Math.pow(tanCube, 3);
        double denominator = ((tanValue + tanValue) - (tanValue - tanNestedCube))
                + (Math.pow(tanValue, 2) - cosValue);
        double leftPart = (squaredDivision * secValue)
                * divide(secValue, denominator, "((tan(x) + tan(x)) - (tan(x) - ((tan(x) ^ 3) ^ 3))) + ((tan(x) ^ 2) - cos(x))");

        double rightPartBase = divide(tanValue * sinValue, secValue, "sec(x)") - (cotValue * secValue);
        double rightPart = Math.pow(rightPartBase, 2);

        return leftPart - rightPart;
    }

    private double divide(double dividend, double divisor, String divisorLabel) {
        if (Math.abs(divisor) <= CLOSE_TO_ZERO) {
            throw new IllegalArgumentException("division by zero in " + divisorLabel);
        }
        return dividend / divisor;
    }
}
