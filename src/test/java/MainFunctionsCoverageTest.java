import Interfaces.CalculationFunction;
import MainFunctions.LogarythmicFunction;
import MainFunctions.SystemFunction;
import MainFunctions.TrigonometryFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MainFunctionsCoverageTest {
    private static final double EPS = 1.0E-12;

    @Test
    void logarythmicFunctionRejectsNullLnDependency() {
        CalculationFunction function = constant(1.0);

        assertThrows(IllegalArgumentException.class,
                () -> new LogarythmicFunction(null, function, function, function));
    }

    @Test
    void logarythmicFunctionRejectsNullLog2Dependency() {
        CalculationFunction function = constant(1.0);

        assertThrows(IllegalArgumentException.class,
                () -> new LogarythmicFunction(function, null, function, function));
    }

    @Test
    void logarythmicFunctionRejectsNullLog5Dependency() {
        CalculationFunction function = constant(1.0);

        assertThrows(IllegalArgumentException.class,
                () -> new LogarythmicFunction(function, function, null, function));
    }

    @Test
    void logarythmicFunctionRejectsNullLog10Dependency() {
        CalculationFunction function = constant(1.0);

        assertThrows(IllegalArgumentException.class,
                () -> new LogarythmicFunction(function, function, function, null));
    }

    @Test
    void logarythmicFunctionRejectsNonFiniteArgument() {
        CalculationFunction function = constant(1.0);
        LogarythmicFunction branch = new LogarythmicFunction(function, function, function, function);

        assertThrows(IllegalArgumentException.class, () -> branch.calculate(Double.NaN));
    }

    @Test
    void logarythmicFunctionRejectsNonPositiveArgument() {
        CalculationFunction function = constant(1.0);
        LogarythmicFunction branch = new LogarythmicFunction(function, function, function, function);

        assertThrows(IllegalArgumentException.class, () -> branch.calculate(0.0));
    }

    @Test
    void logarythmicFunctionRejectsZeroLog10Denominator() {
        LogarythmicFunction branch = new LogarythmicFunction(
                constant(1.0),
                constant(2.0),
                constant(3.0),
                constant(0.0)
        );

        assertThrows(IllegalArgumentException.class, () -> branch.calculate(2.0));
    }

    @Test
    void logarythmicFunctionCalculatesFormulaFromDependencies() {
        LogarythmicFunction branch = new LogarythmicFunction(
                constant(2.0),
                constant(3.0),
                constant(5.0),
                constant(10.0)
        );

        double expected = ((((5.0 * 10.0) + 3.0) + 2.0) * 5.0) / Math.pow(10.0, 3);

        assertEquals(expected, branch.calculate(2.0), EPS);
    }

    @Test
    void trigonometryFunctionRejectsNullSinDependency() {
        CalculationFunction function = constant(1.0);

        assertThrows(IllegalArgumentException.class,
                () -> new TrigonometryFunction(null, function, function, function, function, function));
    }

    @Test
    void trigonometryFunctionRejectsNullCosDependency() {
        CalculationFunction function = constant(1.0);

        assertThrows(IllegalArgumentException.class,
                () -> new TrigonometryFunction(function, null, function, function, function, function));
    }

    @Test
    void trigonometryFunctionRejectsNullTanDependency() {
        CalculationFunction function = constant(1.0);

        assertThrows(IllegalArgumentException.class,
                () -> new TrigonometryFunction(function, function, null, function, function, function));
    }

    @Test
    void trigonometryFunctionRejectsNullCotDependency() {
        CalculationFunction function = constant(1.0);

        assertThrows(IllegalArgumentException.class,
                () -> new TrigonometryFunction(function, function, function, null, function, function));
    }

    @Test
    void trigonometryFunctionRejectsNullSecDependency() {
        CalculationFunction function = constant(1.0);

        assertThrows(IllegalArgumentException.class,
                () -> new TrigonometryFunction(function, function, function, function, null, function));
    }

    @Test
    void trigonometryFunctionRejectsNullCscDependency() {
        CalculationFunction function = constant(1.0);

        assertThrows(IllegalArgumentException.class,
                () -> new TrigonometryFunction(function, function, function, function, function, null));
    }

    @Test
    void trigonometryFunctionRejectsNonFiniteArgument() {
        CalculationFunction function = constant(1.0);
        TrigonometryFunction branch = new TrigonometryFunction(function, function, function, function, function, function);

        assertThrows(IllegalArgumentException.class, () -> branch.calculate(Double.POSITIVE_INFINITY));
    }

    @Test
    void trigonometryFunctionRejectsPositiveArgument() {
        CalculationFunction function = constant(1.0);
        TrigonometryFunction branch = new TrigonometryFunction(function, function, function, function, function, function);

        assertThrows(IllegalArgumentException.class, () -> branch.calculate(1.0));
    }

    @Test
    void trigonometryFunctionRejectsZeroSinDivisor() {
        TrigonometryFunction branch = new TrigonometryFunction(
                constant(0.0),
                constant(1.0),
                constant(1.0),
                constant(1.0),
                constant(1.0),
                constant(1.0)
        );

        assertThrows(IllegalArgumentException.class, () -> branch.calculate(-1.0));
    }

    @Test
    void trigonometryFunctionRejectsZeroCscDivisor() {
        TrigonometryFunction branch = new TrigonometryFunction(
                constant(1.0),
                constant(1.0),
                constant(1.0),
                constant(1.0),
                constant(1.0),
                constant(0.0)
        );

        assertThrows(IllegalArgumentException.class, () -> branch.calculate(-1.0));
    }

    @Test
    void trigonometryFunctionRejectsZeroTanDenominator() {
        TrigonometryFunction branch = new TrigonometryFunction(
                constant(1.0),
                constant(3.0),
                constant(1.0),
                constant(1.0),
                constant(1.0),
                constant(1.0)
        );

        assertThrows(IllegalArgumentException.class, () -> branch.calculate(-1.0));
    }

    @Test
    void trigonometryFunctionRejectsZeroSecDivisor() {
        TrigonometryFunction branch = new TrigonometryFunction(
                constant(1.0),
                constant(1.0),
                constant(1.0),
                constant(1.0),
                constant(0.0),
                constant(1.0)
        );

        assertThrows(IllegalArgumentException.class, () -> branch.calculate(-1.0));
    }

    @Test
    void trigonometryFunctionCalculatesFormulaFromDependencies() {
        TrigonometryFunction branch = new TrigonometryFunction(
                constant(2.0),
                constant(3.0),
                constant(4.0),
                constant(5.0),
                constant(6.0),
                constant(7.0)
        );

        double sinDifferenceDivision = (2.0 - 2.0) / 2.0;
        double secSubtraction = sinDifferenceDivision - 6.0;
        double nestedCube = Math.pow(Math.pow(secSubtraction, 3), 3);
        double squaredDivision = Math.pow((nestedCube + 3.0) / 7.0, 2);
        double tanCube = Math.pow(4.0, 3);
        double tanNestedCube = Math.pow(tanCube, 3);
        double denominator = ((4.0 + 4.0) - (4.0 - tanNestedCube)) + (Math.pow(4.0, 2) - 3.0);
        double leftPart = (squaredDivision * 6.0) * (6.0 / denominator);
        double rightPartBase = ((4.0 * 2.0) / 6.0) - (5.0 * 6.0);
        double expected = leftPart - Math.pow(rightPartBase, 2);

        assertEquals(expected, branch.calculate(-1.0), EPS);
    }

    @Test
    void systemFunctionRejectsNullTrigonometryBranch() {
        assertThrows(IllegalArgumentException.class,
                () -> new SystemFunction(null, constant(1.0)));
    }

    @Test
    void systemFunctionRejectsNullLogarithmicBranch() {
        assertThrows(IllegalArgumentException.class,
                () -> new SystemFunction(constant(1.0), null));
    }

    @Test
    void systemFunctionRejectsNonFiniteArgument() {
        SystemFunction system = new SystemFunction(constant(1.0), constant(2.0));

        assertThrows(IllegalArgumentException.class, () -> system.calculate(Double.NEGATIVE_INFINITY));
    }

    @Test
    void systemFunctionDelegatesNonPositiveArgumentToTrigonometryBranch() {
        CalculationFunction trigBranch = mock(CalculationFunction.class);
        CalculationFunction logBranch = mock(CalculationFunction.class);
        when(trigBranch.calculate(-1.0)).thenReturn(11.0);
        SystemFunction system = new SystemFunction(trigBranch, logBranch);

        assertEquals(11.0, system.calculate(-1.0), EPS);
        verify(trigBranch).calculate(-1.0);
        verify(logBranch, never()).calculate(-1.0);
    }

    @Test
    void systemFunctionDelegatesPositiveArgumentToLogarithmicBranch() {
        CalculationFunction trigBranch = mock(CalculationFunction.class);
        CalculationFunction logBranch = mock(CalculationFunction.class);
        when(logBranch.calculate(2.0)).thenReturn(22.0);
        SystemFunction system = new SystemFunction(trigBranch, logBranch);

        assertEquals(22.0, system.calculate(2.0), EPS);
        verify(logBranch).calculate(2.0);
        verify(trigBranch, never()).calculate(2.0);
    }

    private static CalculationFunction constant(double value) {
        return x -> value;
    }
}
