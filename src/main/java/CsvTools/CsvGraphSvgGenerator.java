package CsvTools;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class CsvGraphSvgGenerator {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 720;
    private static final int LEFT = 90;
    private static final int RIGHT = 40;
    private static final int TOP = 70;
    private static final int BOTTOM = 80;
    private static final List<String> EXPECTED_GRAPH_ORDER = List.of(
            "sin",
            "cos",
            "tan",
            "cot",
            "sec",
            "csc",
            "ln",
            "log2",
            "log5",
            "log10",
            "trig_branch",
            "log_branch",
            "system"
    );

    private CsvGraphSvgGenerator() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length > 2) {
            throw new IllegalArgumentException("Usage: CsvGraphSvgGenerator [csvDirectory] [outputDirectory]");
        }

        Path csvDirectory = Paths.get(args.length >= 1 ? args[0] : "csv-exports");
        Path outputDirectory = Paths.get(args.length >= 2 ? args[1] : "graph-exports");
        Files.createDirectories(outputDirectory);

        List<Path> graphFiles = new ArrayList<>();
        for (Path csvPath : csvPathsInDisplayOrder(csvDirectory)) {
            GraphData graphData = readCsv(csvPath);
            Path svgPath = outputDirectory.resolve(fileNameWithoutExtension(csvPath) + ".svg");
            Files.write(svgPath, renderSvg(graphData).getBytes(StandardCharsets.UTF_8));
            graphFiles.add(svgPath);
            System.out.println("Generated " + svgPath);
        }

        Files.write(outputDirectory.resolve("index.html"), renderIndex(graphFiles), StandardCharsets.UTF_8);
        System.out.println("Generated " + outputDirectory.resolve("index.html"));
    }

    private static List<Path> csvPathsInDisplayOrder(Path csvDirectory) throws IOException {
        Map<String, Path> csvByName = new LinkedHashMap<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(csvDirectory, "*.csv")) {
            for (Path csvPath : stream) {
                csvByName.put(fileNameWithoutExtension(csvPath), csvPath);
            }
        }

        List<Path> ordered = new ArrayList<>();
        List<String> missingExpected = new ArrayList<>();
        for (String expectedName : EXPECTED_GRAPH_ORDER) {
            Path csvPath = csvByName.remove(expectedName);
            if (csvPath == null) {
                missingExpected.add(expectedName);
            } else {
                ordered.add(csvPath);
            }
        }

        List<Path> extraCsv = new ArrayList<>(csvByName.values());
        Collections.sort(extraCsv, Comparator.comparing(path -> path.getFileName().toString()));
        ordered.addAll(extraCsv);

        if (!missingExpected.isEmpty()) {
            System.out.println("Missing expected CSV files: " + String.join(", ", missingExpected));
        }
        return ordered;
    }

    private static GraphData readCsv(Path csvPath) throws IOException {
        List<String> lines = Files.readAllLines(csvPath, StandardCharsets.UTF_8);
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("CSV file is empty: " + csvPath);
        }

        String[] header = lines.get(0).split(";", -1);
        String title = header.length > 1 ? header[1] : fileNameWithoutExtension(csvPath);
        List<Point> points = new ArrayList<>();
        List<Double> finiteValues = new ArrayList<>();

        for (int index = 1; index < lines.size(); index++) {
            String[] parts = lines.get(index).split(";", -1);
            if (parts.length < 2) {
                continue;
            }

            double x = Double.parseDouble(parts[0]);
            Double y = parseValue(parts[1]);
            points.add(new Point(x, y));
            if (y != null && Double.isFinite(y)) {
                finiteValues.add(y);
            }
        }

        if (points.isEmpty() || finiteValues.isEmpty()) {
            throw new IllegalArgumentException("CSV file has no finite graph points: " + csvPath);
        }

        Collections.sort(finiteValues);
        return new GraphData(title, points, finiteValues);
    }

    private static Double parseValue(String value) {
        if ("undefined".equals(value)) {
            return null;
        }
        double parsed = Double.parseDouble(value);
        if (!Double.isFinite(parsed)) {
            return null;
        }
        return parsed;
    }

    private static String renderSvg(GraphData data) {
        double xMin = data.points.get(0).x;
        double xMax = data.points.get(data.points.size() - 1).x;
        double yMin = percentile(data.finiteValues, 0.02);
        double yMax = percentile(data.finiteValues, 0.98);
        if (Math.abs(yMax - yMin) < 1.0E-12) {
            yMin -= 1.0;
            yMax += 1.0;
        }

        StringBuilder svg = new StringBuilder();
        svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"").append(WIDTH).append("\" height=\"").append(HEIGHT).append("\" viewBox=\"0 0 ").append(WIDTH).append(' ').append(HEIGHT).append("\">\n");
        svg.append("<rect width=\"100%\" height=\"100%\" fill=\"#ffffff\"/>\n");
        svg.append("<text x=\"").append(WIDTH / 2).append("\" y=\"35\" text-anchor=\"middle\" font-family=\"Arial\" font-size=\"24\" font-weight=\"700\">").append(escape(data.title)).append("</text>\n");
        svg.append("<text x=\"").append(WIDTH / 2).append("\" y=\"60\" text-anchor=\"middle\" font-family=\"Arial\" font-size=\"13\" fill=\"#666666\">CSV export; undefined points are gaps; y-axis clipped by 2% and 98% percentiles</text>\n");
        drawGrid(svg, xMin, xMax, yMin, yMax);
        drawAxes(svg, xMin, xMax, yMin, yMax);
        drawLine(svg, data.points, xMin, xMax, yMin, yMax);
        svg.append("</svg>\n");
        return svg.toString();
    }

    private static void drawGrid(StringBuilder svg, double xMin, double xMax, double yMin, double yMax) {
        int plotWidth = WIDTH - LEFT - RIGHT;
        int plotHeight = HEIGHT - TOP - BOTTOM;
        svg.append("<rect x=\"").append(LEFT).append("\" y=\"").append(TOP).append("\" width=\"").append(plotWidth).append("\" height=\"").append(plotHeight).append("\" fill=\"#fbfbfb\" stroke=\"#222222\" stroke-width=\"1\"/>\n");

        for (int index = 0; index <= 10; index++) {
            double x = LEFT + plotWidth * index / 10.0;
            double value = xMin + (xMax - xMin) * index / 10.0;
            svg.append("<line x1=\"").append(format(x)).append("\" y1=\"").append(TOP).append("\" x2=\"").append(format(x)).append("\" y2=\"").append(TOP + plotHeight).append("\" stroke=\"#e5e7eb\"/>\n");
            svg.append("<text x=\"").append(format(x)).append("\" y=\"").append(HEIGHT - 45).append("\" text-anchor=\"middle\" font-family=\"Arial\" font-size=\"11\" fill=\"#555555\">").append(format(value)).append("</text>\n");
        }

        for (int index = 0; index <= 8; index++) {
            double y = TOP + plotHeight * index / 8.0;
            double value = yMax - (yMax - yMin) * index / 8.0;
            svg.append("<line x1=\"").append(LEFT).append("\" y1=\"").append(format(y)).append("\" x2=\"").append(WIDTH - RIGHT).append("\" y2=\"").append(format(y)).append("\" stroke=\"#e5e7eb\"/>\n");
            svg.append("<text x=\"").append(LEFT - 10).append("\" y=\"").append(format(y + 4)).append("\" text-anchor=\"end\" font-family=\"Arial\" font-size=\"11\" fill=\"#555555\">").append(format(value)).append("</text>\n");
        }

        svg.append("<text x=\"").append(WIDTH / 2).append("\" y=\"").append(HEIGHT - 15).append("\" text-anchor=\"middle\" font-family=\"Arial\" font-size=\"14\">x</text>\n");
        svg.append("<text x=\"24\" y=\"").append(HEIGHT / 2).append("\" text-anchor=\"middle\" font-family=\"Arial\" font-size=\"14\" transform=\"rotate(-90 24 ").append(HEIGHT / 2).append(")\">result</text>\n");
    }

    private static void drawAxes(StringBuilder svg, double xMin, double xMax, double yMin, double yMax) {
        if (xMin < 0.0 && xMax > 0.0) {
            double x = mapX(0.0, xMin, xMax);
            svg.append("<line x1=\"").append(format(x)).append("\" y1=\"").append(TOP).append("\" x2=\"").append(format(x)).append("\" y2=\"").append(HEIGHT - BOTTOM).append("\" stroke=\"#777777\" stroke-width=\"1.4\"/>\n");
        }
        if (yMin < 0.0 && yMax > 0.0) {
            double y = mapY(0.0, yMin, yMax);
            svg.append("<line x1=\"").append(LEFT).append("\" y1=\"").append(format(y)).append("\" x2=\"").append(WIDTH - RIGHT).append("\" y2=\"").append(format(y)).append("\" stroke=\"#777777\" stroke-width=\"1.4\"/>\n");
        }
    }

    private static void drawLine(StringBuilder svg, List<Point> points, double xMin, double xMax, double yMin, double yMax) {
        StringBuilder path = new StringBuilder();
        boolean started = false;

        for (Point point : points) {
            if (point.y == null || point.y < yMin || point.y > yMax) {
                started = false;
                continue;
            }

            double x = mapX(point.x, xMin, xMax);
            double y = mapY(point.y, yMin, yMax);
            if (!started) {
                path.append('M').append(format(x)).append(' ').append(format(y));
                started = true;
            } else {
                path.append('L').append(format(x)).append(' ').append(format(y));
            }
        }

        svg.append("<path d=\"").append(path).append("\" fill=\"none\" stroke=\"#2563eb\" stroke-width=\"2\" stroke-linejoin=\"round\" stroke-linecap=\"round\"/>\n");
    }

    private static double mapX(double value, double xMin, double xMax) {
        return LEFT + (value - xMin) / (xMax - xMin) * (WIDTH - LEFT - RIGHT);
    }

    private static double mapY(double value, double yMin, double yMax) {
        return TOP + (yMax - value) / (yMax - yMin) * (HEIGHT - TOP - BOTTOM);
    }

    private static double percentile(List<Double> values, double percentile) {
        int index = (int) Math.round((values.size() - 1) * percentile);
        return values.get(Math.max(0, Math.min(values.size() - 1, index)));
    }

    private static List<String> renderIndex(List<Path> graphFiles) {
        List<String> lines = new ArrayList<>();
        lines.add("<!doctype html>");
        lines.add("<html lang=\"ru\">");
        lines.add("<head>");
        lines.add("<meta charset=\"utf-8\">");
        lines.add("<title>Lab 2 Graph Exports</title>");
        lines.add("<style>body{font-family:Arial,sans-serif;margin:24px;background:#f8fafc;color:#111827}nav{display:flex;flex-wrap:wrap;gap:8px;margin:0 0 24px}section{margin:0 0 32px;padding:18px;background:white;border:1px solid #e5e7eb}img{width:100%;max-width:1200px;height:auto}a{color:#2563eb}nav a{padding:6px 10px;background:#ffffff;border:1px solid #d1d5db;text-decoration:none;color:#111827}</style>");
        lines.add("</head>");
        lines.add("<body>");
        lines.add("<h1>Lab 2 Graph Exports</h1>");
        lines.add("<nav>");
        for (Path graphFile : graphFiles) {
            String fileName = graphFile.getFileName().toString();
            String title = fileNameWithoutExtension(graphFile);
            lines.add("<a href=\"#" + escape(title) + "\">" + escape(title) + "</a>");
        }
        lines.add("</nav>");
        for (Path graphFile : graphFiles) {
            String fileName = graphFile.getFileName().toString();
            String title = fileNameWithoutExtension(graphFile);
            lines.add("<section>");
            lines.add("<h2 id=\"" + escape(title) + "\">" + escape(fileName) + "</h2>");
            lines.add("<p><a href=\"" + escape(fileName) + "\">Open " + escape(fileName) + "</a></p>");
            lines.add("<img src=\"" + escape(fileName) + "\" alt=\"" + escape(fileName) + "\">");
            lines.add("</section>");
        }
        lines.add("</body>");
        lines.add("</html>");
        return lines;
    }

    private static String fileNameWithoutExtension(Path path) {
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        return dotIndex < 0 ? fileName : fileName.substring(0, dotIndex);
    }

    private static String format(double value) {
        return String.format(java.util.Locale.US, "%.6g", value);
    }

    private static String escape(String value) {
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    private static final class GraphData {
        private final String title;
        private final List<Point> points;
        private final List<Double> finiteValues;

        private GraphData(String title, List<Point> points, List<Double> finiteValues) {
            this.title = title;
            this.points = points;
            this.finiteValues = finiteValues;
        }
    }

    private static final class Point {
        private final double x;
        private final Double y;

        private Point(double x, Double y) {
            this.x = x;
            this.y = y;
        }
    }
}
