package se.anders_raberg.adventofcode2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 {

    private static final Logger LOGGER = Logger.getLogger(Day10.class.getName());
    private static final Pattern PATTERN_START = Pattern.compile("value (\\d+) goes to bot (\\d+)");
    private static final Pattern PATTERN_RULES = Pattern
            .compile("bot (\\d+) gives low to (bot|output) (\\d+) and high to (bot|output) (\\d+)");

    private static final Map<Integer, Set<Integer>> BOTS = new HashMap<>();
    private static final Map<Integer, Set<Integer>> OUTPUTS = new HashMap<>();
    private static final Map<Integer, TransferRule> RULES = new HashMap<>();

    private static final Set<Integer> COMPARE_TARGET = Set.of(17, 61);

    private enum Destination {
        BOT, OUTPUT;
    }

    private static class TransferRule {
        private final int _low;
        private final int _high;
        private final Destination _lowDest;
        private final Destination _highDest;

        private TransferRule(int low, int high, Destination lowDest, Destination highDest) {
            _low = low;
            _high = high;
            _lowDest = lowDest;
            _highDest = highDest;
        }

        public int low() {
            return _low;
        }

        public int high() {
            return _high;
        }

        public Destination lowDest() {
            return _lowDest;
        }

        public Destination highDest() {
            return _highDest;
        }

    }

    private Day10() {
    }

    public static void run() throws IOException {
        List<String> instructions = Files.readAllLines(Paths.get("inputs/input10.txt"));

        for (String instruction : instructions) {
            Matcher m = PATTERN_START.matcher(instruction);
            if (m.matches()) {
                int value = Integer.parseInt(m.group(1));
                int bot = Integer.parseInt(m.group(2));
                BOTS.computeIfAbsent(bot, b -> new HashSet<>()).add(value);
            }

            m = PATTERN_RULES.matcher(instruction);
            if (m.matches()) {
                RULES.put(Integer.parseInt(m.group(1)),
                        new TransferRule(Integer.parseInt(m.group(3)), Integer.parseInt(m.group(5)),
                                Destination.valueOf(m.group(2).toUpperCase()),
                                Destination.valueOf(m.group(4).toUpperCase())));
            }
        }

        Optional<Entry<Integer, Set<Integer>>> botWithTwoChips;

        do {
            botWithTwoChips = BOTS.entrySet().stream().filter(bot -> bot.getValue().size() == 2).findFirst();
            botWithTwoChips.ifPresent(e -> {
                int low = removeLow(e.getKey());
                int high = removeHigh(e.getKey());

                TransferRule transferRule = RULES.get(e.getKey());
                if (transferRule.lowDest() == Destination.BOT) {
                    BOTS.computeIfAbsent(transferRule.low(), b -> new HashSet<>()).add(low);
                } else {
                    OUTPUTS.computeIfAbsent(transferRule.low(), b -> new HashSet<>()).add(low);
                }

                if (transferRule.highDest() == Destination.BOT) {
                    BOTS.computeIfAbsent(transferRule.high(), b -> new HashSet<>()).add(high);
                } else {
                    OUTPUTS.computeIfAbsent(transferRule.high(), b -> new HashSet<>()).add(high);
                }

                Optional<Entry<Integer, Set<Integer>>> findAny = BOTS.entrySet().stream()
                        .filter(x -> x.getValue().equals(COMPARE_TARGET)).findAny();

                findAny.ifPresent(bot -> LOGGER.info("Part 1 : Bot number :" + bot.getKey()));

            });
        } while (botWithTwoChips.isPresent());

        Set<Integer> totalSet = new HashSet<>();
        totalSet.addAll(OUTPUTS.get(0));
        totalSet.addAll(OUTPUTS.get(1));
        totalSet.addAll(OUTPUTS.get(2));

        LOGGER.info(() -> MessageFormat.format("Part 2 : Product : {0}", totalSet.stream().reduce(1, (a, b) -> a * b)));
    }

    private static int removeLow(int bot) {
        int low = BOTS.get(bot).stream().min(Integer::compare).orElseThrow();
        BOTS.get(bot).remove(low);
        return low;
    }

    private static int removeHigh(int bot) {
        int high = BOTS.get(bot).stream().max(Integer::compare).orElseThrow();
        BOTS.get(bot).remove(high);
        return high;
    }
}
