import Interfaces.CalculationFunction;
import LogarithmicFunctions.LnCalc;
import LogarithmicFunctions.Log10Calc;
import LogarithmicFunctions.Log2Calc;
import LogarithmicFunctions.Log5Calc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LogarithmicFunctionsTest {
    private static final double EPS = 1.0E-12;
    private static final double ASSERT_EPS = 1.0E-7;

    private static final double E = Math.E;
    private static final double ONE_OVER_E = 1.0 / Math.E;
    private static final double LN_2 = 0.6931471805599453;
    private static final double LN_5 = 1.6094379124341003;
    private static final double LN_10 = 2.302585092994046;

    private CalculationFunction ln;
    private CalculationFunction log2;
    private CalculationFunction log5;
    private CalculationFunction log10;

    @BeforeEach
    void setUp() {
        ln = new LnCalc(EPS);
        log2 = new Log2Calc(ln);
        log5 = new Log5Calc(ln);
        log10 = new Log10Calc(ln);
    }

    @Test
    void lnAtOneOverEEqualsMinusOne() {
        assertEquals(-1.0, ln.calculate(ONE_OVER_E), ASSERT_EPS);
    }

    @Test
    void lnAtOneEqualsZero() {
        assertEquals(0.0, ln.calculate(1.0), ASSERT_EPS);
    }

    @Test
    void lnAtTwoEqualsKnownConstant() {
        assertEquals(LN_2, ln.calculate(2.0), ASSERT_EPS);
    }

    @Test
    void lnAtEEqualsOne() {
        assertEquals(1.0, ln.calculate(E), ASSERT_EPS);
    }

    @Test
    void lnAtFiveEqualsKnownConstant() {
        assertEquals(LN_5, ln.calculate(5.0), ASSERT_EPS);
    }

    @Test
    void lnAtTenEqualsKnownConstant() {
        assertEquals(LN_10, ln.calculate(10.0), ASSERT_EPS);
    }

    @Test
    void lnThrowsAtNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> ln.calculate(-1.0));
    }

    @Test
    void lnThrowsAtZero() {
        assertThrows(IllegalArgumentException.class, () -> ln.calculate(0.0));
    }

    @Test
    void log2AtOneEqualsZero() {
        assertEquals(0.0, log2.calculate(1.0), ASSERT_EPS);
    }

    @Test
    void log2AtTwoEqualsOne() {
        assertEquals(1.0, log2.calculate(2.0), ASSERT_EPS);
    }

    @Test
    void log2AtFourEqualsTwo() {
        assertEquals(2.0, log2.calculate(4.0), ASSERT_EPS);
    }

    @Test
    void log2AtEightEqualsThree() {
        assertEquals(3.0, log2.calculate(8.0), ASSERT_EPS);
    }

    @Test
    void log2AtOneHalfEqualsMinusOne() {
        assertEquals(-1.0, log2.calculate(0.5), ASSERT_EPS);
    }

    @Test
    void log2ThrowsAtZero() {
        assertThrows(IllegalArgumentException.class, () -> log2.calculate(0.0));
    }

    @Test
    void log2ThrowsAtNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> log2.calculate(-2.0));
    }

    @Test
    void log5AtOneEqualsZero() {
        assertEquals(0.0, log5.calculate(1.0), ASSERT_EPS);
    }

    @Test
    void log5AtFiveEqualsOne() {
        assertEquals(1.0, log5.calculate(5.0), ASSERT_EPS);
    }

    @Test
    void log5AtTwentyFiveEqualsTwo() {
        assertEquals(2.0, log5.calculate(25.0), ASSERT_EPS);
    }

    @Test
    void log5AtOneFifthEqualsMinusOne() {
        assertEquals(-1.0, log5.calculate(0.2), ASSERT_EPS);
    }

    @Test
    void log5ThrowsAtZero() {
        assertThrows(IllegalArgumentException.class, () -> log5.calculate(0.0));
    }

    @Test
    void log5ThrowsAtNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> log5.calculate(-5.0));
    }

    @Test
    void log10AtOneEqualsZero() {
        assertEquals(0.0, log10.calculate(1.0), ASSERT_EPS);
    }

    @Test
    void log10AtTenEqualsOne() {
        assertEquals(1.0, log10.calculate(10.0), ASSERT_EPS);
    }

    @Test
    void log10AtHundredEqualsTwo() {
        assertEquals(2.0, log10.calculate(100.0), ASSERT_EPS);
    }

    @Test
    void log10AtOneTenthEqualsMinusOne() {
        assertEquals(-1.0, log10.calculate(0.1), ASSERT_EPS);
    }

    @Test
    void log10ThrowsAtZero() {
        assertThrows(IllegalArgumentException.class, () -> log10.calculate(0.0));
    }

    @Test
    void log10ThrowsAtNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> log10.calculate(-10.0));
    }

    @Test
    void lnAdditionIdentityForTwoAndFive() {
        assertEquals(ln.calculate(2.0) + ln.calculate(5.0), ln.calculate(10.0), ASSERT_EPS);
    }

    @Test
    void log2AdditionIdentityForTwoAndFour() {
        assertEquals(log2.calculate(8.0), log2.calculate(2.0) + log2.calculate(4.0), ASSERT_EPS);
    }

    @Test
    void log10AdditionIdentityForTenAndTen() {
        assertEquals(log10.calculate(100.0), log10.calculate(10.0) + log10.calculate(10.0), ASSERT_EPS);
    }
}
