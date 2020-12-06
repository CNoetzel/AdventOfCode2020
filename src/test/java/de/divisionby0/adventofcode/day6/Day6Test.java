package de.divisionby0.adventofcode.day6;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day6Test {

    private static final String[] TEST_DATA = { "abc", "", "a", "b", "c", "", "ab", "ac", "", "a", "a", "a", "a", "", "b" };


    @Test
    public void testGroupAnswersAndEliminateDuplicates() {
        List<String> input = Arrays.asList(TEST_DATA);
        List<Set<Character>> output =  Day6.groupAnswersAndEliminateDuplicates(input);

        assertEquals(5, output.size());
        assertEquals(3, output.get(0).size());
        assertEquals(3, output.get(1).size());
        assertEquals(3, output.get(2).size());
        assertEquals(1, output.get(3).size());
        assertEquals(1, output.get(4).size());
    }

    @Test
    public void testGroupAnswersAndCountDuplicates() {
        List<String> input = Arrays.asList(TEST_DATA);
        List<Set<Character>> output =  Day6.groupAnswersAndCountDuplicates(input);

        assertEquals(5, output.size());
        assertEquals(3, output.get(0).size());
        assertEquals(0, output.get(1).size());
        assertEquals(1, output.get(2).size());
        assertEquals(1, output.get(3).size());
        assertEquals(1, output.get(4).size());
    }
}
