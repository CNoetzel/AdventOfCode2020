package de.divisionby0.adventofcode.day8;

import de.divisionby0.adventofcode.day1.Day1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class Day8 {

    private static int LAST_INDEX_MODIFIED = 0; // Store position of last modified instruction
    private static int INDEX = 0;               // Store position of last executed instuction

    public static void main(String[] args) throws IOException {
        String path = Objects.requireNonNull(Day1.class.getClassLoader().getResource("day8/input.txt")).getPath();
        List<String> instructions = Files.readAllLines(new File(path).toPath(), StandardCharsets.UTF_8);

        // Part 1
        long accumulator1 = calculateAccumulator(instructions);
        System.out.println("Accumulator value before repeated execution: " + accumulator1);

        // Part 2
        long accumulator2 = 0;
        while(INDEX != instructions.size()-1) {
            // modify instruction
            List<String> modifiedInstructions = modifyInstructions(instructions);

            // calculate accumulator
            INDEX = 0;
            accumulator2 = calculateAccumulator(modifiedInstructions);
        }
        System.out.println("Accumulator after successful execution: " + accumulator2);
    }

    private static List<String> modifyInstructions(List<String> instructions) {
        List<String> instructionCopy = instructions.stream().map(String::new).collect(Collectors.toList());

        int i;
        for (i = LAST_INDEX_MODIFIED; i < instructionCopy.size(); i++) {
            String[] instruction = instructionCopy.get(i).split(" ");
            if (instruction[0].equals("jmp")) {
                String newInstruction = instructionCopy.get(i).replace("jmp","nop");
                instructionCopy.remove(i);
                instructionCopy.add(i, newInstruction);
                break;
            } else if (instruction[0].equals("nop")) {
                String newInstruction = instructionCopy.get(i).replace("nop","jmp");
                instructionCopy.remove(i);
                instructionCopy.add(i, newInstruction);
                break;
            }
        }
        LAST_INDEX_MODIFIED = ++i; // update because break; won't do it

        return instructionCopy;
    }


    private static long calculateAccumulator(List<String> instructions) {
        Set<Integer> visitedIndexes = new HashSet<>();
        long accumulator = 0;

        while (!visitedIndexes.contains(INDEX) && INDEX != instructions.size()-1) {
            visitedIndexes.add(INDEX);

            String[] instruction = instructions.get(INDEX).split(" ");
            String command = instruction[0];
            int number = Integer.parseInt(instruction[1]);
            switch (command) {
                case "jmp":
                    INDEX += number;
                    break;
                case "acc":
                    accumulator += number;
                    INDEX++;
                    break;
                case "nop":
                    // do nothing
                    INDEX++;
                    break;
            }
        }
        return accumulator;
    }
}
