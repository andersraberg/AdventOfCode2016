package se.anders_raberg.adventofcode2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Day3 {
    private static final Logger LOGGER = Logger.getLogger(Day3.class.getName());
    
    private Day3() {
    }

    private static class TriangleCandidate {
        private final long _sideA;
        private final long _sideB;
        private final long _sideC;

        public TriangleCandidate(long sideA, long sideB, long sideC) {
            _sideA = sideA;
            _sideB = sideB;
            _sideC = sideC;
        }

        public boolean valid() {
            return _sideA + _sideB > _sideC && //
                    _sideA + _sideC > _sideB && //
                    _sideB + _sideC > _sideA;
        }

        public static TriangleCandidate valueOf(String s) {
            String[] sides = s.trim().split("\\W+");
            return new TriangleCandidate( //
                    Long.valueOf(sides[0]), //
                    Long.valueOf(sides[1]), //
                    Long.valueOf(sides[2]));
        }

        public static TriangleCandidate valueOf(List<String> sides) {
            return new TriangleCandidate( //
                    Long.valueOf(sides.get(0)), //
                    Long.valueOf(sides.get(1)), //
                    Long.valueOf(sides.get(2)));
        }
    }

    public static void run() throws IOException {
        List<String> allLines = Files.readAllLines(Paths.get("inputs/input3.txt"));

        // Part 1
        long count = allLines //
                .stream() //
                .map(TriangleCandidate::valueOf) //
                .filter(TriangleCandidate::valid).count();

        // Part 2
        List<TriangleCandidate>  candidates = new ArrayList<>();
        for (int i = 0; i < allLines.size(); i = i + 3) {
            String[] sp0 = split(allLines.get(i));
            String[] sp1 = split(allLines.get(i + 1));
            String[] sp2 = split(allLines.get(i + 2));
            
            candidates.add(TriangleCandidate.valueOf(Arrays.asList(sp0[0], sp1[0], sp2[0])));
            candidates.add(TriangleCandidate.valueOf(Arrays.asList(sp0[1], sp1[1], sp2[1])));
            candidates.add(TriangleCandidate.valueOf(Arrays.asList(sp0[2], sp1[2], sp2[2])));
        }

        long count2 = candidates.stream().filter(TriangleCandidate::valid).count();
        LOGGER.info(() -> String.format("Part 1: Valid trangles: %s", count));
        LOGGER.info(() -> String.format("Part 2: Valid trangles: %s", count2));
    }
    
    private static String[] split(String s) {
        return s.trim().split("\\W+");
    }

}
