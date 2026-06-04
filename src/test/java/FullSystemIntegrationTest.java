import Interfaces.CalculationFunction;
import StubGeneration.StubModules;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class FullSystemIntegrationTest {
    private final CalculationFunction expectedSystem = StubModules.systemStub();
    private final CalculationFunction system = IntegrationTestSupport.fullRealSystem();

    @ParameterizedTest(name = "full real system at x={0}")
    @MethodSource("IntegrationTestSupport#definedSystemPoints")
    void matchesSystemTableWhenEveryModuleIsReal(double x) {
        IntegrationTestSupport.assertMatches(expectedSystem, system, x);
    }

    @Test
    void fullRealSystemIsUndefinedAtTrigBoundaryZero() {
        IntegrationTestSupport.assertUndefined(system, 0.0);
    }

    @Test
    void fullRealSystemIsUndefinedAtLogBoundaryOne() {
        IntegrationTestSupport.assertUndefined(system, 1.0);
    }
}
