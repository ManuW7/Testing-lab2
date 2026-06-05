import Interfaces.CalculationFunction;
import LogarithmicFunctions.LnCalc;
import LogarithmicFunctions.Log10Calc;
import LogarithmicFunctions.Log2Calc;
import LogarithmicFunctions.Log5Calc;
import MainFunctions.LogarythmicFunction;
import MainFunctions.SystemFunction;
import MainFunctions.TrigonometryFunction;
import TrigonometryFunctions.CosCalc;
import TrigonometryFunctions.CotCalc;
import TrigonometryFunctions.CscCalc;
import TrigonometryFunctions.SecCalc;
import TrigonometryFunctions.SinCalc;
import TrigonometryFunctions.TanCalc;
import org.mockito.Mockito;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

final class IntegrationTestSupport {
    static final double EPS = 1.0E-12;
    private static final double LOOKUP_EPS = 1.0E-6;

    private static final double X_TRIG_ASYMPTOTE_LEFT = -2.0 * Math.PI - 0.01;
    private static final double X_TRIG_ASYMPTOTE_RIGHT = -3.0 * Math.PI / 2.0 + 0.1;
    private static final double X_TRIG_PERIODIC_NEAR_ZERO = -2.0 * Math.PI - 0.1;
    private static final double X_TRIG_PERIODIC_NEAR_PI = -Math.PI - 0.1;

    private IntegrationTestSupport() {
    }

    static Stream<Double> definedTrigBranchPoints() {
        return Stream.of(
                X_TRIG_PERIODIC_NEAR_ZERO,
                X_TRIG_ASYMPTOTE_LEFT,
                X_TRIG_ASYMPTOTE_RIGHT,
                X_TRIG_PERIODIC_NEAR_PI,
                -2.0,
                -1.0,
                -0.5,
                -0.1,
                -0.01,
                -0.001,
                -1.0E-4
        );
    }

    static Stream<Double> definedLogBranchPoints() {
        return Stream.of(
                1.0E-6,
                1.0E-4,
                0.001,
                0.01,
                0.1,
                0.5,
                0.99,
                1.01,
                2.0,
                10.0,
                100.0,
                125.0
        );
    }

    static Stream<Double> definedSystemPoints() {
        return Stream.concat(definedTrigBranchPoints(), definedLogBranchPoints());
    }

    static CalculationFunction realSin() {
        return new SinCalc(EPS);
    }

    static CalculationFunction realCos(CalculationFunction sin) {
        return new CosCalc(sin);
    }

    static CalculationFunction realTan(CalculationFunction sin, CalculationFunction cos) {
        return new TanCalc(sin, cos);
    }

    static CalculationFunction realCot(CalculationFunction sin, CalculationFunction cos) {
        return new CotCalc(sin, cos);
    }

    static CalculationFunction realSec(CalculationFunction cos) {
        return new SecCalc(cos);
    }

    static CalculationFunction realCsc(CalculationFunction sin) {
        return new CscCalc(sin);
    }

    static CalculationFunction realLn() {
        return new LnCalc(EPS);
    }

    static CalculationFunction realLog2(CalculationFunction ln) {
        return new Log2Calc(ln);
    }

    static CalculationFunction realLog5(CalculationFunction ln) {
        return new Log5Calc(ln);
    }

    static CalculationFunction realLog10(CalculationFunction ln) {
        return new Log10Calc(ln);
    }

    static CalculationFunction sinStub() {
        return mockTable("sin", mapOf(
                point(X_TRIG_PERIODIC_NEAR_ZERO, -0.09983341664682781),
                point(X_TRIG_ASYMPTOTE_LEFT, -0.009999833334166453),
                point(X_TRIG_ASYMPTOTE_RIGHT, 0.9950041652780249),
                point(X_TRIG_PERIODIC_NEAR_PI, 0.09983341664682933),
                point(-2.0, -0.909297426825682),
                point(-1.0, -0.8414709848078937),
                point(-0.5, -0.479425538604203),
                point(-0.1, -0.09983341664682817),
                point(-0.01, -0.009999833334166666),
                point(-0.001, -9.999998333333417E-4),
                point(-1.0E-4, -9.999999983333334E-5)
        ));
    }

