import Interfaces.CalculationFunction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SystemIntegrationTest {

    private final CalculationFunction expectedSystem = IntegrationTestSupport.systemStub();

    @ParameterizedTest(name = "system level 1: both branches stub, case {index}")
    @MethodSource("IntegrationTestSupport#definedSystemPoints")
    void level1_bothBranchesStub(double x) {
        CalculationFunction system = IntegrationTestSupport.systemWithBranchStubs();

        IntegrationTestSupport.assertMatches(expectedSystem, system, x);
    }

    @ParameterizedTest(name = "system level 2: real trig branch and stub log branch, case {index}")
    @MethodSource("IntegrationTestSupport#definedSystemPoints")
    void level2_realTrigBranchStubLogBranch(double x) {
        CalculationFunction system = IntegrationTestSupport.systemWithRealTrigBranch();

        IntegrationTestSupport.assertMatches(expectedSystem, system, x);
    }

    @ParameterizedTest(name = "system level 3: stub trig branch and real log branch, case {index}")
    @MethodSource("IntegrationTestSupport#definedSystemPoints")
    void level3_stubTrigBranchRealLogBranch(double x) {
        CalculationFunction system = IntegrationTestSupport.systemWithRealLogBranch();

        IntegrationTestSupport.assertMatches(expectedSystem, system, x);
    }

    @ParameterizedTest(name = "system level 4: fully real system, case {index}")
    @MethodSource("IntegrationTestSupport#definedSystemPoints")
    void level4_fullyRealSystem(double x) {
        CalculationFunction system = IntegrationTestSupport.fullRealSystem();

        IntegrationTestSupport.assertMatches(expectedSystem, system, x);
    }

    @ParameterizedTest(name = "fully real system undefined point, case {index}")
    @MethodSource("IntegrationTestSupport#undefinedSystemPoints")
    void fullyRealSystemIsUndefinedAtSpecialPoints(double x) {
        CalculationFunction system = IntegrationTestSupport.fullRealSystem();

        IntegrationTestSupport.assertUndefined(system, x);
    }
}
