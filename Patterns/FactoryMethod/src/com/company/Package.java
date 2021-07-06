package com.company;

public class Package {
    private String description;
    private int weight;

    public Package(String _description, int _weight) {
        description = _description;
        weight = _weight;
    }

    public String getDescription() {
        return description;
    }
    public int getWeight() {
        return weight;
    }
}