    static CalculationFunction cosStub() {
        return mockTable("cos", mapOf(
                point(X_TRIG_PERIODIC_NEAR_ZERO, 0.9950041652780249),
                point(X_TRIG_ASYMPTOTE_LEFT, 0.9999500004166648),
                point(X_TRIG_ASYMPTOTE_RIGHT, -0.09983341664682781),
                point(X_TRIG_PERIODIC_NEAR_PI, -0.9950041652780256),
                point(-2.0, -0.41614683654713797),
                point(-1.0, 0.5403023058681387),
                point(-0.5, 0.8775825618903735),
                point(-0.1, 0.9950041652780249),
                point(-0.01, 0.9999500004166648),
                point(-0.001, 0.9999995000000415),
                point(-1.0E-4, 0.9999999949999996)
        ));
    }

    static CalculationFunction tanStub() {
        return mockTable("tan", mapOf(
                point(X_TRIG_PERIODIC_NEAR_ZERO, -0.10033467208545029),
                point(X_TRIG_ASYMPTOTE_LEFT, -0.010000333346666999),
                point(X_TRIG_ASYMPTOTE_RIGHT, -9.966644423259265),
                point(X_TRIG_PERIODIC_NEAR_PI, -0.10033467208545176),
                point(-2.0, 2.185039863261543),
                point(-1.0, -1.5574077246549),
                point(-0.5, -0.54630248984379),
                point(-0.1, -0.10033467208545065),
                point(-0.01, -0.010000333346667212),
                point(-0.001, -0.001000000333333467),
                point(-1.0E-4, -1.0000000033333338E-4)
        ));
    }

    static CalculationFunction cotStub() {
        return mockTable("cot", mapOf(
                point(X_TRIG_PERIODIC_NEAR_ZERO, -9.966644423259265),
                point(X_TRIG_ASYMPTOTE_LEFT, -99.99666664444631),
                point(X_TRIG_ASYMPTOTE_RIGHT, -0.10033467208545029),
                point(X_TRIG_PERIODIC_NEAR_PI, -9.966644423259119),
                point(-2.0, 0.4576575543602807),
                point(-1.0, -0.6420926159343315),
                point(-0.5, -1.8304877217124536),
                point(-0.1, -9.966644423259227),
                point(-0.01, -99.99666664444418),
                point(-0.001, -999.9996666666442),
                point(-1.0E-4, -9999.99996666666)
        ));
    }

    static CalculationFunction secStub() {
        return mockTable("sec", mapOf(
                point(X_TRIG_PERIODIC_NEAR_ZERO, 1.0050209184004564),
                point(X_TRIG_ASYMPTOTE_LEFT, 1.0000500020834184),
                point(X_TRIG_ASYMPTOTE_RIGHT, -10.016686131634811),
                point(X_TRIG_PERIODIC_NEAR_PI, -1.0050209184004555),
                point(-2.0, -2.4029979617224067),
                point(-1.0, 1.8508157176809292),
                point(-0.5, 1.1394939273245481),
                point(-0.1, 1.0050209184004564),
                point(-0.01, 1.0000500020834184),
                point(-0.001, 1.0000005000002086),
                point(-1.0E-4, 1.0000000050000004)
        ));
    }

    static CalculationFunction cscStub() {
        return mockTable("csc", mapOf(
                point(X_TRIG_PERIODIC_NEAR_ZERO, -10.016686131634811),
                point(X_TRIG_ASYMPTOTE_LEFT, -100.00166668611344),
                point(X_TRIG_ASYMPTOTE_RIGHT, 1.0050209184004564),
                point(X_TRIG_PERIODIC_NEAR_PI, 10.016686131634659),
                point(-2.0, -1.099750170294616),
                point(-1.0, -1.1883951057781252),
                point(-0.5, -2.085829642933488),
                point(-0.1, -10.016686131634774),
                point(-0.01, -100.0016666861113),
                point(-0.001, -1000.0001666666861),
                point(-1.0E-4, -10000.000016666665)
        ));
    }

