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

    private IntegrationTestSupport() {
    }

    static Stream<Double> definedTrigBranchPoints() {
        return Stream.of(
                -6.293185307179586,
                -6.273185307179586,
                -5.88213,
                -5.87213,
                -5.86213,
                -5.784623946010794,
                -5.764623946010794,
                -5.72718,
                -5.71718,
                -5.70718,
                -4.72238898038469,
                -4.70238898038469,
                -3.915741466221586,
                -3.895741466221587,
                -3.151592653589793,
                -3.131592653589793,
                -1.5807963267948966,
                -1.5607963267948965,
                -0.48269,
                -0.47269,
                -0.46269,
                -0.01
        );
    }

    static Stream<Double> definedLogBranchPoints() {
        return Stream.of(
                1.0E-6,
                0.1,
                0.99,
                1.01,
                2.79,
                2.8,
                2.81,
                9.99,
                10.0,
                10.01,
                100.0
        );
    }

    static Stream<Double> definedSystemPoints() {
        return Stream.concat(definedTrigBranchPoints(), definedLogBranchPoints());
    }

    static Stream<Double> undefinedTrigBranchPoints() {
        return Stream.of(
                -2.0 * Math.PI,
                -5.774623946010794,
                -3.0 * Math.PI / 2.0,
                -3.9057414662215866,
                -Math.PI,
                -Math.PI / 2.0,
                0.0
        );
    }

    static Stream<Double> undefinedLogBranchPoints() {
        return Stream.of(
                -1.0,
                0.0,
                1.0
        );
    }

    static Stream<Double> undefinedSystemPoints() {
        return Stream.concat(undefinedTrigBranchPoints(), Stream.of(1.0));
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
                point(7.8639816339744826, 0.99995000041666526),
                point(7.843981633974483, 0.99995000041666526),
                point(7.4529263267948966, 0.9206495252262179),
                point(7.4429263267948969, 0.91669965692259869),
                point(7.4329263267948971, 0.91265811941720099),
                point(7.3554202728056906, 0.87827137369084263),
                point(7.335420272805691, 0.86853311243725217),
                point(7.2979763267948963, 0.84937027100340412),
                point(7.2879763267948965, 0.84404991636157523),
                point(7.2779763267948967, 0.83864515743148282),
                point(6.293185307179586, 0.0099998333341662672),
                point(6.2731853071795864, -0.009999833334166635),
                point(5.4865377930164829, -0.71501636466255303),
                point(5.4665377930164833, -0.72885458834697725),
                point(4.7223889803846895, -0.99995000041666526),
                point(4.7023889803846899, -0.99995000041666526),
                point(3.1515926535897929, -0.0099998333341666124),
                point(3.1315926535897933, 0.0099998333341667356),
                point(2.0534863267948964, 0.88574952910512883),
                point(2.0434863267948966, 0.89034679982020559),
                point(2.0334863267948964, 0.89485503659725341),
                point(1.5807963267948966, 0.99995000041666526),
                point(-6.293185307179586, -0.0099998333341662065),
                point(-6.2731853071795864, 0.0099998333341666957),
                point(-5.8821300000000001, 0.39039012756566921),
                point(-5.8721300000000003, 0.39957695003339461),
                point(-5.8621300000000005, 0.40872381513909634),
                point(-5.784623946010794, 0.47816251855953767),
                point(-5.7646239460107944, 0.49563114571227201),
                point(-5.7271799999999997, 0.52779744479828983),
                point(-5.7171799999999999, 0.53626461629499467),
                point(-5.7071800000000001, 0.5446781617769556),
                point(-4.7223889803846895, 0.99995000041666526),
                point(-4.7023889803846899, 0.99995000041666526),
                point(-3.9157414662215864, 0.69910771578115694),
                point(-3.8957414662215868, 0.68466852494149189),
                point(-3.1515926535897929, 0.0099998333341663297),
                point(-3.1315926535897933, -0.0099998333341665743),
                point(-1.5807963267948966, -0.99995000041666526),
                point(-1.5607963267948965, -0.99995000041666526),
                point(-0.48269000000000001, -0.46416351826704616),
                point(-0.47269, -0.45528296261766571),
                point(-0.46268999999999999, -0.44635687905142474),
                point(-0.01, -0.0099998333341666645)
        ));
    }

    static CalculationFunction cosStub() {
        return mockTable("cos", mapOf(
                point(-6.293185307179586, 0.99995000041666526),
                point(-6.2731853071795864, 0.99995000041666526),
                point(-5.8821300000000001, 0.9206495252262179),
                point(-5.8721300000000003, 0.91669965692259869),
                point(-5.8621300000000005, 0.91265811941720099),
                point(-5.784623946010794, 0.87827137369084263),
                point(-5.7646239460107944, 0.86853311243725217),
                point(-5.7271799999999997, 0.84937027100340412),
                point(-5.7171799999999999, 0.84404991636157523),
                point(-5.7071800000000001, 0.83864515743148282),
                point(-4.7223889803846895, 0.0099998333341662672),
                point(-4.7023889803846899, -0.009999833334166635),
                point(-3.9157414662215864, -0.71501636466255303),
                point(-3.8957414662215868, -0.72885458834697725),
                point(-3.1515926535897929, -0.99995000041666526),
                point(-3.1315926535897933, -0.99995000041666526),
                point(-1.5807963267948966, -0.0099998333341666124),
                point(-1.5607963267948965, 0.0099998333341667356),
                point(-0.48269000000000001, 0.88574952910512883),
                point(-0.47269, 0.89034679982020559),
                point(-0.46268999999999999, 0.89485503659725341),
                point(-0.01, 0.99995000041666526)
        ));
    }

    static CalculationFunction tanStub() {
        return mockTable("tan", mapOf(
                point(-6.293185307179586, -0.010000333346666748),
                point(-6.2731853071795864, 0.010000333346667238),
                point(-5.8821300000000001, 0.42403772213942575),
                point(-5.8721300000000003, 0.43588644003074262),
                point(-5.8621300000000005, 0.44783890752004313),
                point(-5.784623946010794, 0.54443596009523831),
                point(-5.7646239460107944, 0.57065313758901648),
                point(-5.7271799999999997, 0.6213985382073427),
                point(-5.7171799999999999, 0.63534704038199186),
                point(-5.7071800000000001, 0.6494739246395228),
                point(-4.7223889803846895, 99.996666644448197),
                point(-4.7023889803846899, -99.996666644444531),
                point(-3.9157414662215864, -0.97775065065971734),
                point(-3.8957414662215868, -0.93937602354168048),
                point(-3.1515926535897929, -0.010000333346666871),
                point(-3.1315926535897933, 0.010000333346667115),
                point(-1.5807963267948966, 99.996666644444758),
                point(-1.5607963267948965, -99.996666644443536),
                point(-0.48269000000000001, -0.52403473331336647),
                point(-0.47269, -0.51135463474412934),
                point(-0.46268999999999999, -0.49880356124353592),
                point(-0.01, -0.010000333346667207)
        ));
    }

    static CalculationFunction cotStub() {
        return mockTable("cot", mapOf(
                point(-6.293185307179586, -99.996666644448808),
                point(-6.2731853071795864, 99.99666664444392),
                point(-5.8821300000000001, 2.3582807561427166),
                point(-5.8721300000000003, 2.2941755194987739),
                point(-5.8621300000000005, 2.2329457829772079),
                point(-5.784623946010794, 1.8367633170760247),
                point(-5.7646239460107944, 1.7523779930921863),
                point(-5.7271799999999997, 1.6092731773796496),
                point(-5.7171799999999999, 1.5739429578498807),
                point(-5.7071800000000001, 1.539707695817087),
                point(-4.7223889803846895, 0.010000333346666808),
                point(-4.7023889803846899, -0.010000333346667176),
                point(-3.9157414662215864, -1.0227556476953203),
                point(-3.8957414662215868, -1.0645364315662988),
                point(-3.1515926535897929, -99.996666644447586),
                point(-3.1315926535897933, 99.996666644445142),
                point(-1.5807963267948966, 0.010000333346667153),
                point(-1.5607963267948965, -0.010000333346667277),
                point(-0.48269000000000001, -1.9082704569546385),
                point(-0.47269, -1.9555899801326295),
                point(-0.46268999999999999, -2.0047972342197449),
                point(-0.01, -99.996666644444232)
        ));
    }

    static CalculationFunction secStub() {
        return mockTable("sec", mapOf(
                point(-6.293185307179586, 1.000050002083418),
                point(-6.2731853071795864, 1.000050002083418),
                point(-5.8821300000000001, 1.0861896656648842),
                point(-5.8721300000000003, 1.0908698311909968),
                point(-5.8621300000000005, 1.0957005462665179),
                point(-5.784623946010794, 1.1386002435643618),
                point(-5.7646239460107944, 1.1513665808247993),
                point(-5.7271799999999997, 1.177342831670632),
                point(-5.7171799999999999, 1.1847640531861847),
                point(-5.7071800000000001, 1.192399420826203),
                point(-4.7223889803846895, 100.00166668611529),
                point(-4.7023889803846899, -100.00166668611162),
                point(-3.9157414662215864, -1.398569388648808),
                point(-3.8957414662215868, -1.3720157847506638),
                point(-3.1515926535897929, -1.000050002083418),
                point(-3.1315926535897933, -1.000050002083418),
                point(-1.5807963267948966, -100.00166668611183),
                point(-1.5607963267948965, 100.00166668611061),
                point(-0.48269000000000001, 1.1289873346139943),
                point(-0.47269, 1.1231578528747872),
                point(-0.46268999999999999, 1.1174994374536544),
                point(-0.01, 1.000050002083418)
        ));
    }

    static CalculationFunction cscStub() {
        return mockTable("csc", mapOf(
                point(-6.293185307179586, -100.0016666861159),
                point(-6.2731853071795864, 100.00166668611101),
                point(-5.8821300000000001, 2.5615401860585876),
                point(-5.8721300000000003, 2.5026468616781452),
                point(-5.8621300000000005, 2.4466399141916439),
                point(-5.784623946010794, 2.0913391601928466),
                point(-5.7646239460107944, 2.0176294582191741),
                point(-5.7271799999999997, 1.8946662395877523),
                point(-5.7171799999999999, 1.8647510382260768),
                point(-5.7071800000000001, 1.8359465647339421),
                point(-4.7223889803846895, 1.000050002083418),
                point(-4.7023889803846899, 1.000050002083418),
                point(-3.9157414662215864, 1.4303947409343598),
                point(-3.8957414662215868, 1.4605607875511069),
                point(-3.1515926535897929, 100.00166668611466),
                point(-3.1315926535897933, -100.00166668611222),
                point(-1.5807963267948966, -1.000050002083418),
                point(-1.5607963267948965, -1.000050002083418),
                point(-0.48269000000000001, -2.1544131769198462),
                point(-0.47269, -2.1964362431892117),
                point(-0.46268999999999999, -2.2403597814492069),
                point(-0.01, -100.00166668611132)
        ));
    }

    static CalculationFunction lnStub() {
        return mockTable("ln", mapOf(
                point(1.0E-6, -13.815510557964274),
                point(0.1, -2.3025850929940455),
                point(0.99, -0.010050335853501451),
                point(1.01, 0.009950330853168092),
                point(2.79, 1.0260415958332743),
                point(2.8, 1.0296194171811581),
                point(2.81, 1.0331844833456545),
                point(9.99, 2.3015845926604621),
                point(10.0, 2.3025850929940459),
                point(10.01, 2.3035845933271291),
                point(100.0, 4.6051701859880918),
                point(2.0, 0.69314718055994529),
                point(5.0, 1.6094379124341003)
        ));
    }

    static CalculationFunction log2Stub() {
        return mockTable("log2", mapOf(
                point(1.0E-6, -19.931568569324174),
                point(0.1, -3.3219280948873622),
                point(0.99, -0.014499569695115091),
                point(1.01, 0.014355292977070055),
                point(2.79, 1.4802651220544629),
                point(2.8, 1.4854268271702415),
                point(2.81, 1.4905701304462016),
                point(9.99, 3.3204846780176935),
                point(10.0, 3.3219280948873626),
                point(10.01, 3.3233700690612689),
                point(100.0, 6.6438561897747253)
        ));
    }

    static CalculationFunction log5Stub() {
        return mockTable("log5", mapOf(
                point(1.0E-6, -8.5840593484403591),
                point(0.1, -1.4306765580733929),
                point(0.99, -0.0062446247698374445),
                point(1.01, 0.0061824881694996833),
                point(2.79, 0.63751548780250722),
                point(2.8, 0.63973851319556052),
                point(2.81, 0.64195361334757861),
                point(9.99, 1.4300549122640991),
                point(10.0, 1.4306765580733933),
                point(10.01, 1.4312975825474419),
                point(100.0, 2.8613531161467867)
        ));
    }

    static CalculationFunction log10Stub() {
        return mockTable("log10", mapOf(
                point(1.0E-6, -5.9999999999999991),
                point(0.1, -0.99999999999999978),
                point(0.99, -0.0043648054024500883),
                point(1.01, 0.0043213737826425782),
                point(2.79, 0.44560420327359751),
                point(2.8, 0.44715803134221915),
                point(2.81, 0.44870631990507986),
                point(9.99, 0.99956548822598223),
                point(10.0, 1.0),
                point(10.01, 1.0004340774793186),
                point(100.0, 2.0)
        ));
    }

    static CalculationFunction trigBranchStub() {
        return mockTable("trig_branch", mapOf(
                point(-6.293185307179586, -10000.353339677711),
                point(-6.2731853071795864, -10000.313340343399),
                point(-5.8821300000000001, -6.6005118573668122),
                point(-5.8721300000000003, -6.5467873071035774),
                point(-5.8621300000000005, -6.6116403174489964),
                point(-5.784623946010794, -52.236245047517315),
                point(-5.7646239460107944, 65.650168114239591),
                point(-5.7271799999999997, 24.845431344051654),
                point(-5.7171799999999999, 24.384245857221206),
                point(-5.7071800000000001, 24.744729623021293),
                point(-4.7223889803846895, 1.0005334820293123E22),
                point(-4.7023889803846899, -1.0005334820289287E22),
                point(-3.9157414662215864, -3024.3406965500085),
                point(-3.8957414662215868, 2346.5571801500273),
                point(-3.1515926535897929, -10000.313340344081),
                point(-3.1315926535897933, -10000.353339676927),
                point(-1.5807963267948966, 1.0005334820289308E22),
                point(-1.5607963267948965, -1.0005334820288165E22),
                point(-0.48269000000000001, -6.6743622309746202),
                point(-0.47269, -6.6515420673060461),
                point(-0.46268999999999999, -6.6726554523848467),
                point(-0.01, -10000.353339676796)
        ));
    }

    static CalculationFunction logBranchStub() {
        return mockTable("log_branch", mapOf(
                point(1.0E-6, 0.70569221907463431),
                point(0.1, -6.000023754655853),
                point(0.99, -1841.5311541019671),
                point(1.01, 1864.1535573081298),
                point(2.79, 20.105146150929905),
                point(2.8, 20.042395382457755),
                point(2.81, 19.980300603655433),
                point(9.99, 10.097192557261184),
                point(10.0, 10.093694582297314),
                point(10.01, 10.090203137510581),
                point(100.0, 6.0702649980590238)
        ));
    }

    static CalculationFunction systemStub() {
        LinkedHashMap<Double, Double> table = new LinkedHashMap<>();
        table.putAll(extractTable(trigBranchStub(), definedTrigBranchPoints()));
        table.putAll(extractTable(logBranchStub(), definedLogBranchPoints()));
        return mockTable("system", table);
    }

    static CalculationFunction trigBranchWithStubDependencies() {
        return new TrigonometryFunction(sinStub(), cosStub(), tanStub(), cotStub(), secStub(), cscStub());
    }

    static CalculationFunction trigBranchWithLevel2Real() {
        CalculationFunction sin = sinStub();
        CalculationFunction cos = cosStub();
        return new TrigonometryFunction(
                sin,
                cos,
                realTan(sin, cos),
                realCot(sin, cos),
                realSec(cos),
                cscStub()
        );
    }

    static CalculationFunction trigBranchWithLevel2AndLevel3Real() {
        CalculationFunction sin = sinStub();
        CalculationFunction cos = realCos(sin);
        return new TrigonometryFunction(
                sin,
                cos,
                realTan(sin, cos),
                realCot(sin, cos),
                realSec(cos),
                realCsc(sin)
        );
    }

    static CalculationFunction trigBranchWithAllRealTrigModules() {
        CalculationFunction sin = realSin();
        CalculationFunction cos = realCos(sin);
        return new TrigonometryFunction(sin, cos, realTan(sin, cos), realCot(sin, cos), realSec(cos), realCsc(sin));
    }

    static CalculationFunction logBranchWithStubDependencies() {
        return new LogarythmicFunction(lnStub(), log2Stub(), log5Stub(), log10Stub());
    }

    static CalculationFunction logBranchWithLevel2Real() {
        CalculationFunction ln = lnStub();
        return new LogarythmicFunction(ln, realLog2(ln), realLog5(ln), realLog10(ln));
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

    private static Map<Double, Double> extractTable(CalculationFunction function, Stream<Double> points) {
        LinkedHashMap<Double, Double> table = new LinkedHashMap<>();
        points.forEach(x -> table.put(x, function.calculate(x)));
        return table;
    }

    private static double lookup(Map<Double, Double> table, double x) {
        for (Map.Entry<Double, Double> entry : table.entrySet()) {
            if (distance(entry.getKey(), x) <= LOOKUP_EPS) {
                return entry.getValue();
            }
        }
        throw new IllegalArgumentException("No mocked table value for x = " + x);
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
