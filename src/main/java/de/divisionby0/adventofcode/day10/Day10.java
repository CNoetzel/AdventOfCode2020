package de.divisionby0.adventofcode.day10;

import de.divisionby0.adventofcode.day1.Day1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class Day10 {

    private static final int OUTLET_JOLTAGE = 0;
    private static final int DEVICE_JOLTAGE_OFFSET = 3;
    protected static Set<List<Integer>> solutions = new HashSet<>();

    public static void main(String[] args) throws IOException {
        String path = Objects.requireNonNull(Day1.class.getClassLoader().getResource("day10/input.txt")).getPath();
        List<Integer> adapters = Files.readAllLines(new File(path).toPath(), StandardCharsets.UTF_8)
                .stream().map(Integer::parseInt).sorted().collect(Collectors.toList());

        // Part 1
        int diffCount[] = calculateDifferences(adapters);
        String differences = Arrays.stream(diffCount).mapToObj(String::valueOf).collect(Collectors.joining(","));
        System.out.println("The following differences were calculated: " + differences);
        System.out.println("The value we are looking for is: " + diffCount[1] * diffCount[3]);

        // Part 2 -> takes very long to calculate
        calculatePossibleArrangements(adapters);
        System.out.println("There are " + solutions.size() + " arrangements which connect your device to the outlet.");

    }

    protected static int[] calculateDifferences(List<Integer> sortedListOfAdapters) {
        int[] diffCount = new int[4];

        // set initial value for the difference of first adapter to outlet
        diffCount[sortedListOfAdapters.get(0)] = 1;

        for (int i = 0; i < sortedListOfAdapters.size(); i++) {
            // compare to next
            int diff = 0;
            if (i == sortedListOfAdapters.size() - 1) {
                diff = Math.abs(sortedListOfAdapters.get(i) - sortedListOfAdapters.get(i) + DEVICE_JOLTAGE_OFFSET);
            } else {
                diff = Math.abs(sortedListOfAdapters.get(i) - sortedListOfAdapters.get(i + 1));
            }
            diffCount[diff] = ++diffCount[diff];
        }
        return diffCount;
    }

    protected static void calculatePossibleArrangements(List<Integer> sortedListOfAdapters) {
        solutions.add(sortedListOfAdapters);
        System.out.println("Current solutions: " + solutions.size());
        List<Integer> omittableAdapters = findOmittableAdapters(sortedListOfAdapters);
        for (int i = 0; i < omittableAdapters.size(); i++) {
            ArrayList<Integer> workingCopy = new ArrayList<>(sortedListOfAdapters);
            workingCopy.remove(omittableAdapters.get(i));
            calculatePossibleArrangements(workingCopy);
        }
    }

    private static List<Integer> findOmittableAdapters(List<Integer> sortedListOfAdapters) {
        List<Integer> omittableAdapters = new ArrayList<>();
        for (int index = 0; index < sortedListOfAdapters.size(); index++) {
            if (index == 0) {
                // compare first adapter to outlet
                if (isOmittable(OUTLET_JOLTAGE, sortedListOfAdapters.get(index+1))) {
                    omittableAdapters.add(sortedListOfAdapters.get(index));
                }
            } else if (index == sortedListOfAdapters.size()-1) {
                // compare last adapter to device
                if (isOmittable(sortedListOfAdapters.get(index-1), sortedListOfAdapters.get(index)+DEVICE_JOLTAGE_OFFSET)) {
                    omittableAdapters.add(sortedListOfAdapters.get(index));
                }
            } else {
                if (isOmittable(sortedListOfAdapters.get(index-1), sortedListOfAdapters.get(index+1))) {
                    omittableAdapters.add(sortedListOfAdapters.get(index));
                }
            }
        }
        return omittableAdapters;
    }

    private static boolean isOmittable(int before, int after) {
        return after-before <= 3;
    }
}
