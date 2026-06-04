import Interfaces.CalculationFunction;
import StubGeneration.StubModules;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class TrigBranchWithRealSinAndCosIntegrationTest {
    private final CalculationFunction expectedBranch = StubModules.trigBranchStub();
    private final CalculationFunction branch = IntegrationTestSupport.trigBranchWithRealSinAndCos();

    @ParameterizedTest(name = "trig branch with real sin/cos and other stubs at x={0}")
    @MethodSource("IntegrationTestSupport#definedTrigBranchPoints")
    void matchesTrigBranchTableWhenSinAndCosAreReal(double x) {
        IntegrationTestSupport.assertMatches(expectedBranch, branch, x);
    }
}
