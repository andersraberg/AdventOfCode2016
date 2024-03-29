package se.anders_raberg.adventofcode2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day9 {
    private static final Logger LOGGER = Logger.getLogger(Day9.class.getName());
    private static final Pattern PATTERN = Pattern.compile("\\((\\d+)x(\\d+)\\)(.*)");

    private Day9() {
    }

    private static StringBuilder UNCOMPRESSED_DATA = new StringBuilder();

    public static void run() throws IOException {
        String rawData = new String(Files.readAllBytes(Paths.get("inputs/input9.txt"))).replaceAll("\\s+", "");

        parseString(rawData);

        LOGGER.info(() -> String.format("Part 1 : Length : %s", UNCOMPRESSED_DATA.length()));
        LOGGER.info(() -> String.format("Part 2 : Length : %s", countDecrompress(rawData)));
    }

    private static void parseString(String data) {
        Matcher m = PATTERN.matcher(data);
        boolean found = m.find();

        if (found) {
            int size = Integer.parseInt(m.group(1));
            int factor = Integer.parseInt(m.group(2));

            String tmp = m.group(3).substring(0, size);
            UNCOMPRESSED_DATA.append(tmp.repeat(factor));

            parseString(m.group(3).substring(size));
        } else {
            UNCOMPRESSED_DATA.append(data);
        }
    }

    private static long countDecrompress(String input) {
        long count = 0;
        while (input.contains("(")) {
            count += input.substring(0, input.indexOf("(")).length();
            int A = Integer.parseInt(input.substring(input.indexOf("(") + 1, input.indexOf("x")));
            int B = Integer.parseInt(input.substring(input.indexOf("x") + 1, input.indexOf(")")));

            String decompress = input.substring(input.indexOf(")")+1, input.indexOf(")") + A + 1);
            count += B * countDecrompress(decompress);

            input = input.substring(input.indexOf(")") + A + 1);
        }
        count += input.length();

        return count;
    }

}
