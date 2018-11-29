package se.anders_raberg.adventofcode2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day1 {
    private static final Logger LOGGER = Logger.getLogger(Day1.class.getName());

    private enum Direction {
        NORTH(1, 0), EAST(0, 1), SOUTH(-1, 0), WEST(0, -1);

        private final int _northSouth;
        private final int _eastWest;

        private Direction(int northSouth, int eastWest) {
            _northSouth = northSouth;
            _eastWest = eastWest;
        }

        public Direction turnRight() {
            switch (this) {
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            }
            throw new IllegalStateException();
        }

        public Direction turnLeft() {
            switch (this) {
            case NORTH:
                return WEST;
            case WEST:
                return SOUTH;
            case SOUTH:
                return EAST;
            case EAST:
                return NORTH;
            }
            throw new IllegalStateException();
        }

    }

    private enum Turn {
        L, R;
    }

    private static class Position {
        private final int _northSouth;
        private final int _eastWest;

        public Position(int northSouth, int eastWest) {
            _northSouth = northSouth;
            _eastWest = eastWest;
        }

        @Override
        public int hashCode() {
            return Objects.hash(_eastWest, _northSouth);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Position other = (Position) obj;
            return _eastWest == other._eastWest && _northSouth == other._northSouth;
        }

        @Override
        public String toString() {
            return "Position [_northSouth=" + _northSouth + ", _eastWest=" + _eastWest + "]";
        }
    }

    private static Position _firstRevisitedposition = null;
    private static Set<Position> _visitedPositions = new HashSet<>();

    public static void run() throws IOException {
        String[] iterations = new String(Files.readAllBytes(Paths.get("inputs/input1.txt"))).trim().split(", ");
        Direction dir = Direction.NORTH;
        int northSouthSteps = 0;
        int eastWestSteps = 0;

        for (String iter : iterations) {
            Turn turn = Turn.valueOf(iter.substring(0, 1));
            int steps = Integer.valueOf(iter.substring(1, iter.length()));
            switch (turn) {
            case L:
                dir = dir.turnLeft();
                break;
            case R:
                dir = dir.turnRight();
                break;
            default:
                throw new IllegalStateException();
            }

            for (int i = 0; i < steps; i++) {
                northSouthSteps += dir._northSouth;
                eastWestSteps += dir._eastWest;

                if (!_visitedPositions.add(new Position(northSouthSteps, eastWestSteps))
                        && _firstRevisitedposition == null) {

                    _firstRevisitedposition = new Position(northSouthSteps, eastWestSteps);
                }
            }
        }
        LOGGER.log(Level.INFO, "Distance: " + (Math.abs(northSouthSteps) + Math.abs(eastWestSteps)));
        LOGGER.log(Level.INFO, "Pos: "
                + (Math.abs(_firstRevisitedposition._northSouth) + Math.abs(_firstRevisitedposition._eastWest)));
    }
}
