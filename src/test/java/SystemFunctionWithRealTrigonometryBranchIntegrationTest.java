import Interfaces.CalculationFunction;
import StubGeneration.StubModules;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SystemFunctionWithRealTrigonometryBranchIntegrationTest {
    private final CalculationFunction expectedSystem = StubModules.systemStub();
    private final CalculationFunction system = IntegrationTestSupport.systemWithRealTrigBranch();

    @ParameterizedTest(name = "system with real trigonometry branch and log stub at x={0}")
    @MethodSource("IntegrationTestSupport#definedSystemPoints")
    void matchesSystemTableWhenTrigonometryBranchIsReal(double x) {
        IntegrationTestSupport.assertMatches(expectedSystem, system, x);
    }
}
