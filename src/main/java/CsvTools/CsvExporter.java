package CsvTools;

import Interfaces.CalculationFunction;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvExporter {
    public void export(
            CalculationFunction module,
            double startX,
            double endX,
            double step,
            String outputPath,
            String headerName
    ) throws IOException {
        validateModule(module);
        if (!Double.isFinite(startX) || !Double.isFinite(endX) || !Double.isFinite(step)) {
            throw new IllegalArgumentException("range arguments must be finite");
        }
        if (step <= 0.0) {
            throw new IllegalArgumentException("step must be positive");
        }
        if (endX < startX) {
            throw new IllegalArgumentException("endX must be greater than or equal to startX");
        }

        List<String> lines = new ArrayList<>();
        lines.add("x;" + validateHeaderName(headerName));

        double epsilon = Math.abs(step) * 1.0E-9;
        for (double currentX = startX; currentX <= endX + epsilon; currentX += step) {
            lines.add(formatLine(module, currentX, true));
        }

        writeLines(outputPath, lines);
    }

    public void exportPoints(
            CalculationFunction module,
            double[] points,
            String outputPath,
            String headerName
    ) throws IOException {
        validateModule(module);
        validatePoints(points);

        List<String> lines = new ArrayList<>();
        lines.add("x;" + validateHeaderName(headerName));
        for (double point : points) {
            lines.add(formatLine(module, point, true));
        }

        writeLines(outputPath, lines);
    }

    public void exportPointColumns(
            double[] points,
            String outputPath,
            String[] headerNames,
            CalculationFunction[] modules
    ) throws IOException {
        validatePoints(points);
        if (headerNames == null || modules == null || headerNames.length == 0 || headerNames.length != modules.length) {
            throw new IllegalArgumentException("headers and modules must have the same non-zero length");
        }

        StringBuilder header = new StringBuilder("x");
        for (int index = 0; index < headerNames.length; index++) {
            validateModule(modules[index]);
            header.append(';').append(validateHeaderName(headerNames[index]));
        }

        List<String> lines = new ArrayList<>();
        lines.add(header.toString());
        for (double point : points) {
            StringBuilder line = new StringBuilder(Double.toString(point));
            for (CalculationFunction module : modules) {
                line.append(';').append(formatValue(module, point, false));
            }
            lines.add(line.toString());
        }

        writeLines(outputPath, lines);
    }

    private String formatLine(CalculationFunction module, double x, boolean allowUndefined) {
        return Double.toString(x) + ";" + formatValue(module, x, allowUndefined);
    }

    private String formatValue(CalculationFunction module, double x, boolean allowUndefined) {
        try {
            double value = module.calculate(x);
            if (!Double.isFinite(value)) {
                return undefinedOrThrow(x, allowUndefined);
            }
            return Double.toString(value);
        } catch (IllegalArgumentException exception) {
            return undefinedOrThrow(x, allowUndefined);
        }
    }

    private String undefinedOrThrow(double x, boolean allowUndefined) {
        if (allowUndefined) {
            return "undefined";
        }
        throw new IllegalArgumentException("stub table point is undefined: x = " + x);
    }

    private void writeLines(String outputPath, List<String> lines) throws IOException {
        if (isBlank(outputPath)) {
            throw new IllegalArgumentException("outputPath must not be blank");
        }

        Path path = Paths.get(outputPath);
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Files.write(path, lines, StandardCharsets.UTF_8);
    }

    private void validateModule(CalculationFunction module) {
        if (module == null) {
            throw new IllegalArgumentException("module must not be null");
        }
    }

    private void validatePoints(double[] points) {
        if (points == null || points.length == 0) {
            throw new IllegalArgumentException("points must not be null or empty");
        }
        for (double point : points) {
            if (!Double.isFinite(point)) {
                throw new IllegalArgumentException("points must contain only finite numbers");
            }
        }
    }

    private String validateHeaderName(String headerName) {
        if (isBlank(headerName)) {
            throw new IllegalArgumentException("headerName must not be blank");
        }
        if (headerName.contains(";")) {
            throw new IllegalArgumentException("headerName must not contain semicolon");
        }
        return headerName;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
