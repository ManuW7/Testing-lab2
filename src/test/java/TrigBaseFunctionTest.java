import Interfaces.CalculationFunction;
import TrigonometryFunctions.SinCalc;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrigBaseFunctionTest {
    private static final double EPS = 1.0E-12;
    private static final double ASSERT_EPS = 1.0E-9;

    private static final double PI = Math.PI;
    private static final double HALF_PI = Math.PI / 2.0;
    private static final double TWO_PI = Math.PI * 2.0;
    private static final double SIXTH_PI = Math.PI / 6.0;
    private static final double FOURTH_PI = Math.PI / 4.0;
    private static final double THIRD_PI = Math.PI / 3.0;
    private static final double SQRT2_OVER_2 = 0.7071067811865476;
    private static final double SQRT3_OVER_2 = 0.8660254037844386;

    private final CalculationFunction sin = new SinCalc(EPS);

    @Test
    void sinAtZeroEqualsZero() {
        assertEquals(0.0, sin.calculate(0.0), ASSERT_EPS);
    }

    @Test
    void sinAtPiOverSixEqualsOneHalf() {
        assertEquals(0.5, sin.calculate(SIXTH_PI), ASSERT_EPS);
    }

    @Test
    void sinAtPiOverFourEqualsSqrtTwoOverTwo() {
        assertEquals(SQRT2_OVER_2, sin.calculate(FOURTH_PI), ASSERT_EPS);
    }

    @Test
    void sinAtPiOverThreeEqualsSqrtThreeOverTwo() {
        assertEquals(SQRT3_OVER_2, sin.calculate(THIRD_PI), ASSERT_EPS);
    }

    @Test
    void sinAtPiOverTwoEqualsOne() {
        assertEquals(1.0, sin.calculate(HALF_PI), ASSERT_EPS);
    }

    @Test
    void sinAtPiEqualsZero() {
        assertEquals(0.0, sin.calculate(PI), ASSERT_EPS);
    }

    @Test
    void sinAtNegativePiOverTwoEqualsMinusOne() {
        assertEquals(-1.0, sin.calculate(-HALF_PI), ASSERT_EPS);
    }

    @Test
    void sinIsOddAtPiOverSix() {
        assertEquals(-sin.calculate(SIXTH_PI), sin.calculate(-SIXTH_PI), ASSERT_EPS);
    }

    @Test
    void sinIsOddAtPiOverFour() {
        assertEquals(-sin.calculate(FOURTH_PI), sin.calculate(-FOURTH_PI), ASSERT_EPS);
    }

    @Test
    void sinIsOddAtPiOverThree() {
        assertEquals(-sin.calculate(THIRD_PI), sin.calculate(-THIRD_PI), ASSERT_EPS);
    }

    @Test
    void sinIsPeriodicForPositiveShift() {
        assertEquals(sin.calculate(SIXTH_PI), sin.calculate(SIXTH_PI + TWO_PI), ASSERT_EPS);
    }

    @Test
    void sinIsPeriodicForNegativeShift() {
        assertEquals(sin.calculate(-FOURTH_PI), sin.calculate(-FOURTH_PI - TWO_PI), ASSERT_EPS);
    }

    @Test
    void sinAtTwoPiEqualsZero() {
        assertEquals(0.0, sin.calculate(TWO_PI), ASSERT_EPS);
    }
}
