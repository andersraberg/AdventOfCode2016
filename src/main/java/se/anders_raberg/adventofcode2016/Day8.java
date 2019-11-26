package se.anders_raberg.adventofcode2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8 {
    private static final Logger LOGGER = Logger.getLogger(Day8.class.getName());
    private static final Pattern PATTERN_RECT = Pattern.compile("rect (\\d+)x(\\d+)");
    private static final Pattern PATTERN_COLUMN = Pattern.compile("rotate column x=(\\d+) by (\\d+)");
    private static final Pattern PATTERN_ROW = Pattern.compile("rotate row y=(\\d+) by (\\d+)");

    private Day8() {
    }

    public static void run() throws IOException {
        List<String> operations = Files.readAllLines(Paths.get("inputs/input8.txt"));
        String[][] display = new String[6][50];

        initDisplay(display);
        toString(display);

        operations.forEach(operation -> {
            Matcher m = PATTERN_RECT.matcher(operation);
            if (m.matches()) {
                rect(display, Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
            }

            m = PATTERN_COLUMN.matcher(operation);
            if (m.matches()) {
                column(display, Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
            }

            m = PATTERN_ROW.matcher(operation);
            if (m.matches()) {
                row(display, Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
            }
        });

        LOGGER.info(() -> String.format("Part 1: Pixel count: %s", countDisplay(display)));
        LOGGER.info(() -> String.format("Part 2: Code: %s", toString(display)));
    }

    private static void rect(String[][] display, int width, int height) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                display[y][x] = "#";
            }
        }
    }

    private static void column(String[][] display, int column, int steps) {
        for (int s = 0; s < steps; s++) {
            String end = display[display.length - 1][column];
            int i;
            for (i = display.length - 1; i > 0; i--) {
                display[i][column] = display[i - 1][column];
            }
            display[0][column] = end;
        }

    }

    private static void row(String[][] display, int row, int steps) {
        for (int s = 0; s < steps; s++) {
            String[] tmpRow = display[row];
            String end = tmpRow[tmpRow.length - 1];
            int i;
            for (i = tmpRow.length - 1; i > 0; i--) {
                tmpRow[i] = tmpRow[i - 1];
            }
            tmpRow[0] = end;
        }
    }

    private static void initDisplay(String[][] display) {
        for (int y = 0; y < display.length; y++) {
            for (int x = 0; x < display[y].length; x++) {
                display[y][x] = ".";
            }
        }
    }

    private static String toString(String[][] display) {
        StringBuilder sb = new StringBuilder("\n");
        for (int y = 0; y < display.length; y++) {
            for (int x = 0; x < display[y].length; x++) {
                sb.append(display[y][x]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private static int countDisplay(String[][] display) {
        int i = 0;
        for (int y = 0; y < display.length; y++) {
            for (int x = 0; x < display[y].length; x++) {
                if (display[y][x].equals("#")) {
                    i++;
                }
            }
        }
        return i;
    }
}
