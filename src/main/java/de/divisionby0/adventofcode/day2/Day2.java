package de.divisionby0.adventofcode.day2;

import de.divisionby0.adventofcode.day1.Day1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 {

    public static void main(String[] args) throws IOException {
        String path = Day1.class.getClassLoader().getResource("day2/input.txt").getPath();
        List<String> passwords = Files.readAllLines(new File(path).toPath(), StandardCharsets.UTF_8);

        // Policy 1
        List<String> validPasswordsPolicy1 = passwords.stream().filter(line -> {
            String[] entries = line.split(" ");
            int minVal = Integer.parseInt(entries[0].split("-")[0]);
            int maxVal = Integer.parseInt(entries[0].split("-")[1]);
            char letter = entries[1].charAt(0);

            long occurrences = entries[2].chars().filter(ch -> ch == letter).count();
            return occurrences >= minVal &&  occurrences <= maxVal;
        }).collect(Collectors.toList());

        System.out.println("Number of valid passwords according to first policy: " + validPasswordsPolicy1.size());

        // Policy 2
        List<String> validPasswordsPolicy2 = passwords.stream().filter(line -> {
            String[] entries = line.split(" ");
            int index1 = Integer.parseInt(entries[0].split("-")[0])-1; // index offset
            int index2 = Integer.parseInt(entries[0].split("-")[1])-1; // index offset
            char letter = entries[1].charAt(0);

            boolean pos1Match = entries[2].length() >= index1 && entries[2].charAt(index1) == letter;
            boolean pos2Match = entries[2].length() >= index2 && entries[2].charAt(index2) == letter;
            return  pos1Match ^ pos2Match; // xor exactly one match
        }).collect(Collectors.toList());

        System.out.println("Number of valid passwords according to second policy: " + validPasswordsPolicy2.size());

    }
}
