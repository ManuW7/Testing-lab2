import Interfaces.CalculationFunction;
import StubGeneration.StubModules;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class TrigBranchWithStubDependenciesIntegrationTest {
    private final CalculationFunction expectedBranch = StubModules.trigBranchStub();
    private final CalculationFunction branch = IntegrationTestSupport.trigBranchWithStubDependencies();

    @ParameterizedTest(name = "trig branch with all dependency stubs at x={0}")
    @MethodSource("IntegrationTestSupport#definedTrigBranchPoints")
    void matchesTrigBranchTableWhenAllDependenciesAreStubs(double x) {
        IntegrationTestSupport.assertMatches(expectedBranch, branch, x);
    }

    @Test
    void trigBranchWithStubDependenciesIsUndefinedAtZero() {
        IntegrationTestSupport.assertUndefined(branch, 0.0);
    }
}
