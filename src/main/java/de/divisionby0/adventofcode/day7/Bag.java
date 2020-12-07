package de.divisionby0.adventofcode.day7;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Bag {

    private String color;
    private List<Bag> otherBags;


    public Bag(String color) {
        this.color = color;
        this.otherBags = new ArrayList<>();
    }

    public String getColor() {
        return color;
    }

    public List<Bag> getOtherBags() {
        return otherBags;
    }

    public void setOtherBags(List<Bag> otherBags) {
        this.otherBags = otherBags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bag bag = (Bag) o;
        return color.equals(bag.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }

    @Override
    public String toString() {
        return "Bag{" +
                "color='" + color + '\'' +
                ", otherBags=" + otherBags.stream().map(Object::toString).collect(Collectors.joining(",")) +
                '}';
    }
}
