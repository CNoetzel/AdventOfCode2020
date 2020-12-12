package de.divisionby0.adventofcode.day3;

import de.divisionby0.adventofcode.day1.Day1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day3 {

    public static void main(String[] args) throws IOException {
        String path = Objects.requireNonNull(Day1.class.getClassLoader().getResource("day3/input.txt")).getPath();
        String[][] forest = Files.readAllLines(new File(path).toPath(), StandardCharsets.UTF_8)
                .stream()
                .map(line -> line.split("(?!^)"))
                .toArray(String[][]::new);

        // Part 1
        int slope0 = countTrees(forest, 1,3);

        System.out.println("Number of trees for part 1: " + slope0);

        // Part 2
        long slope1 = countTrees(forest, 1,1);
        long slope2 = countTrees(forest, 1,3);
        long slope3 = countTrees(forest, 1,5);
        long slope4 = countTrees(forest, 1,7);
        long slope5 = countTrees(forest, 2,1);

        long treesComplex = slope1 * slope2 * slope3 * slope4 * slope5;

        System.out.println("Number of trees for part 2: " + treesComplex);
    }

    private static int countTrees(String[][] forest, int stepsDown, int stepsRight) {
        int partialSize = forest[0].length;
        int lastLine = forest.length-1;
        int[] index = {0,0}; // first value is y-axis, second value is x-axis
        int treeCounter = 0;
        while(index[0] <= lastLine) {
            // Check if current position has a tree
            if (forest[index[0]][index[1]].equals("#")) {
                treeCounter++;
            }
            // Update path
            index[0] = index[0]+stepsDown; // y-axis no problem
            // x-axis consider size
            int yIndexNew = index[1]+stepsRight;
            index[1] = yIndexNew < partialSize ? yIndexNew : (yIndexNew - partialSize);
        }
        return treeCounter;
    }
}
