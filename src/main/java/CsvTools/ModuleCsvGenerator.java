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
import java.util.LinkedHashMap;
import java.util.Map;

public final class ModuleCsvGenerator {
    private static final double EPSILON = 1.0E-12;

    private ModuleCsvGenerator() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 5) {
            printUsage();
            throw new IllegalArgumentException("Expected arguments: module startX endX step outputPath");
        }

        String moduleName = args[0];
        double startX = Double.parseDouble(args[1]);
        double endX = Double.parseDouble(args[2]);
        double step = Double.parseDouble(args[3]);
        String outputPath = args[4];

        Map<String, CalculationFunction> modules = buildModules();
        CalculationFunction module = modules.get(moduleName);
        if (module == null) {
            printUsage();
            throw new IllegalArgumentException("Unknown module: " + moduleName);
        }

        new CsvExporter().export(module, startX, endX, step, outputPath, moduleName);
        System.out.println("Generated " + outputPath + " for module " + moduleName);
    }

    private static Map<String, CalculationFunction> buildModules() {
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

        LinkedHashMap<String, CalculationFunction> modules = new LinkedHashMap<>();
        modules.put("sin", sin);
        modules.put("cos", cos);
        modules.put("tan", tan);
        modules.put("cot", cot);
        modules.put("sec", sec);
        modules.put("csc", csc);
        modules.put("ln", ln);
        modules.put("log2", log2);
        modules.put("log5", log5);
        modules.put("log10", log10);
        modules.put("trig_branch", trigBranch);
        modules.put("log_branch", logBranch);
        modules.put("system", system);
        return modules;
    }

    private static void printUsage() {
        System.out.println("Usage: ModuleCsvGenerator <module> <startX> <endX> <step> <outputPath>");
        System.out.println("Modules: sin, cos, tan, cot, sec, csc, ln, log2, log5, log10, trig_branch, log_branch, system");
    }
}
