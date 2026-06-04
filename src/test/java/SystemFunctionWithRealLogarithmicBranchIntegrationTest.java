import Interfaces.CalculationFunction;
import StubGeneration.StubModules;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SystemFunctionWithRealLogarithmicBranchIntegrationTest {
    private final CalculationFunction expectedSystem = StubModules.systemStub();
    private final CalculationFunction system = IntegrationTestSupport.systemWithRealLogBranch();

    @ParameterizedTest(name = "system with trig stub and real logarithmic branch at x={0}")
    @MethodSource("IntegrationTestSupport#definedSystemPoints")
    void matchesSystemTableWhenLogarithmicBranchIsReal(double x) {
        IntegrationTestSupport.assertMatches(expectedSystem, system, x);
    }
}
