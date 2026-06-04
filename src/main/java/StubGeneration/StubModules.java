package StubGeneration;

import Interfaces.CalculationFunction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public final class StubModules {
    private static final double LOOKUP_EPSILON = 1.0E-6;

    private static final double[] BASE_TRIG_FUNCTION_POINTS = {
            -Math.PI / 2.0 - 0.01,
            -Math.PI / 2.0 + 0.01,
            -Math.PI / 3.0,
            -Math.PI / 4.0,
            -Math.PI / 6.0,
            -0.1,
            -0.01,
            0.01,
            0.1
    };

    public static final double[] TRIG_BRANCH_POINTS = {
            -2.0 * Math.PI - 0.1,
            -2.0 * Math.PI - 0.01,
            -2.0 * Math.PI + 0.01,
            -2.0 * Math.PI + 0.1,
            -3.0 * Math.PI / 2.0 - 0.1,
            -3.0 * Math.PI / 2.0 - 0.01,
            -3.0 * Math.PI / 2.0 + 0.01,
            -3.0 * Math.PI / 2.0 + 0.1,
            -5.304549133547825,
            -4.046226715345206,
            -4.025527880775067,
            -4.005527880775068,
            -3.7989451838247144,
            -3.7889451838247146,
            -3.778945183824715,
            -Math.PI - 0.1,
            -2.0,
            -1.5,
            -1.0,
            -Math.PI - 0.01,
            -Math.PI + 0.01,
            -Math.PI + 0.1,
            -2.5318935222948733,
            -2.5218935222948735,
            -2.5118935222948737,
            -Math.PI / 2.0 - 0.1,
            -Math.PI / 2.0 - 0.01,
            -Math.PI / 2.0 + 0.01,
            -Math.PI / 2.0 + 0.1,
            -1.0824892503595005,
            -0.8836336743971969,
            -0.8636336743971968,
            -0.7574565642387139,
            -0.5,
            -0.2,
            -0.1,
            -0.01,
            -0.001,
            -1.0E-4,
            0.0
    };

    public static final double[] TRIG_FUNCTION_POINTS = buildTrigFunctionPoints();

    public static final double[] LOG_POINTS = {
            1.0E-6,
            1.0E-4,
            0.001,
            0.01,
            0.05,
            0.1,
            0.2,
            0.24170525010063834,
            1.0 / 3.0,
            0.4165142415584832,
            0.5,
            0.9,
            0.99,
            1.0,
            1.01,
            1.1,
            2.0,
            3.0,
            5.0,
            10.0,
            100.0,
            112.99821225476838,
            125.0
    };

    public static final double[] SYSTEM_POINTS = buildSystemPoints();

    private StubModules() {
    }

    public static CalculationFunction sinStub() {
        return fromCsv("/lab2/stub/trig-functions.csv", "sin");
    }

    public static CalculationFunction cosStub() {
        return fromCsv("/lab2/stub/trig-functions.csv", "cos");
    }

    public static CalculationFunction tanStub() {
        return fromCsv("/lab2/stub/trig-functions.csv", "tan");
    }

    public static CalculationFunction cotStub() {
        return fromCsv("/lab2/stub/trig-functions.csv", "cot");
    }

    public static CalculationFunction secStub() {
        return fromCsv("/lab2/stub/trig-functions.csv", "sec");
    }

    public static CalculationFunction cscStub() {
        return fromCsv("/lab2/stub/trig-functions.csv", "csc");
    }

    public static CalculationFunction lnStub() {
        return fromCsv("/lab2/stub/log-functions.csv", "ln");
    }

    public static CalculationFunction log2Stub() {
        return fromCsv("/lab2/stub/log-functions.csv", "log2");
    }

    public static CalculationFunction log5Stub() {
        return fromCsv("/lab2/stub/log-functions.csv", "log5");
    }

    public static CalculationFunction log10Stub() {
        return fromCsv("/lab2/stub/log-functions.csv", "log10");
    }

    public static CalculationFunction trigBranchStub() {
        return fromCsv("/lab2/stub/trig-branch.csv", "trig_branch");
    }

    public static CalculationFunction logBranchStub() {
        return fromCsv("/lab2/stub/log-branch.csv", "log_branch");
    }

    public static CalculationFunction systemStub() {
        return fromCsv("/lab2/stub/system.csv", "system");
    }

    private static CalculationFunction fromCsv(String resourcePath, String columnName) {
        return new TableModule(loadColumn(resourcePath, columnName), LOOKUP_EPSILON);
    }

    private static Map<Double, Double> loadColumn(String resourcePath, String columnName) {
        try (BufferedReader reader = openResource(resourcePath)) {
            String header = reader.readLine();
            if (header == null) {
                throw new IllegalStateException("CSV resource is empty: " + resourcePath);
            }
            String[] columns = header.split(";");
            int targetIndex = findColumnIndex(columns, columnName, resourcePath);

            LinkedHashMap<Double, Double> result = new LinkedHashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split(";");
                if (parts.length <= targetIndex) {
                    throw new IllegalStateException("Malformed CSV line in " + resourcePath + ": " + line);
                }
                if (parts[targetIndex].trim().equals("undefined")) {
                    continue;
                }
                result.put(Double.parseDouble(parts[0]), Double.parseDouble(parts[targetIndex]));
            }
            return result;
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to read CSV resource: " + resourcePath, exception);
        }
    }


    private static int findColumnIndex(String[] columns, String columnName, String resourcePath) {
        for (int index = 0; index < columns.length; index++) {
            if (columns[index].trim().equals(columnName)) {
                return index;
            }
        }
        throw new IllegalStateException("Column '" + columnName + "' is missing in " + resourcePath);
    }

    private static BufferedReader openResource(String resourcePath) throws IOException {
        InputStream resource = StubModules.class.getResourceAsStream(resourcePath);
        if (resource == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }
        return new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8));
    }

    private static double[] buildTrigFunctionPoints() {
        LinkedHashMap<Double, Boolean> points = new LinkedHashMap<>();
        addPoints(points, BASE_TRIG_FUNCTION_POINTS);
        for (double point : TRIG_BRANCH_POINTS) {
            if (point < 0.0) {
                addPoint(points, point);
                addPoint(points, Math.PI / 2.0 - point);
            }
        }

        double[] result = new double[points.size()];
        int index = 0;
        for (double point : points.keySet()) {
            result[index] = point;
            index++;
        }
        return result;
    }

    private static double[] buildSystemPoints() {
        LinkedHashMap<Double, Boolean> points = new LinkedHashMap<>();
        addPoints(points, TRIG_BRANCH_POINTS);
        addPoints(points, LOG_POINTS);

        double[] result = new double[points.size()];
        int index = 0;
        for (double point : points.keySet()) {
            result[index] = point;
            index++;
        }
        return result;
    }

    private static void addPoints(LinkedHashMap<Double, Boolean> points, double[] values) {
        for (double value : values) {
            addPoint(points, value);
        }
    }

    private static void addPoint(LinkedHashMap<Double, Boolean> points, double point) {
        points.put(point, Boolean.TRUE);
    }
}
