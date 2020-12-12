package de.divisionby0.adventofcode.day11;

import de.divisionby0.adventofcode.day1.Day1;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Test {

    @Test
    public void testOccupySeats() throws IOException {
        String path = Objects.requireNonNull(Day1.class.getClassLoader().getResource("day11/input.txt")).getPath();
        String[][] seatMatrix = Day11.getMatrixFromFile(path);

        assertEquals(37, Day11.occupySeats(false, seatMatrix));
    }

    @Test
    public void testOccupySeatsAdvanced() throws IOException {
        String path = Objects.requireNonNull(Day1.class.getClassLoader().getResource("day11/input.txt")).getPath();
        String[][] seatMatrix = Day11.getMatrixFromFile(path);

        assertEquals(26, Day11.occupySeats(true, seatMatrix));
    }

    @Test
    public void testGetDirectionIndicatorIfSeatInSight() {
        // 0 1 2
        // 3 x 4
        // 5 6 7
        assertEquals(0, Day11.getDirectionIndicatorIfSeatInSight(1,1, 0,0));
        assertEquals(1, Day11.getDirectionIndicatorIfSeatInSight(1,1, 0,1));
        assertEquals(2, Day11.getDirectionIndicatorIfSeatInSight(1,1, 0,2));
        assertEquals(3, Day11.getDirectionIndicatorIfSeatInSight(1,1, 1,0));
        assertEquals(-1, Day11.getDirectionIndicatorIfSeatInSight(1,1, 1,1));
        assertEquals(4, Day11.getDirectionIndicatorIfSeatInSight(1,1, 1,2));
        assertEquals(5, Day11.getDirectionIndicatorIfSeatInSight(1,1, 2,0));
        assertEquals(6, Day11.getDirectionIndicatorIfSeatInSight(1,1, 2,1));
        assertEquals(7, Day11.getDirectionIndicatorIfSeatInSight(1,1, 2,2));
    }

    @Test
    public void testGetVisibleSeats() throws IOException {
        String path = Objects.requireNonNull(Day1.class.getClassLoader().getResource("day11/input2.txt")).getPath();
        String[][] seatMatrix = Day11.getMatrixFromFile(path);

        // middle
        String[] visibleSeats;
        visibleSeats = Day11.getVisibleSeats(3, 3, seatMatrix);
        assertArrayEquals(new String[8], visibleSeats);

        // corner up left
        visibleSeats = Day11.getVisibleSeats(0, 0, seatMatrix);
        assertArrayEquals(new String[]{null,null,null,null,"#",null,"#","L"}, visibleSeats);

        // corner up right
        visibleSeats = Day11.getVisibleSeats(0, 6, seatMatrix);
        assertArrayEquals(new String[]{null,null,null,"#",null,"L","#",null}, visibleSeats);

        // corner down left
        visibleSeats = Day11.getVisibleSeats(6, 0, seatMatrix);
        assertArrayEquals(new String[]{null,"#","L",null,"#",null,null,null}, visibleSeats);

        // corner down right
        visibleSeats = Day11.getVisibleSeats(6, 6, seatMatrix);
        assertArrayEquals(new String[]{"L","#",null,"#",null,null,null,null}, visibleSeats);
    }
}
