import Interfaces.CalculationFunction;
import TrigonometryFunctions.CosCalc;
import TrigonometryFunctions.CotCalc;
import TrigonometryFunctions.CscCalc;
import TrigonometryFunctions.SecCalc;
import TrigonometryFunctions.SinCalc;
import TrigonometryFunctions.TanCalc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TrigDerivedFunctionsTest {
    private static final double EPS = 1.0E-12;
    private static final double ASSERT_EPS = 1.0E-8;

    private static final double PI = Math.PI;
    private static final double HALF_PI = Math.PI / 2.0;
    private static final double TWO_PI = Math.PI * 2.0;
    private static final double SIXTH_PI = Math.PI / 6.0;
    private static final double FOURTH_PI = Math.PI / 4.0;
    private static final double THIRD_PI = Math.PI / 3.0;
    private static final double SQRT2_OVER_2 = 0.7071067811865476;
    private static final double SQRT3_OVER_2 = 0.8660254037844386;
    private static final double SQRT3 = 1.7320508075688772;
    private static final double ONE_OVER_SQRT3 = 0.5773502691896257;
    private static final double TWO_OVER_SQRT3 = 1.1547005383792515;
    private static final double SQRT2 = 1.4142135623730951;

    private CalculationFunction cos;
    private CalculationFunction tan;
    private CalculationFunction cot;
    private CalculationFunction sec;
    private CalculationFunction csc;

    @BeforeEach
    void setUp() {
        CalculationFunction sin = new SinCalc(EPS);
        cos = new CosCalc(sin);
        tan = new TanCalc(sin, cos);
        cot = new CotCalc(sin, cos);
        sec = new SecCalc(cos);
        csc = new CscCalc(sin);
    }

    @Test
    void cosAtZeroEqualsOne() {
        assertEquals(1.0, cos.calculate(0.0), ASSERT_EPS);
    }

    @Test
    void cosAtPiOverSixEqualsSqrtThreeOverTwo() {
        assertEquals(SQRT3_OVER_2, cos.calculate(SIXTH_PI), ASSERT_EPS);
    }

    @Test
    void cosAtPiOverFourEqualsSqrtTwoOverTwo() {
        assertEquals(SQRT2_OVER_2, cos.calculate(FOURTH_PI), ASSERT_EPS);
    }

    @Test
    void cosAtPiOverThreeEqualsOneHalf() {
        assertEquals(0.5, cos.calculate(THIRD_PI), ASSERT_EPS);
    }

    @Test
    void cosAtPiOverTwoEqualsZero() {
        assertEquals(0.0, cos.calculate(HALF_PI), ASSERT_EPS);
    }

    @Test
    void cosAtPiEqualsMinusOne() {
        assertEquals(-1.0, cos.calculate(PI), ASSERT_EPS);
    }

    @Test
    void cosAtNegativePiEqualsMinusOne() {
        assertEquals(-1.0, cos.calculate(-PI), ASSERT_EPS);
    }

    @Test
    void cosIsEvenAtPiOverSix() {
        assertEquals(cos.calculate(SIXTH_PI), cos.calculate(-SIXTH_PI), ASSERT_EPS);
    }

    @Test
    void cosIsEvenAtPiOverFour() {
        assertEquals(cos.calculate(FOURTH_PI), cos.calculate(-FOURTH_PI), ASSERT_EPS);
    }

    @Test
    void cosIsEvenAtPiOverThree() {
        assertEquals(cos.calculate(THIRD_PI), cos.calculate(-THIRD_PI), ASSERT_EPS);
    }

    @Test
    void cosIsPeriodicForPositiveShift() {
        assertEquals(cos.calculate(THIRD_PI), cos.calculate(THIRD_PI + TWO_PI), ASSERT_EPS);
    }

    @Test
    void cosIsPeriodicForLargeShift() {
        assertEquals(cos.calculate(SIXTH_PI), cos.calculate(SIXTH_PI + 1000.0 * TWO_PI), ASSERT_EPS);
    }

    @Test
    void tanAtZeroEqualsZero() {
        assertEquals(0.0, tan.calculate(0.0), ASSERT_EPS);
    }

    @Test
    void tanAtPiOverSixEqualsOneOverSqrtThree() {
        assertEquals(ONE_OVER_SQRT3, tan.calculate(SIXTH_PI), ASSERT_EPS);
    }

    @Test
    void tanAtPiOverFourEqualsOne() {
        assertEquals(1.0, tan.calculate(FOURTH_PI), ASSERT_EPS);
    }

    @Test
    void tanAtPiOverThreeEqualsSqrtThree() {
        assertEquals(SQRT3, tan.calculate(THIRD_PI), ASSERT_EPS);
    }

    @Test
    void tanIsOddAtPiOverSix() {
        assertEquals(-tan.calculate(SIXTH_PI), tan.calculate(-SIXTH_PI), ASSERT_EPS);
    }

    @Test
    void tanIsOddAtPiOverFour() {
        assertEquals(-tan.calculate(FOURTH_PI), tan.calculate(-FOURTH_PI), ASSERT_EPS);
    }

    @Test
    void tanIsPeriodicWithPiShift() {
        assertEquals(tan.calculate(SIXTH_PI), tan.calculate(SIXTH_PI + PI), ASSERT_EPS);
    }

    @Test
    void tanIsPeriodicForLargePiShift() {
        assertEquals(tan.calculate(SIXTH_PI), tan.calculate(SIXTH_PI + 1000.0 * PI), ASSERT_EPS);
    }

    @Test
    void tanThrowsAtPiOverTwo() {
        assertThrows(IllegalArgumentException.class, () -> tan.calculate(HALF_PI));
    }

    @Test
    void tanThrowsAtNegativePiOverTwo() {
        assertThrows(IllegalArgumentException.class, () -> tan.calculate(-HALF_PI));
    }

    @Test
    void cotAtPiOverSixEqualsSqrtThree() {
        assertEquals(SQRT3, cot.calculate(SIXTH_PI), ASSERT_EPS);
    }

    @Test
    void cotAtPiOverFourEqualsOne() {
        assertEquals(1.0, cot.calculate(FOURTH_PI), ASSERT_EPS);
    }

    @Test
    void cotAtPiOverThreeEqualsOneOverSqrtThree() {
        assertEquals(ONE_OVER_SQRT3, cot.calculate(THIRD_PI), ASSERT_EPS);
    }

    @Test
    void cotIsOddAtPiOverSix() {
        assertEquals(-cot.calculate(SIXTH_PI), cot.calculate(-SIXTH_PI), ASSERT_EPS);
    }

    @Test
    void cotIsOddAtPiOverFour() {
        assertEquals(-cot.calculate(FOURTH_PI), cot.calculate(-FOURTH_PI), ASSERT_EPS);
    }

    @Test
    void cotIsPeriodicWithPiShift() {
        assertEquals(cot.calculate(THIRD_PI), cot.calculate(THIRD_PI + PI), ASSERT_EPS);
    }

    @Test
    void cotIsPeriodicForLargePiShift() {
        assertEquals(cot.calculate(THIRD_PI), cot.calculate(THIRD_PI + 1000.0 * PI), ASSERT_EPS);
    }

    @Test
    void cotThrowsAtZero() {
        assertThrows(IllegalArgumentException.class, () -> cot.calculate(0.0));
    }

    @Test
    void cotThrowsAtPi() {
        assertThrows(IllegalArgumentException.class, () -> cot.calculate(PI));
    }

    @Test
    void secAtZeroEqualsOne() {
        assertEquals(1.0, sec.calculate(0.0), ASSERT_EPS);
    }

    @Test
    void secAtPiOverSixEqualsTwoOverSqrtThree() {
        assertEquals(TWO_OVER_SQRT3, sec.calculate(SIXTH_PI), ASSERT_EPS);
    }

    @Test
    void secAtPiOverFourEqualsSqrtTwo() {
        assertEquals(SQRT2, sec.calculate(FOURTH_PI), ASSERT_EPS);
    }

    @Test
    void secAtPiOverThreeEqualsTwo() {
        assertEquals(2.0, sec.calculate(THIRD_PI), ASSERT_EPS);
    }

    @Test
    void secAtPiEqualsMinusOne() {
        assertEquals(-1.0, sec.calculate(PI), ASSERT_EPS);
    }

    @Test
    void secIsEvenAtPiOverSix() {
        assertEquals(sec.calculate(SIXTH_PI), sec.calculate(-SIXTH_PI), ASSERT_EPS);
    }

    @Test
    void secIsPeriodicForPositiveShift() {
        assertEquals(sec.calculate(THIRD_PI), sec.calculate(THIRD_PI + TWO_PI), ASSERT_EPS);
    }

    @Test
    void secIsPeriodicForLargeShift() {
        assertEquals(sec.calculate(SIXTH_PI), sec.calculate(SIXTH_PI + 1000.0 * TWO_PI), ASSERT_EPS);
    }

    @Test
    void secThrowsAtPiOverTwo() {
        assertThrows(IllegalArgumentException.class, () -> sec.calculate(HALF_PI));
    }

    @Test
    void secThrowsAtNegativePiOverTwo() {
        assertThrows(IllegalArgumentException.class, () -> sec.calculate(-HALF_PI));
    }

    @Test
    void cscAtPiOverSixEqualsTwo() {
        assertEquals(2.0, csc.calculate(SIXTH_PI), ASSERT_EPS);
    }

    @Test
    void cscAtPiOverFourEqualsSqrtTwo() {
        assertEquals(SQRT2, csc.calculate(FOURTH_PI), ASSERT_EPS);
    }

    @Test
    void cscAtPiOverThreeEqualsTwoOverSqrtThree() {
        assertEquals(TWO_OVER_SQRT3, csc.calculate(THIRD_PI), ASSERT_EPS);
    }

    @Test
    void cscAtPiOverTwoEqualsOne() {
        assertEquals(1.0, csc.calculate(HALF_PI), ASSERT_EPS);
    }

    @Test
    void cscAtNegativePiOverTwoEqualsMinusOne() {
        assertEquals(-1.0, csc.calculate(-HALF_PI), ASSERT_EPS);
    }

    @Test
    void cscIsOddAtPiOverSix() {
        assertEquals(-csc.calculate(SIXTH_PI), csc.calculate(-SIXTH_PI), ASSERT_EPS);
    }

    @Test
    void cscIsPeriodicForPositiveShift() {
        assertEquals(csc.calculate(THIRD_PI), csc.calculate(THIRD_PI + TWO_PI), ASSERT_EPS);
    }

    @Test
    void cscIsPeriodicForLargeShift() {
        assertEquals(csc.calculate(SIXTH_PI), csc.calculate(SIXTH_PI + 1000.0 * TWO_PI), ASSERT_EPS);
    }

    @Test
    void cscThrowsAtZero() {
        assertThrows(IllegalArgumentException.class, () -> csc.calculate(0.0));
    }

    @Test
    void cscThrowsAtPi() {
        assertThrows(IllegalArgumentException.class, () -> csc.calculate(PI));
    }
}
