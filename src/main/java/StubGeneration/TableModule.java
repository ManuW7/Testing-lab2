package StubGeneration;

import Interfaces.CalculationFunction;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 * Табличная реализация CalculationFunction.
 *
 * Реальная функция считает значение по формуле.
 * TableModule ничего не считает: он получает готовую таблицу x -> value
 * и при calculate(x) возвращает значение из этой таблицы.
 *
 * Если нужная точка отсутствует, выбрасывается IllegalArgumentException.
 */
public class TableModule implements CalculationFunction {
    private static final double DEFAULT_LOOKUP_EPSILON = 1.0E-9;

    private final Map<Double, Double> table;
    private final double lookupEpsilon;

    public TableModule(Map<Double, Double> table) {
        this(table, DEFAULT_LOOKUP_EPSILON);
    }

    public TableModule(Map<Double, Double> table, double lookupEpsilon) {
        if (table == null || table.isEmpty()) {
            throw new IllegalArgumentException("table must not be null or empty");
        }
        if (!Double.isFinite(lookupEpsilon) || lookupEpsilon <= 0.0) {
            throw new IllegalArgumentException("lookupEpsilon must be a positive finite number");
        }

        LinkedHashMap<Double, Double> copiedTable = new LinkedHashMap<>();
        for (Map.Entry<Double, Double> entry : table.entrySet()) {
            Double x = entry.getKey();
            Double y = entry.getValue();

            if (x == null || y == null || !Double.isFinite(x) || !Double.isFinite(y)) {
                throw new IllegalArgumentException("table entries must contain finite non-null numbers");
            }

            copiedTable.put(x, y);
        }

        this.table = Map.copyOf(copiedTable);
        this.lookupEpsilon = lookupEpsilon;
    }

    @Override
    public double calculate(double x) {
        if (!Double.isFinite(x)) {
            throw new IllegalArgumentException("x must be finite");
        }

        Double bestValue = null;
        double bestDistance = Double.POSITIVE_INFINITY;

        /*
         * Ищем не строго равный double, а ближайший в пределах lookupEpsilon.
         * Это нужно из-за погрешностей представления double:
         * одно и то же математическое значение может прийти как -0.1
         * или как -0.10000000000000002.
         */
        for (Map.Entry<Double, Double> entry : table.entrySet()) {
            double distance = Math.abs(entry.getKey() - x);
            if (distance <= lookupEpsilon && distance < bestDistance) {
                bestDistance = distance;
                bestValue = entry.getValue();
            }
        }

        if (bestValue == null) {
            throw new IllegalArgumentException("No tabulated value found for x = " + x);
        }

        return bestValue;
    }
}
