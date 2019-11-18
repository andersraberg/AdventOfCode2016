package se.anders_raberg.adventofcode2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Day6 {
    private static final Logger LOGGER = Logger.getLogger(Day6.class.getName());

    private Day6() {
    }

    public static void run() throws IOException {
        List<String> messages = Files.readAllLines(Paths.get("inputs/input6.txt"));

        Map<Integer, List<Character>> msgPosMap = new HashMap<>();
        messages.forEach(m -> {
            for (int i = 0; i < 8; i++) {
                msgPosMap.computeIfAbsent(i, x -> new ArrayList<>()).add(m.charAt(i));
            }
        });

        List<Character> decodedMsg = msgPosMap.entrySet() //
                .stream() //
                .sorted((e1, e2) -> e1.getKey() - e2.getKey()) //
                .map(a -> getMostFrequent(a.getValue())) //
                .collect(Collectors.toList());

        LOGGER.info(() -> String.format("Part 1: Message: %s", decodedMsg));

        List<Character> decodedMsg2 = msgPosMap.entrySet() //
                .stream() //
                .sorted((e1, e2) -> e1.getKey() - e2.getKey()) //
                .map(a -> getLeastFrequent(a.getValue())) //
                .collect(Collectors.toList());

        LOGGER.info(() -> String.format("Part 2: Message: %s", decodedMsg2));
    }

    private static Character getMostFrequent(List<Character> chars) {
        return getFirstByOrdering(chars, (o1, o2) -> (int) (o2 - o1));
    }

    private static Character getLeastFrequent(List<Character> chars) {
        return getFirstByOrdering(chars, (o1, o2) -> (int) (o1 - o2));
    }

    private static Character getFirstByOrdering(List<Character> chars, Comparator<Long> comparator) {
        Map<Character, Long> countMap = chars.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return countMap.entrySet() //
                .stream() //
                .sorted((o1, o2) -> comparator.compare(o1.getValue(), o2.getValue())).findFirst()
                .orElseThrow(IllegalStateException::new) //
                .getKey();
    }

}
