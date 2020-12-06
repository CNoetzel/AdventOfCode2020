package de.divisionby0.adventofcode.day1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

public class Day1 {

    public static void main(String[] args) throws IOException {
        String path = Objects.requireNonNull(Day1.class.getClassLoader().getResource("day1/input.txt")).getPath();
        List<String> numbers = Files.readAllLines(new File(path).toPath(), StandardCharsets.UTF_8);

        // Two numbers
        numbers.stream().anyMatch(number1 -> numbers.stream().anyMatch(number2 -> {
            int n1 = Integer.parseInt(number1);
            int n2 = Integer.parseInt(number2);
            if ((n1 + n2) == 2020) {
                System.out.println("Answer 1:" + n1 * n2);
                return true;
            }
            return false;
        }));

        // Three numbers
        numbers.stream().anyMatch(number1 -> numbers.stream().anyMatch(number2 -> numbers.stream().anyMatch(number3 -> {
            int n1 = Integer.parseInt(number1);
            int n2 = Integer.parseInt(number2);
            int n3 = Integer.parseInt(number3);
            if ((n1 + n2 + n3) == 2020) {
                System.out.println("Answer 2:" + n1 * n2 * n3);
                return true;
            }
            return false;
        })));

    }
}
