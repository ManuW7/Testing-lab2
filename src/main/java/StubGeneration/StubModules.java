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

    /*
     * StubModules - это фабрика табличных заглушек.
     *
     * В тестах мы не хотим каждый раз вручную читать CSV и собирать Map<Double, Double>.
     * Поэтому вызываем, например:
     *
     *     StubModules.sinStub()
     *
     * Этот вызов делает цепочку:
     *
     *     sinStub()
     *       -> fromCsv("/lab2/stub/trig-functions.csv", "sin")
     *       -> loadColumn(...)
     *       -> new TableModule(...)
     *
     * В итоге возвращается объект CalculationFunction.
     * Он ведет себя как обычная функция, но значения берет из таблицы.
     */

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

    /*
     * Методы ниже возвращают заглушки отдельных тригонометрических модулей.
     *
     * Все они читают один общий CSV:
     *
     *     src/main/resources/lab2/stub/trig-functions.csv
     *
     * В этом CSV несколько столбцов:
     *
     *     x;sin;cos;tan;cot;sec;csc
     *
     * Например sinStub() берет только столбец "sin",
     * а tanStub() берет только столбец "tan".
     */
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

    /*
     * Методы ниже возвращают заглушки отдельных логарифмических модулей.
     *
     * Все они читают CSV:
     *
     *     src/main/resources/lab2/stub/log-functions.csv
     *
     * Структура файла:
     *
     *     x;ln;log2;log5;log10
     */
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

    /*
     * Заглушки веток и всей системы.
     *
     * trigBranchStub() читает уже готовые результаты всей тригонометрической ветки.
     * logBranchStub() читает уже готовые результаты всей логарифмической ветки.
     * systemStub() читает уже готовые результаты всей системы.
     *
     * Эти заглушки используются как эталон в интеграционных тестах.
     */
    public static CalculationFunction trigBranchStub() {
        return fromCsv("/lab2/stub/trig-branch.csv", "trig_branch");
    }

    public static CalculationFunction logBranchStub() {
        return fromCsv("/lab2/stub/log-branch.csv", "log_branch");
    }

    public static CalculationFunction systemStub() {
        return fromCsv("/lab2/stub/system.csv", "system");
    }

    /*
     * Универсальный метод создания заглушки из CSV.
     *
     * resourcePath - путь к CSV внутри src/main/resources.
     * columnName - имя столбца, который нужно взять.
     *
     * Например:
     *
     *     fromCsv("/lab2/stub/log-functions.csv", "log10")
     *
     * прочитает из файла только пары:
     *
     *     x -> log10(x)
     *
     * и передаст их в TableModule.
     */
    private static CalculationFunction fromCsv(String resourcePath, String columnName) {
        return new TableModule(loadColumn(resourcePath, columnName), LOOKUP_EPSILON);
    }

    /*
     * Читает один столбец из CSV и возвращает таблицу значений.
     *
     * Пример CSV:
     *
     *     x;ln;log2;log5;log10
     *     0.5;-0.6931471805599453;-1.0;-0.43067655807339306;-0.3010299956639812
     *
     * Если вызвать:
     *
     *     loadColumn("/lab2/stub/log-functions.csv", "log2")
     *
     * результатом будет Map:
     *
     *     0.5 -> -1.0
     *     ...
     *
     * Строки со значением "undefined" пропускаются.
     * Это важно для точек, где функция математически не определена:
     *
     *     x = 0 для тригонометрической ветки;
     *     x = 1 для логарифмической ветки.
     *
     * Если строка пропущена, TableModule потом не найдет эту точку
     * и выбросит IllegalArgumentException.
     */
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


    /*
     * Ищет индекс нужного столбца в первой строке CSV.
     *
     * Например для заголовка:
     *
     *     x;sin;cos;tan
     *
     * columnName = "cos" вернет индекс 2.
     */
    private static int findColumnIndex(String[] columns, String columnName, String resourcePath) {
        for (int index = 0; index < columns.length; index++) {
            if (columns[index].trim().equals(columnName)) {
                return index;
            }
        }
        throw new IllegalStateException("Column '" + columnName + "' is missing in " + resourcePath);
    }

    /*
     * Открывает CSV из classpath.
     *
     * В исходниках файл лежит в:
     *
     *     src/main/resources/lab2/stub/...
     *
     * При запуске Maven/IDEA копирует resources в classpath,
     * поэтому код открывает файл как:
     *
     *     /lab2/stub/...
     */
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
