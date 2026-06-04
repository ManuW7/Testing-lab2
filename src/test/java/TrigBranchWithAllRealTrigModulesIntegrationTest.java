import Interfaces.CalculationFunction;
import StubGeneration.StubModules;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class TrigBranchWithAllRealTrigModulesIntegrationTest {
    private final CalculationFunction expectedBranch = StubModules.trigBranchStub();
    private final CalculationFunction branch = IntegrationTestSupport.trigBranchWithAllRealTrigModules();

    @ParameterizedTest(name = "trig branch with all real trig modules at x={0}")
    @MethodSource("IntegrationTestSupport#definedTrigBranchPoints")
    void matchesTrigBranchTableWhenEveryTrigModuleIsReal(double x) {
        IntegrationTestSupport.assertMatches(expectedBranch, branch, x);
    }

    @Test
    void trigBranchWithAllRealTrigModulesIsUndefinedAtZero() {
        IntegrationTestSupport.assertUndefined(branch, 0.0);
    }
}