    static CalculationFunction lnStub() {
        return mockTable("ln", mapOf(
                point(1.0E-6, -13.815510557829889),
                point(1.0E-4, -9.210340371974493),
                point(0.001, -6.90775527898207),
                point(0.01, -4.605170185988049),
                point(0.1, -2.3025850929940126),
                point(0.5, -0.6931471805599343),
                point(0.99, -0.010050335853501428),
                point(1.01, 0.009950330853168073),
                point(2.0, 0.6931471805599343),
                point(10.0, 2.3025850929940135),
                point(100.0, 4.605170185988049),
                point(125.0, 4.828313737302287)
        ));
    }

    static CalculationFunction log2Stub() {
        return mockTable("log2", mapOf(
                point(1.0E-6, -19.931568569130615),
                point(1.0E-4, -13.287712379547221),
                point(0.001, -9.96578428466215),
                point(0.01, -6.64385618977477),
                point(0.1, -3.3219280948873675),
                point(0.5, -1.0),
                point(0.99, -0.014499569695115289),
                point(1.01, 0.014355292977070255),
                point(2.0, 1.0),
                point(10.0, 3.321928094887369),
                point(100.0, 6.64385618977477),
                point(125.0, 6.965784284662178)
        ));
    }

    static CalculationFunction log5Stub() {
        return mockTable("log5", mapOf(
                point(1.0E-6, -8.584059348357052),
                point(1.0E-4, -5.72270623229265),
                point(0.001, -4.292029674220233),
                point(0.01, -2.8613531161468235),
                point(0.1, -1.4306765580734044),
                point(0.5, -0.4306765580733958),
                point(0.99, -0.006244624769837569),
                point(1.01, 0.006182488169499809),
                point(2.0, 0.4306765580733958),
                point(10.0, 1.4306765580734049),
                point(100.0, 2.8613531161468235),
                point(125.0, 3.000000000000058)
        ));
    }

    static CalculationFunction log10Stub() {
        return mockTable("log10", mapOf(
                point(1.0E-6, -5.999999999941721),
                point(1.0E-4, -3.999999999999322),
                point(0.001, -3.000000000000013),
                point(0.01, -2.0000000000000098),
                point(0.1, -0.9999999999999997),
                point(0.5, -0.30102999566398064),
                point(0.99, -0.00436480540245014),
                point(1.01, 0.00432137378264263),
                point(2.0, 0.30102999566398064),
                point(10.0, 1.0),
                point(100.0, 2.0000000000000098),
                point(125.0, 2.0969100130080798)
        ));
    }

    static CalculationFunction trigBranchStub() {
        return mockTable("trig_branch", mapOf(
                point(X_TRIG_PERIODIC_NEAR_ZERO, -100.53379145228652),
                point(X_TRIG_ASYMPTOTE_LEFT, -10000.35333967722),
                point(X_TRIG_ASYMPTOTE_RIGHT, -1.0548494229624818E11),
                point(X_TRIG_PERIODIC_NEAR_PI, -100.13440450397724),
                point(-2.0, 29808.77682233933),
                point(-1.0, -2931.2968335316664),
                point(-0.5, -6.8354019342314185),
                point(-0.1, -100.53379145228577),
                point(-0.01, -10000.353339676793),
                point(-0.001, -1000000.3353333997),
                point(-1.0E-4, -1.0000000033353332E8)
        ));
    }

    static CalculationFunction logBranchStub() {
        return mockTable("log_branch", mapOf(
                point(1.0E-6, 0.7056922190616366),
                point(1.0E-4, 0.035120621701271414),
                point(0.001, -0.6354509756714273),
                point(0.01, -1.9765941704175207),
                point(0.1, -6.000023754655848),
                point(0.5, -24.68425213354369),
                point(0.99, -1841.531154101951),
                point(1.01, 1864.1535573081144),
                point(2.0, 28.77792296118522),
                point(10.0, 10.093694582297374),
                point(100.0, 6.070264998059049),
                point(125.0, 5.8843196733065355)
        ));
    }

    static CalculationFunction systemStub() {
        LinkedHashMap<Double, Double> table = new LinkedHashMap<>();
        table.putAll(extractTable(trigBranchStub()));
        table.putAll(extractTable(logBranchStub()));
        return mockTable("system", table);
    }

    static CalculationFunction trigBranchWithStubDependencies() {
        return new TrigonometryFunction(sinStub(), cosStub(), tanStub(), cotStub(), secStub(), cscStub());
    }

