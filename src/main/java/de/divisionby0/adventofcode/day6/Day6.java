package de.divisionby0.adventofcode.day6;

import de.divisionby0.adventofcode.day1.Day1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class Day6 {

    public static void main(String[] args) throws IOException {
        String path = Objects.requireNonNull(Day1.class.getClassLoader().getResource("day6/input.txt")).getPath();
        List<String> answerInput = Files.readAllLines(new File(path).toPath(), StandardCharsets.UTF_8);

        // Part 1
        List<Set<Character>> answers1 = groupAnswersAndEliminateDuplicates(answerInput);
        int sum1 = answers1.stream().mapToInt(Set::size).sum();

        System.out.println("Sum of answers anyone in group answered is: " + sum1);

        // Part 2
        List<Set<Character>> answers2 = groupAnswersAndCountDuplicates(answerInput);
        int sum2 = answers2.stream().mapToInt(Set::size).sum();

        System.out.println("Sum of answers everyone in group answered is: " + sum2);

    }

    protected static List<Set<Character>> groupAnswersAndEliminateDuplicates(List<String> input) {
        List<Set<Character>> groupedAnswers = new ArrayList<>();
        groupedAnswers.add(new HashSet<>()); // init with empty set
        int grpIndex = 0;

        for (String line : input) {
            // new line means new group in list
            if (line.isEmpty()) {
                grpIndex++;
                groupedAnswers.add(new HashSet<>()); // init with empty set
            } else {
                Set<Character> charsSet = stringToCharacterSet(line);
                // use a set to eliminate duplicates
                groupedAnswers.get(grpIndex).addAll(charsSet);
            }
        }
        return groupedAnswers;
    }

    protected static List<Set<Character>> groupAnswersAndCountDuplicates(List<String> input) {
        List<Set<Character>> groupedAnswers = new ArrayList<>();
        groupedAnswers.add(stringToCharacterSet(input.get(0))); // init with first input of group
        int grpIndex = 0;

        for (int i = 0; i < input.size(); i++) {
            // new line means new group in list
            String line = input.get(i);
            if (line.isEmpty()) {
                grpIndex++;
                groupedAnswers.add(stringToCharacterSet(input.get(i+1))); // init with first input of group
            } else {
                Set<Character> charsSet = stringToCharacterSet(line);
                // use intersection of answers
                Set<Character> intersection = groupedAnswers.get(grpIndex).stream()
                        .distinct()
                        .filter(charsSet::contains)
                        .collect(Collectors.toSet());
                groupedAnswers.set(grpIndex, intersection);
            }
        }
        return groupedAnswers;
    }

    private static Set<Character> stringToCharacterSet(String string) {
       return string.chars().mapToObj(e->(char)e).collect(Collectors.toSet());
    }
}
