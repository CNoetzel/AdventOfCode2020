package de.divisionby0.adventofcode.day5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day5Test {

    @Test
    public void testCalculateSeatId() {
        assertEquals(357, Day5.calculateSeatId("FBFBBFFRLR"));
        assertEquals(567, Day5.calculateSeatId("BFFFBBFRRR"));
        assertEquals(119, Day5.calculateSeatId("FFFBBBFRRR"));
        assertEquals(820, Day5.calculateSeatId("BBFFBBFRLL"));
    }

    @Test
    public void testDetermineRow() {
        assertEquals(44, Day5.determineRow("FBFBBFF", 0, 127));
        assertEquals(70, Day5.determineRow("BFFFBBF", 0, 127));
        assertEquals(14, Day5.determineRow("FFFBBBF", 0, 127));
        assertEquals(102, Day5.determineRow("BBFFBBF", 0, 127));
    }

    @Test
    public void testDetermineColumn() {
        assertEquals(5, Day5.determineColumn("RLR", 0, 7));
        assertEquals(7, Day5.determineColumn("RRR", 0, 7));
        assertEquals(4, Day5.determineColumn("RLL", 0, 7));
        assertEquals(0, Day5.determineColumn("LLL", 0, 7));
    }

}
