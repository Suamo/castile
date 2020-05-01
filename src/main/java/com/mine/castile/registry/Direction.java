package com.mine.castile.registry;

public enum Direction {
    UP("up"),
    LEFT("left"),
    RIGHT("right"),
    DOWN("down");

    private String name;

    private Direction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
