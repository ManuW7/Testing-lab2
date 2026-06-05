import Interfaces.CalculationFunction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SystemFunctionWithBranchStubsIntegrationTest {
    private final CalculationFunction expectedSystem = IntegrationTestSupport.systemStub();
    private final CalculationFunction system = IntegrationTestSupport.systemWithBranchStubs();

    @ParameterizedTest(name = "system with trig/log branch stubs at x={0}")
    @MethodSource("IntegrationTestSupport#definedSystemPoints")
    void matchesSystemTableWhenBothBranchesAreStubs(double x) {
        IntegrationTestSupport.assertMatches(expectedSystem, system, x);
    }
}
