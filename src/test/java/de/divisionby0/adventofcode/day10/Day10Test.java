package de.divisionby0.adventofcode.day10;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Test {

    private static final Integer[] SMALL_INPUT = {1,4,5,6,7,10,11,12,15,16,19};

    @Test
    public void testCalculateDifferences() {
        int[] expectedOutput = new int[]{0,7,0,5};
        int[] differences = Day10.calculateDifferences(Arrays.asList(SMALL_INPUT));

        assertArrayEquals(expectedOutput,differences);
    }

    @Test
    public void testPossibleArrangements() {
        List<Integer> list =  new ArrayList<>();
        list.addAll(Arrays.asList(SMALL_INPUT));
        assertEquals(8, Day10.calculatePossibleArrangements(0, list));
    }
}