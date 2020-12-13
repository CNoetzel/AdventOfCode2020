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

    private static final Map<Integer, Map<List<Integer>, Long>> cache = new HashMap<>();

    public static void main(String[] args) throws IOException {
        String path = Objects.requireNonNull(Day1.class.getClassLoader().getResource("day10/input.txt")).getPath();
        List<Integer> adapters = Files.readAllLines(new File(path).toPath(), StandardCharsets.UTF_8)
                .stream().map(Integer::parseInt).sorted().collect(Collectors.toList());

        // Part 1
        int diffCount[] = calculateDifferences(adapters);
        String differences = Arrays.stream(diffCount).mapToObj(String::valueOf).collect(Collectors.joining(","));
        System.out.println("The following differences were calculated: " + differences);
        System.out.println("The value we are looking for is: " + diffCount[1] * diffCount[3]);

        // Part 2
        long arrangements = calculatePossibleArrangements(0, adapters);
        System.out.println("There are " + arrangements + " arrangements which connect your device to the outlet.");

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

    protected static long calculatePossibleArrangements(Integer prevAdapter, List<Integer> remainingAdapters) {
        if (remainingAdapters.isEmpty()) {
            return 1L;
        }

        // if count already present return it
        Long count = getFromCache(prevAdapter, remainingAdapters);
        if (count != null) {
            return count;
        }

        // calculate number of arrangements
        Integer nextAdapter = remainingAdapters.remove(0);
        count = calculatePossibleArrangements(nextAdapter, remainingAdapters);

        if (!remainingAdapters.isEmpty() && remainingAdapters.get(0) - prevAdapter <= 3) {
            count += calculatePossibleArrangements(prevAdapter, remainingAdapters);
        }
        remainingAdapters.add(0, nextAdapter);
        addToCache(prevAdapter, remainingAdapters, count);

        return count;
    }

    private static void addToCache(int previous, List<Integer> remainingAdapters, Long count) {
        Map<List<Integer>, Long> map = new HashMap<>();
        map.put(remainingAdapters, count);
        cache.put(previous, map);
    }

    private static Long getFromCache(int previous, List<Integer> remainingAdapters) {
        Map<List<Integer>, Long> map = cache.get(previous);
        return map != null ? map.get(remainingAdapters) : null;
    }
}
