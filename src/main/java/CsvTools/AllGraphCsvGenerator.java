package CsvTools;

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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public final class AllGraphCsvGenerator {
    private static final double EPSILON = 1.0E-12;

    private AllGraphCsvGenerator() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            throw new IllegalArgumentException("Usage: AllGraphCsvGenerator [outputDirectory]");
        }

        Path outputDirectory = Paths.get(args.length == 0 ? "csv-exports" : args[0]);
        CsvExporter exporter = new CsvExporter();
        Map<String, CalculationFunction> modules = buildModules();

        double[] trigPoints = trigGraphPoints();
        double[] logPoints = logGraphPoints();
        double[] systemPoints = systemGraphPoints(trigPoints, logPoints);

        export(exporter, outputDirectory, modules, trigPoints, "sin", "cos", "tan", "cot", "sec", "csc", "trig_branch");
        export(exporter, outputDirectory, modules, logPoints, "ln", "log2", "log5", "log10", "log_branch");
        export(exporter, outputDirectory, modules, systemPoints, "system");
    }

    private static void export(
            CsvExporter exporter,
            Path outputDirectory,
            Map<String, CalculationFunction> modules,
            double[] points,
            String... moduleNames
    ) throws IOException {
        for (String moduleName : moduleNames) {
            Path outputPath = outputDirectory.resolve(moduleName + ".csv");
            exporter.exportPoints(modules.get(moduleName), points, outputPath.toString(), moduleName);
            System.out.println("Generated " + outputPath);
        }
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

    private static double[] trigGraphPoints() {
        TreeSet<Double> points = new TreeSet<>();
        addRange(points, -4.0 * Math.PI, 0.0, 0.01);
        addAround(points, -4.0 * Math.PI, -4.0 * Math.PI, 0.0);
        addAround(points, -3.5 * Math.PI, -4.0 * Math.PI, 0.0);
        addAround(points, -3.0 * Math.PI, -4.0 * Math.PI, 0.0);
        addAround(points, -2.5 * Math.PI, -4.0 * Math.PI, 0.0);
        addAround(points, -2.0 * Math.PI, -4.0 * Math.PI, 0.0);
        addAround(points, -1.5 * Math.PI, -4.0 * Math.PI, 0.0);
        addAround(points, -1.0 * Math.PI, -4.0 * Math.PI, 0.0);
        addAround(points, -0.5 * Math.PI, -4.0 * Math.PI, 0.0);
        addAround(points, 0.0, -4.0 * Math.PI, 0.0);
        return toArray(points);
    }

    private static double[] logGraphPoints() {
        TreeSet<Double> points = new TreeSet<>();
        points.add(0.0);
        addRange(points, 0.05, 20.0, 0.05);
        addRange(points, 20.0, 125.0, 1.0);
        addAround(points, 0.001, 0.0, Double.POSITIVE_INFINITY);
        addAround(points, 0.01, 0.0, Double.POSITIVE_INFINITY);
        addAround(points, 0.1, 0.0, Double.POSITIVE_INFINITY);
        addAround(points, 1.0, 0.0, Double.POSITIVE_INFINITY);
        addAround(points, 2.0, 0.0, Double.POSITIVE_INFINITY);
        addAround(points, 5.0, 0.0, Double.POSITIVE_INFINITY);
        addAround(points, 10.0, 0.0, Double.POSITIVE_INFINITY);
        addAround(points, 100.0, 0.0, Double.POSITIVE_INFINITY);
        addAround(points, 125.0, 0.0, Double.POSITIVE_INFINITY);
        return toArray(points);
    }

    private static double[] systemGraphPoints(double[] trigPoints, double[] logPoints) {
        TreeSet<Double> points = new TreeSet<>();
        for (double point : trigPoints) {
            points.add(point);
        }
        for (double point : logPoints) {
            points.add(point);
        }
        return toArray(points);
    }

    private static void addRange(TreeSet<Double> points, double start, double end, double step) {
        double tolerance = step * 1.0E-9;
        for (double point = start; point <= end + tolerance; point += step) {
            points.add(point);
        }
    }

    private static void addAround(TreeSet<Double> points, double point, double minValue, double maxValue) {
        double[] shifts = {-0.01, -0.001, -0.0001, 0.0, 0.0001, 0.001, 0.01};
        for (double shift : shifts) {
            double value = point + shift;
            if (value >= minValue && value <= maxValue) {
                points.add(value);
            }
        }
    }

    private static double[] toArray(TreeSet<Double> points) {
        List<Double> sortedPoints = new ArrayList<>(points);
        double[] result = new double[sortedPoints.size()];
        for (int index = 0; index < sortedPoints.size(); index++) {
            result[index] = sortedPoints.get(index);
        }
        return result;
    }
}
