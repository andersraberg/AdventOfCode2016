package se.anders_raberg.adventofcode2016;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Day5 {
    private static final Logger LOGGER = Logger.getLogger(Day5.class.getName());

    private Day5() {
    }

    public static void run() throws NoSuchAlgorithmException {
        String input = "abbhdwsy";
        MessageDigest md = MessageDigest.getInstance("MD5");
        List<String> hashCache = new ArrayList<>();

        int index = 0;
        while (hashCache.size() < 32) {
            byte[] digest = md.digest(((input + index).getBytes()));
            String hash = toHex(digest);
            if (hash.substring(0, 5).equals("00000")) {
                hashCache.add(hash);
            }
            index++;
        }

        StringBuilder passPart1 = new StringBuilder();
        char[] passPart2 = "........".toCharArray();

        for (String string : hashCache) {
            if (passPart1.length() < 8) {
                passPart1.append(string.charAt(5));
            }
            if (string.matches("00000[0-7].*")) {
                int pos = Integer.parseInt(string.substring(5, 6));
                if (passPart2[pos] == '.') {
                    passPart2[pos] = string.charAt(6);
                    LOGGER.info(() -> Arrays.toString(passPart2));
                }
            }

        }

        LOGGER.info(() -> String.format("Part 1 : Password: %s", passPart1.toString()));
        LOGGER.info(() -> String.format("Part 2 : Password: %s", String.copyValueOf(passPart2)));
    }

    private static String toHex(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

}
