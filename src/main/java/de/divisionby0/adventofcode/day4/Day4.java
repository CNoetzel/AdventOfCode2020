package de.divisionby0.adventofcode.day4;

import de.divisionby0.adventofcode.day1.Day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

public class Day4 {

    private static String[] MANDATORY_ENTRIES = {"byr","iyr","eyr","hgt","hcl","ecl","pid"};

    private static String[] EYE_COLORS = {"amb","blu","brn","gry","grn","hzl","oth"};

    public static void main(String[] args) throws IOException {
        String path = Day1.class.getClassLoader().getResource("day4/input.txt").getPath();

        List<String> passports = getPassportData(path);

        // Part 1
        long validPassportsPart1 = passports.stream()
                .filter(passport -> Arrays.stream(MANDATORY_ENTRIES).allMatch(passport::contains))
                .count();

        System.out.println("Valid passports part1: " + validPassportsPart1);

        // Part 2
        long validPassportsPart2 = passports.stream().filter(passport -> {
            // Check if mandatory fields are present
            boolean valid = false;
            if (Arrays.stream(MANDATORY_ENTRIES).allMatch(passport::contains)) {
                // Check details
                String[] details = passport.trim().split(" ");
                valid = Arrays.stream(details).allMatch(detail -> {
                    String[] keyVal = detail.trim().split(":");
                    return isPassportDetailValid(keyVal[0], keyVal[1]);
                });
            }
            return valid;
        }).count();

        System.out.println("Valid passports part2: " + validPassportsPart2);
    }

    private static List<String> getPassportData(String pathToFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(pathToFile));

        try {
            List<String> passports = new ArrayList<>(Collections.singletonList(""));
            int documentIndex = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) {
                    passports.add("");
                    documentIndex = passports.size() - 1;
                } else {
                    String updateData = passports.get(documentIndex) + " " + line;
                    passports.set(documentIndex, updateData);
                }
            }
            return passports;
        } finally {
            scanner.close();
        }
    }

    protected static boolean isPassportDetailValid(String key, String value) {
        switch (key) {
            case "byr":
                return Integer.parseInt(value) >= 1920 && Integer.parseInt(value) <= 2002;
            case "iyr":
                return Integer.parseInt(value) >= 2010 && Integer.parseInt(value) <= 2020;
            case "eyr":
                return Integer.parseInt(value) >= 2020 && Integer.parseInt(value) <= 2030;
            case "hgt":
                if (value.endsWith("cm")) {
                    int size = Integer.parseInt(value.replaceAll("cm",""));
                    return size >= 150 && size <= 193;
                } else if (value.endsWith("in")) {
                    int size = Integer.parseInt(value.replaceAll("in",""));
                    return size >= 59 && size <= 76;
                } else {
                    return false;
                }
            case "hcl":
                return value.matches("#[0-9a-f]{6}");
            case "ecl":
                return Arrays.asList(EYE_COLORS).stream().anyMatch(value::contains);
            case "pid":
                return value.matches("[0-9]{9}");
            case "cid":
                return true; // always valid
            default:
                return false;
        }
    }
}
