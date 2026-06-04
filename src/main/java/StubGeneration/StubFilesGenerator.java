package StubGeneration;

import Interfaces.CalculationFunction;
import LogarithmicFunctions.LnCalc;
import LogarithmicFunctions.Log10Calc;
import LogarithmicFunctions.Log2Calc;
import LogarithmicFunctions.Log5Calc;
import MainFunctions.LogarythmicFunction;
import MainFunctions.SystemFunction;
import MainFunctions.TrigonometryFunction;
import TrigonometryFunctions.CosCalc;
import TrigonometryFunctions.CotCalc;
import TrigonometryFunctions.CscCalc;
import TrigonometryFunctions.SecCalc;
import TrigonometryFunctions.SinCalc;
import TrigonometryFunctions.TanCalc;

import java.io.IOException;

public final class StubFilesGenerator {
    private static final double EPSILON = 1.0E-12;
    private static final String OUTPUT_DIRECTORY = "src/main/resources/lab2/stub";

    private StubFilesGenerator() {
    }

    public static void main(String[] args) throws IOException {
        CsvExporter exporter = new CsvExporter();

        CalculationFunction sin = new SinCalc(EPSILON);
        CalculationFunction cos = new CosCalc(sin);
        CalculationFunction tan = new TanCalc(sin, cos);
        CalculationFunction cot = new CotCalc(sin, cos);
        CalculationFunction sec = new SecCalc(cos);
        CalculationFunction csc = new CscCalc(sin);

        CalculationFunction ln = new LnCalc(EPSILON);
        CalculationFunction log2 = new Log2Calc(ln);
        CalculationFunction log5 = new Log5Calc(ln);
        CalculationFunction log10 = new Log10Calc(ln);

        CalculationFunction trigBranch = new TrigonometryFunction(sin, cos, tan, cot, sec, csc);
        CalculationFunction logBranch = new LogarythmicFunction(ln, log2, log5, log10);
        CalculationFunction system = new SystemFunction(trigBranch, logBranch);

        exporter.exportPointColumns(
                StubModules.TRIG_FUNCTION_POINTS,
                OUTPUT_DIRECTORY + "/trig-functions.csv",
                new String[]{"sin", "cos", "tan", "cot", "sec", "csc"},
                new CalculationFunction[]{sin, cos, tan, cot, sec, csc}
        );
        exporter.exportPointColumns(
                StubModules.LOG_POINTS,
                OUTPUT_DIRECTORY + "/log-functions.csv",
                new String[]{"ln", "log2", "log5", "log10"},
                new CalculationFunction[]{ln, log2, log5, log10}
        );
        exporter.exportPoints(
                trigBranch,
                StubModules.TRIG_BRANCH_POINTS,
                OUTPUT_DIRECTORY + "/trig-branch.csv",
                "trig_branch"
        );
        exporter.exportPoints(
                logBranch,
                StubModules.LOG_POINTS,
                OUTPUT_DIRECTORY + "/log-branch.csv",
                "log_branch"
        );
        exporter.exportPoints(
                system,
                StubModules.SYSTEM_POINTS,
                OUTPUT_DIRECTORY + "/system.csv",
                "system"
        );

        System.out.println("Generated stub CSV files in " + OUTPUT_DIRECTORY);
    }
}
