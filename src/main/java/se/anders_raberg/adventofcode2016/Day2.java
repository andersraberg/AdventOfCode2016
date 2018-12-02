package se.anders_raberg.adventofcode2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Day2 {
    private static final Logger LOGGER = Logger.getLogger(Day2.class.getName());

    private enum Direction {
        L(0, -1), R(0, 1), U(-1, 0), D(1, 0);

        private final int _vertStep;
        private final int _horizStep;

        private Direction(int vertStep, int horizStep) {
            _vertStep = vertStep;
            _horizStep = horizStep;
        }
    }

    private static final Character[][] KEY_PAD_PART_1 = { //
            { ' ', ' ', ' ', ' ', ' ' }, //
            { ' ', '1', '2', '3', ' ' }, //
            { ' ', '4', '5', '6', ' ' }, //
            { ' ', '7', '8', '9', ' ' }, //
            { ' ', ' ', ' ', ' ', ' ' } };

    private static final Character[][] KEY_PAD_PART_2 = { //
            { ' ', ' ', '1', ' ', ' ' }, //
            { ' ', '2', '3', '4', ' ' }, //
            { '5', '6', '7', '8', '9' }, //
            { ' ', 'A', 'B', 'C', ' ' }, //
            { ' ', ' ', 'D', ' ', ' ' } };

    public static void run() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/input2.txt"));

        List<List<Direction>> iterations = lines.stream() //
                .map(l -> Arrays //
                        .stream(l.split("")) //
                        .map(Direction::valueOf) //
                        .collect(Collectors.toList())) //
                .collect(Collectors.toList());

        LOGGER.info("Part 1 : Key sequence: " + getSequence(KEY_PAD_PART_1, 1, 1, iterations));
        LOGGER.info("Part 2 : Key sequence: " + getSequence(KEY_PAD_PART_2, 1, 1, iterations));
    }

    private static List<Character> getSequence(Character[][] keyPad, int startX, int startY,
            List<List<Direction>> iterations) {
        int xPos = startX;
        int yPos = startY;
        List<Character> sequence = new ArrayList<>();
        for (List<Direction> iter : iterations) {

            for (Direction d : iter) {
                int tmpXPos = Math.max(0, Math.min(4, xPos + d._horizStep));
                int tmpYPos = Math.max(0, Math.min(4, yPos + d._vertStep));
                if (keyPad[tmpYPos][tmpXPos] != ' ') {
                    xPos = tmpXPos;
                    yPos = tmpYPos;
                }
            }
            sequence.add(keyPad[yPos][xPos]);
        }
        return sequence;
    }

}
