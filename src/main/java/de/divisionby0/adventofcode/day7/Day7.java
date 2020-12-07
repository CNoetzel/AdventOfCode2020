package de.divisionby0.adventofcode.day7;

import de.divisionby0.adventofcode.day1.Day1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class Day7 {

    private static final Map<String, Bag> BAGS = new HashMap<>();


    public static void main(String[] args) throws IOException {
        String path = Objects.requireNonNull(Day1.class.getClassLoader().getResource("day7/input.txt")).getPath();
        List<String> bagRules = Files.readAllLines(new File(path).toPath(), StandardCharsets.UTF_8);

        List<Bag> bags = getBagsFromRules(bagRules);

        Bag goldenBag = BAGS.get("shiny gold");

        // Part 1
        long count1 = countBagsWithAtLeastOneMatch(bags, goldenBag);
        System.out.println("A shiny gold bag can be found in " + count1 + " other bags.");

        // Part 2
        long count2 = getSize(BAGS.get("shiny gold"));
        System.out.println("A shiny gold bag contains " + count2 + " other bags.");
    }

    protected static long getSize(Bag bag) {
        if (bag.getOtherBags() == null || bag.getOtherBags().isEmpty()) {
            return 0;
        }
        long ownSize = bag.getOtherBags().size();
        long childrenSize = 0;
        for (Bag child : bag.getOtherBags()) {
            childrenSize += getSize(child);
        }
        return ownSize + childrenSize;
    }

    protected static long countBagsWithAtLeastOneMatch(List<Bag> bags, Bag bagToFind) {
        // -1 because the shiny gold bag is counted, but it is not included in another bag
        return bags.stream().filter(bag -> doesContain(bag, bagToFind)).count() - 1;
    }

    protected static boolean doesContain(Bag bagToSearchIn, Bag bagToFind) {
        if (bagToSearchIn.equals(bagToFind)) {
            return true;
        } else if (!bagToSearchIn.getOtherBags().isEmpty()) {
            return new HashSet<>(bagToSearchIn.getOtherBags()) // convert list to set to avoid repeated search
                    .stream()
                    .anyMatch(bag -> doesContain(bag, bagToFind));
        }
        return false;
    }

    protected static List<Bag> getBagsFromRules(List<String> bagRules) {
        return bagRules.stream().map(rule -> {
            // handle parent
            String[] parentChildSplit = rule.split("contain");
            String parentColor = parentChildSplit[0].replace("bags","").trim();
            Bag bag = getBag(parentColor);

            // handle children
            String[] childrenRule = parentChildSplit[1].split(",");
            List<Bag> children = Arrays.stream(childrenRule).map(childRule -> {
                if(childRule.trim().equals("no other bags.")) {
                    return new ArrayList<Bag>();
                }
                int childAmount = Integer.parseInt(childRule.trim().substring(0,1));
                String childColor = childRule.substring(2)
                        .replaceAll("bag[s]?[\\.]?", "")
                        .trim();
                Bag childBag = getBag(childColor);
                List<Bag> childBags = new ArrayList<>();
                for(int i=0; i<childAmount; i++) {
                    childBags.add(childBag);
                }
                return childBags;
            }).flatMap(Collection::stream).collect(Collectors.toList());
            bag.setOtherBags(children);

            return bag;
        }).collect(Collectors.toList());
    }

    private static Bag getBag(String color) {
        Bag bag ;
        if(BAGS.containsKey(color)) {
            bag = BAGS.get(color);
        } else {
            bag = new Bag(color);
            BAGS.put(color, bag);
        }
        return bag;
    }
}
