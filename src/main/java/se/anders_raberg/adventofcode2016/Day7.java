package se.anders_raberg.adventofcode2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 {
    private static final Logger LOGGER = Logger.getLogger(Day7.class.getName());
    private static final Pattern PATTERN = Pattern.compile("([a-z]*)\\[([a-z]*)\\]([a-z]*)");

    private Day7() {
    }

    public static void run() throws IOException {

        List<String> ips = Files.readAllLines(Paths.get("inputs/input7.txt"));

        AtomicLong part1counter = new AtomicLong(0);
        AtomicLong part2counter = new AtomicLong(0);
        ips.forEach(ip -> {
            Matcher m = PATTERN.matcher(ip);
            boolean foundInside = false;
            boolean foundOutside = false;
            List<String> outsideStringsWithAba = new ArrayList<>();
            StringBuilder insideStrings = new StringBuilder();
            while (m.find()) {
                // Part 1
                foundOutside = foundOutside || containsAbba(m.group(1)) || containsAbba(m.group(3));
                foundInside = foundInside || containsAbba(m.group(2));
                // Part 2
                outsideStringsWithAba.addAll(containsAba(m.group(1)));
                outsideStringsWithAba.addAll(containsAba(m.group(3)));
                insideStrings.append(m.group(2));
            }

            if (foundOutside && !foundInside) {
                part1counter.incrementAndGet();
            }
            
            if (containsBab(insideStrings.toString(), outsideStringsWithAba)) {
                part2counter.incrementAndGet();
            }
        });

        LOGGER.info("Part 1: IPs :" + part1counter.get());
        LOGGER.info("Part 2: IPs :" + part2counter.get());
    }

    private static boolean containsAbba(String ip) {
        for (int i = 0; i < ip.length() - 3; i++) {
            char a1 = ip.charAt(i);
            char b1 = ip.charAt(i + 1);
            char b2 = ip.charAt(i + 2);
            char a2 = ip.charAt(i + 3);

            if (a1 != b1 && a1 == a2 && b1 == b2) {
                return true;
            }
        }
        return false;
    }

    private static List<String> containsAba(String ip) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < ip.length() - 2; i++) {
            char a1 = ip.charAt(i);
            char b1 = ip.charAt(i + 1);
            char a2 = ip.charAt(i + 2);

            if (a1 != b1 && a1 == a2) {
                result.add("" + a1 + b1 + a2);
            }
        }
        return result;
    }

    private static boolean containsBab(String ip, List<String> aba) {
        for (String string : aba) {
            for (int i = 0; i < ip.length() - 2; i++) {

                if (ip.charAt(i) == string.charAt(1) //
                        && ip.charAt(i + 1) == string.charAt(0) //
                        && ip.charAt(i + 2) == string.charAt(1)) {
                    return true;
                }
            }
        }
        return false;
    }
}
