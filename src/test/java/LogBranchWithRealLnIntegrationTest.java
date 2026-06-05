import Interfaces.CalculationFunction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class LogBranchWithRealLnIntegrationTest {
    private final CalculationFunction expectedBranch = IntegrationTestSupport.logBranchStub();
    private final CalculationFunction branch = IntegrationTestSupport.logBranchWithRealLn();

    @ParameterizedTest(name = "log branch with real ln and derived log stubs at x={0}")
    @MethodSource("IntegrationTestSupport#definedLogBranchPoints")
    void matchesLogBranchTableWhenLnIsReal(double x) {
        IntegrationTestSupport.assertMatches(expectedBranch, branch, x);
    }
}
