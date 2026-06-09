import Interfaces.CalculationFunction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class LogBranchIntegrationTest {

    private final CalculationFunction expectedBranch = IntegrationTestSupport.logBranchStub();

    @ParameterizedTest(name = "level 1: only log branch real, case {index}")
    @MethodSource("IntegrationTestSupport#definedLogBranchPoints")
    void level1_onlyLogBranchReal(double x) {
        CalculationFunction branch = IntegrationTestSupport.logBranchWithStubDependencies();

        IntegrationTestSupport.assertMatches(expectedBranch, branch, x);
    }

    @ParameterizedTest(name = "level 1-2: log branch, log2, log5, log10 real, case {index}")
    @MethodSource("IntegrationTestSupport#definedLogBranchPoints")
    void level1And2_logBranchLog2Log5Log10Real(double x) {
        CalculationFunction branch = IntegrationTestSupport.logBranchWithLevel2Real();

        IntegrationTestSupport.assertMatches(expectedBranch, branch, x);
    }

    @ParameterizedTest(name = "all levels: fully real log branch, case {index}")
    @MethodSource("IntegrationTestSupport#definedLogBranchPoints")
    void allLevels_fullyRealLogBranch(double x) {
        CalculationFunction branch = IntegrationTestSupport.logBranchWithAllRealLogModules();

        IntegrationTestSupport.assertMatches(expectedBranch, branch, x);
    }

    @ParameterizedTest(name = "fully real log branch undefined point, case {index}")
    @MethodSource("IntegrationTestSupport#undefinedLogBranchPoints")
    void fullyRealLogBranchIsUndefinedAtSpecialPoints(double x) {
        CalculationFunction branch = IntegrationTestSupport.logBranchWithAllRealLogModules();

        IntegrationTestSupport.assertUndefined(branch, x);
    }
}
