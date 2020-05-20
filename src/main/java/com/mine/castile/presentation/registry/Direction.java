package com.mine.castile.presentation.registry;

public enum Direction {
    UP("up", 1),
    LEFT("left", 2),
    RIGHT("right", 3),
    DOWN("down", 0);

    private String name;
    private int spritePosition;

    private Direction(String name, int spritePosition) {
        this.name = name;
        this.spritePosition = spritePosition;
    }

    public String getName() {
        return name;
    }

    public int getSpritePosition() {
        return spritePosition;
    }
}
