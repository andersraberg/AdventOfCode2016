package se.anders_raberg.adventofcode2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4 {
    private static final Logger LOGGER = Logger.getLogger(Day4.class.getName());

    private Day4() {
    }

    private static final Pattern PATTERN = Pattern.compile("([a-z,-]+)([0-9]+)\\[([a-z]+)\\]");

    public static void run() throws IOException {
        List<String> allRooms = Files.readAllLines(Paths.get("inputs/input4.txt"));

        long sum = 0;
        for (String room : allRooms) {
            Matcher m = PATTERN.matcher(room);
            if (m.matches()) {
                String roomName = m.group(1);
                int sectorId = Integer.parseInt(m.group(2));
                String checksum = m.group(3);

                Map<String, Long> countMap = Arrays.stream(roomName.replaceAll("-", "").split(""))
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

                String calculatedCheckSum = countMap.entrySet().stream() //
                        .sorted(Day4::compare) //
                        .map(Entry::getKey) //
                        .reduce("", (a, b) -> a + b).substring(0, 5);

                if (calculatedCheckSum.equals(checksum)) {
                    sum += sectorId;

                    String decryptedName = rotate(roomName, sectorId);
                    if (decryptedName.contains("northpole")) {
                        LOGGER.info(() -> String.format("Decrypted name: %s : %s", decryptedName, sectorId));
                    }

                }

            }
        }

        long roomSum = sum;
        LOGGER.info(() -> String.format("Sum of real room ID:s %s", roomSum));
    }

    private static int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
        if (o2.getValue().equals(o1.getValue())) {
            return o1.getKey().compareTo(o2.getKey());
        }
        return (int) (o2.getValue() - o1.getValue());
    }

    private static String rotate(String roomName, int sectorId) {
        StringBuilder sb = new StringBuilder();
        int noOfChars = 'z' - 'a' + 1;
        for (char c : roomName.toCharArray()) {
            if (c == '-') {
                sb.append(" ");
            } else {
                int offset = c - 'a';
                int qqqq = (offset + sectorId) % noOfChars;
                sb.appendCodePoint(qqqq + 'a');
            }
        }
        return sb.toString();
    }

}