    static CalculationFunction trigBranchWithRealSin() {
        return new TrigonometryFunction(realSin(), cosStub(), tanStub(), cotStub(), secStub(), cscStub());
    }

    static CalculationFunction trigBranchWithRealSinAndCos() {
        CalculationFunction sin = realSin();
        CalculationFunction cos = realCos(sin);
        return new TrigonometryFunction(sin, cos, tanStub(), cotStub(), secStub(), cscStub());
    }

    static CalculationFunction trigBranchWithAllRealTrigModules() {
        CalculationFunction sin = realSin();
        CalculationFunction cos = realCos(sin);
        return new TrigonometryFunction(sin, cos, realTan(sin, cos), realCot(sin, cos), realSec(cos), realCsc(sin));
    }

    static CalculationFunction logBranchWithStubDependencies() {
        return new LogarythmicFunction(lnStub(), log2Stub(), log5Stub(), log10Stub());
    }

    static CalculationFunction logBranchWithRealLn() {
        return new LogarythmicFunction(realLn(), log2Stub(), log5Stub(), log10Stub());
    }

    static CalculationFunction logBranchWithAllRealLogModules() {
        CalculationFunction ln = realLn();
        return new LogarythmicFunction(ln, realLog2(ln), realLog5(ln), realLog10(ln));
    }

    static CalculationFunction systemWithBranchStubs() {
        return new SystemFunction(trigBranchStub(), logBranchStub());
    }

    static CalculationFunction systemWithRealTrigBranch() {
        return new SystemFunction(trigBranchWithAllRealTrigModules(), logBranchStub());
    }

    static CalculationFunction systemWithRealLogBranch() {
        return new SystemFunction(trigBranchStub(), logBranchWithAllRealLogModules());
    }

    static CalculationFunction fullRealSystem() {
        return new SystemFunction(trigBranchWithAllRealTrigModules(), logBranchWithAllRealLogModules());
    }

    static void assertMatches(CalculationFunction expected, CalculationFunction actual, double x) {
        double expectedValue = expected.calculate(x);
        double actualValue = actual.calculate(x);
        assertEquals(expectedValue, actualValue, tolerance(expectedValue));
    }

    static void assertUndefined(CalculationFunction function, double x) {
        assertThrows(IllegalArgumentException.class, () -> function.calculate(x));
    }

    private static CalculationFunction mockTable(String name, Map<Double, Double> table) {
        CalculationFunction mock = Mockito.mock(CalculationFunction.class, name);
        when(mock.calculate(anyDouble())).thenAnswer(invocation -> lookup(table, invocation.getArgument(0)));
        return mock;
    }

    private static double lookup(Map<Double, Double> table, double x) {
        for (Map.Entry<Double, Double> entry : table.entrySet()) {
            if (distance(entry.getKey(), x) <= LOOKUP_EPS) {
                return entry.getValue();
            }
        }
        throw new IllegalArgumentException("No mocked table value for x = " + x);
    }

    private static Map<Double, Double> extractTable(CalculationFunction function) {
        LinkedHashMap<Double, Double> table = new LinkedHashMap<>();
        definedTrigBranchPoints().forEach(x -> putIfDefined(table, function, x));
        definedLogBranchPoints().forEach(x -> putIfDefined(table, function, x));
        return table;
    }

    private static void putIfDefined(Map<Double, Double> table, CalculationFunction function, double x) {
        try {
            table.put(x, function.calculate(x));
        } catch (IllegalArgumentException ignored) {
        }
    }

    @SafeVarargs
    private static Map<Double, Double> mapOf(Map.Entry<Double, Double>... entries) {
        LinkedHashMap<Double, Double> result = new LinkedHashMap<>();
        for (Map.Entry<Double, Double> entry : entries) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    private static Map.Entry<Double, Double> point(double x, double y) {
        return Map.entry(x, y);
    }

    private static double tolerance(double expected) {
        double magnitude = expected < 0.0 ? -expected : expected;
        return 1.0E-6 + magnitude * 1.0E-6;
    }

    private static double distance(double left, double right) {
        double value = left - right;
        return value < 0.0 ? -value : value;
    }
}
