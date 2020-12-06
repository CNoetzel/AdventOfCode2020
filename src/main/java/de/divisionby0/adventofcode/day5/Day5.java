package de.divisionby0.adventofcode.day5;

import de.divisionby0.adventofcode.day1.Day1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Day5 {

    private static final int MIN_ROW = 0;
    private static final int MAX_ROW = 127;
    private static final int MIN_COLUMN = 0;
    private static final int MAX_COLUMN = 7;

    public static void main(String[] args) throws IOException {
        String path = Day1.class.getClassLoader().getResource("day5/input.txt").getPath();
        List<String> seatCodes = Files.readAllLines(new File(path).toPath(), StandardCharsets.UTF_8);

        // Part 1
        List<Integer> seatIds = seatCodes.stream().map(code -> calculateSeatId(code)).collect(Collectors.toList());
        Collections.sort(seatIds, Collections.reverseOrder());

        System.out.println("Highest seat id is: " + seatIds.get(0));

        // Part 2
        int mySeatID = 0;
        for (int i = 0; i < seatIds.size()-1; i++) {
            if (seatIds.get(i) - seatIds.get(i+1) > 1) {
                mySeatID = seatIds.get(i) - 1;
                break;
            }
        }
        System.out.println("My seat id is: " + mySeatID);

    }

    protected static int calculateSeatId(String seatCode) {
        int row = determineRow(seatCode.substring(0,7), MIN_ROW, MAX_ROW);
        int column = determineColumn(seatCode.substring(7,10), MIN_COLUMN, MAX_COLUMN);
        return (row * 8) + column;
    }

    protected static int determineRow(String code, int lowerBound, int upperBound) {
        // check for last character and return lower or upper bound
        if(code.length() == 1) {
            return code.startsWith("F") ? lowerBound : upperBound;
        }
        // otherwise determine new bounds
        if (code.startsWith("F")) {
            upperBound = (int)Math.floor((upperBound - lowerBound) / 2.0) + lowerBound;
        } else {
            lowerBound = (int)Math.ceil((upperBound - lowerBound) / 2.0) + lowerBound;
        }
        return determineRow(code.substring(1), lowerBound, upperBound);
    }

    protected static int determineColumn(String code, int lowerBound, int upperBound) {
        // check for last character and return lower or upper bound
        if(code.length() == 1) {
            return code.startsWith("L") ? lowerBound : upperBound;
        }
        // otherwise determine new bounds
        if (code.startsWith("L")) {
            upperBound = (int)Math.floor((upperBound - lowerBound) / 2.0) + lowerBound;
        } else {
            lowerBound = (int)Math.ceil((upperBound - lowerBound) / 2.0) + lowerBound;
        }
        return determineColumn(code.substring(1), lowerBound, upperBound);
    }

}
