import Interfaces.CalculationFunction;
import LogarithmicFunctions.LnCalc;
import LogarithmicFunctions.Log10Calc;
import LogarithmicFunctions.Log2Calc;
import LogarithmicFunctions.Log5Calc;
import MainFunctions.LogarythmicFunction;
import MainFunctions.SystemFunction;
import MainFunctions.TrigonometryFunction;
import StubGeneration.StubModules;
import TrigonometryFunctions.CosCalc;
import TrigonometryFunctions.CotCalc;
import TrigonometryFunctions.CscCalc;
import TrigonometryFunctions.SecCalc;
import TrigonometryFunctions.SinCalc;
import TrigonometryFunctions.TanCalc;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class IntegrationTestSupport {
    static final double EPS = 1.0E-12;

    private IntegrationTestSupport() {
    }

    static Stream<Double> definedTrigBranchPoints() {
        return Stream.of(
                -2.0 * Math.PI - 0.1,
                -2.0 * Math.PI - 0.01,
                -3.0 * Math.PI / 2.0 - 0.1,
                -3.0 * Math.PI / 2.0 + 0.1,
                -Math.PI - 0.1,
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

    /*
     * Ниже идут фабрики реальных модулей.
     * Они нужны, чтобы в интеграционных тестах можно было явно собрать систему:
     * сначала только из заглушек, затем с одним реальным модулем, затем с несколькими,
     * и в конце полностью из реальных реализаций.
     */
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

    /*
     * Уровень 1 для тригонометрической ветки.
     *
     * Реальная только сама формула TrigonometryFunction.
     * Все ее зависимости пока табличные:
     * sin, cos, tan, cot, sec, csc.
     *
     * Такой тест отвечает на вопрос:
     * правильно ли TrigonometryFunction склеивает значения зависимостей по формуле,
     * если сами зависимости считаются "идеальными" таблицами?
     */
    static CalculationFunction trigBranchWithStubDependencies() {
        return new TrigonometryFunction(
                StubModules.sinStub(),
                StubModules.cosStub(),
                StubModules.tanStub(),
                StubModules.cotStub(),
                StubModules.secStub(),
                StubModules.cscStub()
        );
    }

    /*
     * Уровень 2 для тригонометрической ветки.
     *
     * Вместо sinStub подставляется настоящий SinCalc.
     * Остальные зависимости остаются табличными.
     *
     * Если тесты прошли, значит реальный sin совместим с веткой и с теми точками,
     * которые ожидаются в таблицах.
     */
    static CalculationFunction trigBranchWithRealSin() {
        return new TrigonometryFunction(
                realSin(),
                StubModules.cosStub(),
                StubModules.tanStub(),
                StubModules.cotStub(),
                StubModules.secStub(),
                StubModules.cscStub()
        );
    }

    /*
     * Уровень 3 для тригонометрической ветки.
     *
     * Теперь real: sin и cos.
     * cos строится через уже реальный sin.
     * tan, cot, sec, csc пока остаются заглушками.
     *
     * Так мы проверяем следующий слой зависимостей, не смешивая сразу все модули.
     */
    static CalculationFunction trigBranchWithRealSinAndCos() {
        CalculationFunction sin = realSin();
        CalculationFunction cos = realCos(sin);
        return new TrigonometryFunction(
                sin,
                cos,
                StubModules.tanStub(),
                StubModules.cotStub(),
                StubModules.secStub(),
                StubModules.cscStub()
        );
    }

    /*
     * Финальный уровень для тригонометрической ветки.
     *
     * Все зависимости real:
     * sin -> cos -> tan/cot/sec/csc -> TrigonometryFunction.
     *
     * Результат сравнивается с trigBranchStub, то есть с CSV-эталоном ветки.
     */
    static CalculationFunction trigBranchWithAllRealTrigModules() {
        CalculationFunction sin = realSin();
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

    /*
     * Уровень 1 для логарифмической ветки.
     *
     * Реальная только формула LogarythmicFunction.
     * Все зависимости табличные:
     * ln, log2, log5, log10.
     */
    static CalculationFunction logBranchWithStubDependencies() {
        return new LogarythmicFunction(
                StubModules.lnStub(),
                StubModules.log2Stub(),
                StubModules.log5Stub(),
                StubModules.log10Stub()
        );
    }

    /*
     * Уровень 2 для логарифмической ветки.
     *
     * Вместо lnStub подставляется настоящий LnCalc.
     * Производные логарифмы log2/log5/log10 пока остаются заглушками.
     *
     * Так отдельно проверяется базовый логарифмический модуль в составе ветки.
     */
    static CalculationFunction logBranchWithRealLn() {
        return new LogarythmicFunction(
                realLn(),
                StubModules.log2Stub(),
                StubModules.log5Stub(),
                StubModules.log10Stub()
        );
    }

    /*
     * Финальный уровень для логарифмической ветки.
     *
     * Все зависимости real:
     * ln -> log2/log5/log10 -> LogarythmicFunction.
     *
     * Результат сравнивается с logBranchStub, то есть с CSV-эталоном ветки.
     */
    static CalculationFunction logBranchWithAllRealLogModules() {
        CalculationFunction ln = realLn();
        return new LogarythmicFunction(
                ln,
                realLog2(ln),
                realLog5(ln),
                realLog10(ln)
        );
    }

    /*
     * Уровень 1 для всей системы.
     *
     * Реальный только SystemFunction.
     * Обе ветки пока табличные:
     * trigBranchStub и logBranchStub.
     *
     * Такой тест проверяет только выбор ветки:
     * x <= 0 должен идти в trig, x > 0 должен идти в log.
     */
    static CalculationFunction systemWithBranchStubs() {
        return new SystemFunction(StubModules.trigBranchStub(), StubModules.logBranchStub());
    }

    /*
     * Уровень 2а для всей системы.
     *
     * Тригонометрическая ветка полностью real.
     * Логарифмическая ветка еще stub.
     *
     * Так проверяется интеграция реальной trig-ветки с SystemFunction.
     */
    static CalculationFunction systemWithRealTrigBranch() {
        return new SystemFunction(trigBranchWithAllRealTrigModules(), StubModules.logBranchStub());
    }

    /*
     * Уровень 2б для всей системы.
     *
     * Логарифмическая ветка полностью real.
     * Тригонометрическая ветка еще stub.
     *
     * Так проверяется интеграция реальной log-ветки с SystemFunction.
     */
    static CalculationFunction systemWithRealLogBranch() {
        return new SystemFunction(StubModules.trigBranchStub(), logBranchWithAllRealLogModules());
    }

    /*
     * Финальный уровень для всей системы.
     *
     * Все real:
     * реальные базовые модули, реальные производные модули, реальные ветки,
     * реальный SystemFunction.
     *
     * Результат сравнивается с systemStub, то есть с CSV-эталоном всей системы.
     */
    static CalculationFunction fullRealSystem() {
        return new SystemFunction(trigBranchWithAllRealTrigModules(), logBranchWithAllRealLogModules());
    }

    /*
     * Общая проверка для интеграционных тестов.
     *
     * expected обычно является табличной заглушкой из StubModules.
     * actual является текущей интеграционной сборкой.
     *
     * Если actual совпал с expected на выбранной точке, значит текущая замена
     * stub -> real не изменила ожидаемое поведение.
     */
    static void assertMatches(CalculationFunction expected, CalculationFunction actual, double x) {
        double expectedValue = expected.calculate(x);
        double actualValue = actual.calculate(x);
        assertEquals(expectedValue, actualValue, tolerance(expectedValue));
    }

    static void assertUndefined(CalculationFunction function, double x) {
        assertThrows(IllegalArgumentException.class, () -> function.calculate(x));
    }

    private static double tolerance(double expected) {
        double magnitude = expected < 0.0 ? -expected : expected;
        return 1.0E-6 + magnitude * 1.0E-6;
    }
}
