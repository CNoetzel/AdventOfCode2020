package de.divisionby0.adventofcode.day9;

import de.divisionby0.adventofcode.day1.Day1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day9 {

    private static final int PREAMBLE_SIZE = 25;

    public static void main(String[] args) throws IOException {
        String path = Objects.requireNonNull(Day1.class.getClassLoader().getResource("day9/input.txt")).getPath();
        List<Long> numbers = Files.readAllLines(new File(path).toPath(), StandardCharsets.UTF_8)
                .stream().map(Long::parseLong).collect(Collectors.toList());

        // Part 1
        long valueToCheck = 0;
        List<Long> preamble;
        int preambleUpperIndex = PREAMBLE_SIZE;
        do {
            valueToCheck = numbers.get(preambleUpperIndex);
            preamble = numbers.subList(preambleUpperIndex - PREAMBLE_SIZE, preambleUpperIndex);
            preambleUpperIndex++;
        } while (isValid(valueToCheck, preamble));

        System.out.println("The value we are looking for is: " + valueToCheck);

        // Part 2
        long sumToFind = 22477624L;
        // we do not have to check the whole amount of number, so we shrink the initial list
        List<Long> numbersShrinked = numbers.subList(0, numbers.indexOf((sumToFind)));
        List<Long> values = getNumbersSumToValue(sumToFind, numbersShrinked);

        System.out.println("The following values sum to " + sumToFind + ": " + values);
        Collections.sort(values);
        long sumOfSmallestAndLargestValue = values.get(0)+values.get(values.size()-1);
        System.out.println("The encryption weakness is: " + sumOfSmallestAndLargestValue);
    }

    protected static List<Long> getNumbersSumToValue(long value, List<Long> numbers) {
        // at least two numbers have to be summed
        int range = 2;
        int index = 0;
        List<Long> scope = new ArrayList<>();

        do {
            // check if we would exceed the range of the list
            if(index+range < numbers.size()) {
                scope = numbers.subList(index, index + range);
                index++;
            } else {
                // increase range and reset index when end is reached
                range++;
                index = 0;
            }
        } while (!doesSumToValue(value, scope));

        return scope;
    }

    protected static boolean doesSumToValue(long value, List<Long> numbers) {
        long sum = numbers.stream().reduce(0L, Long::sum);
        return sum == value;
    }

    protected static boolean isValid(long number, List<Long> preamble) {
        return preamble.stream()
                .anyMatch(x -> preamble.stream()
                        .anyMatch(y -> (x + y == number) && (preamble.indexOf(x) != preamble.indexOf(y))));
    }
}
