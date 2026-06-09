import Interfaces.CalculationFunction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class TrigBranchIntegrationTest {

    private final CalculationFunction expectedBranch = IntegrationTestSupport.trigBranchStub();

    @ParameterizedTest(name = "level 1: only trig branch real, case {index}")
    @MethodSource("IntegrationTestSupport#definedTrigBranchPoints")
    void level1_onlyTrigBranchReal(double x) {
        CalculationFunction branch = IntegrationTestSupport.trigBranchWithStubDependencies();

        IntegrationTestSupport.assertMatches(expectedBranch, branch, x);
    }

    @ParameterizedTest(name = "level 1-2: trig branch, tan, cot, sec real, case {index}")
    @MethodSource("IntegrationTestSupport#definedTrigBranchPoints")
    void level1And2_trigBranchTanCotSecReal(double x) {
        CalculationFunction branch = IntegrationTestSupport.trigBranchWithLevel2Real();

        IntegrationTestSupport.assertMatches(expectedBranch, branch, x);
    }

    @ParameterizedTest(name = "level 1-3: trig branch, tan, cot, sec, cos, csc real, case {index}")
    @MethodSource("IntegrationTestSupport#definedTrigBranchPoints")
    void level1To3_trigBranchTanCotSecCosCscReal(double x) {
        CalculationFunction branch = IntegrationTestSupport.trigBranchWithLevel2AndLevel3Real();

        IntegrationTestSupport.assertMatches(expectedBranch, branch, x);
    }

    @ParameterizedTest(name = "all levels: fully real trig branch, case {index}")
    @MethodSource("IntegrationTestSupport#definedTrigBranchPoints")
    void allLevels_fullyRealTrigBranch(double x) {
        CalculationFunction branch = IntegrationTestSupport.trigBranchWithAllRealTrigModules();

        IntegrationTestSupport.assertMatches(expectedBranch, branch, x);
    }

    @ParameterizedTest(name = "fully real trig branch undefined point, case {index}")
    @MethodSource("IntegrationTestSupport#undefinedTrigBranchPoints")
    void fullyRealTrigBranchIsUndefinedAtSpecialPoints(double x) {
        CalculationFunction branch = IntegrationTestSupport.trigBranchWithAllRealTrigModules();

        IntegrationTestSupport.assertUndefined(branch, x);
    }
}
