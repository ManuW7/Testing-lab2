import Interfaces.CalculationFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class LogBranchWithStubDependenciesIntegrationTest {
    private final CalculationFunction expectedBranch = IntegrationTestSupport.logBranchStub();
    private final CalculationFunction branch = IntegrationTestSupport.logBranchWithStubDependencies();

    @ParameterizedTest(name = "log branch with all dependency stubs at x={0}")
    @MethodSource("IntegrationTestSupport#definedLogBranchPoints")
    void matchesLogBranchTableWhenAllDependenciesAreStubs(double x) {
        IntegrationTestSupport.assertMatches(expectedBranch, branch, x);
    }

    @Test
    void logBranchWithStubDependenciesIsUndefinedAtOne() {
        IntegrationTestSupport.assertUndefined(branch, 1.0);
    }
}
