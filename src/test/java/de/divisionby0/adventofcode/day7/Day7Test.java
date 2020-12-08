package de.divisionby0.adventofcode.day7;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Day7Test {

    private static final String[] RULES = {"light red bags contain 1 bright white bag, 2 muted yellow bags.",
                                            "dark orange bags contain 3 bright white bags, 4 muted yellow bags.",
                                            "bright white bags contain 1 shiny gold bag.",
                                            "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.",
                                            "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.",
                                            "dark olive bags contain 3 faded blue bags, 4 dotted black bags.",
                                            "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.",
                                            "faded blue bags contain no other bags.",
                                            "dotted black bags contain no other bags.",};

    @Test
    public void testGetBagsFromRules() {

        List<Bag> bags = Day7.getBagsFromRules(Arrays.asList(RULES));

        Bag red = new Bag("light red");
        Bag orange = new Bag("dark orange");
        Bag white = new Bag("bright white");
        Bag yellow = new Bag("muted yellow");
        Bag gold = new Bag("shiny gold");
        Bag blue = new Bag("faded blue");

        // check overall size
        assertEquals(9, bags.size());

        // check red
        assertEquals(red, bags.get(0));
        assertEquals(3, bags.get(0).getOtherBags().size());
        assertAmountOfBags(0, 1, white, bags.get(0));
        assertAmountOfBags(1, 2, yellow, bags.get(0));

        // check orange
        assertEquals(orange, bags.get(1));
        assertEquals(7, bags.get(1).getOtherBags().size());
        assertAmountOfBags(0, 3, white, bags.get(1));
        assertAmountOfBags(3, 4, yellow, bags.get(1));

        // check white
        assertEquals(white, bags.get(2));
        assertEquals(1, bags.get(2).getOtherBags().size());
        assertAmountOfBags(0, 1, gold, bags.get(2));

        // check yellow
        assertEquals(yellow, bags.get(3));
        assertEquals(11, bags.get(3).getOtherBags().size());
        assertAmountOfBags(0, 2, gold, bags.get(3));
        assertAmountOfBags(2, 9, blue, bags.get(3));
    }

    @Test
    public void testCountBagsWithAtLeastOneMatch() {
        Bag gold = new Bag("shiny gold");
        List<Bag> bags = Day7.getBagsFromRules(Arrays.asList(RULES));

        long count = Day7.countBagsWithAtLeastOneMatch(bags, gold);

        assertEquals(4, count);
    }

    @Test
    public void testCanContain() {
        Bag bagToFind = new Bag("shiny gold");
        assertTrue(Day7.doesContain(bagToFind, bagToFind));

        // no children
        Bag purple = new Bag("purple");
        assertFalse(Day7.doesContain(purple, bagToFind));

        // direct child
        Bag blue = new Bag("blue");
        blue.setOtherBags(Arrays.asList(bagToFind));
        assertTrue(Day7.doesContain(blue, bagToFind));

        // grandchild
        purple.setOtherBags(Arrays.asList(blue));
        assertTrue(Day7.doesContain(purple, bagToFind));

        // mixed children
        Bag black = new Bag("black");
        Bag brown = new Bag("brown");
        black.setOtherBags(Arrays.asList(bagToFind, brown));
        assertTrue(Day7.doesContain(black, bagToFind));

        // mixed grandchildren
        Bag navy = new Bag("navy");
        navy.setOtherBags(Arrays.asList(black));
        assertTrue(Day7.doesContain(navy, bagToFind));

    }

    @Test
    public void testGetSize() {
        List<Bag> bags = Day7.getBagsFromRules(Arrays.asList(RULES));
        Bag goldenBag = bags.stream()
                .filter(bag -> bag.getColor().equals("shiny gold"))
                .findFirst()
                .orElseGet(null);

        assertEquals(32, Day7.getNumberOfContainingBags(goldenBag));
    }

    private void assertAmountOfBags(int offset, int number, Bag bag, Bag parent) {
        for (int i = offset; i < number; i++) {
            assertEquals(bag, parent.getOtherBags().get(i));
        }
    }
}
