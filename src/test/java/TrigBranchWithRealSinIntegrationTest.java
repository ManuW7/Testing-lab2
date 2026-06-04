import Interfaces.CalculationFunction;
import StubGeneration.StubModules;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class TrigBranchWithRealSinIntegrationTest {
    private final CalculationFunction expectedBranch = StubModules.trigBranchStub();
    private final CalculationFunction branch = IntegrationTestSupport.trigBranchWithRealSin();

    @ParameterizedTest(name = "trig branch with real sin and other stubs at x={0}")
    @MethodSource("IntegrationTestSupport#definedTrigBranchPoints")
    void matchesTrigBranchTableWhenSinIsReal(double x) {
        IntegrationTestSupport.assertMatches(expectedBranch, branch, x);
    }
}
