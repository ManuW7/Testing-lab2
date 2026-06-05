import Interfaces.CalculationFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class LogBranchWithAllRealLogModulesIntegrationTest {
    private final CalculationFunction expectedBranch = IntegrationTestSupport.logBranchStub();
    private final CalculationFunction branch = IntegrationTestSupport.logBranchWithAllRealLogModules();

    @ParameterizedTest(name = "log branch with all real log modules at x={0}")
    @MethodSource("IntegrationTestSupport#definedLogBranchPoints")
    void matchesLogBranchTableWhenEveryLogModuleIsReal(double x) {
        IntegrationTestSupport.assertMatches(expectedBranch, branch, x);
    }

    @Test
    void logBranchWithAllRealLogModulesIsUndefinedAtOne() {
        IntegrationTestSupport.assertUndefined(branch, 1.0);
    }
}
