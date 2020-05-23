package com.mine.castile.data.dom.enums;

public enum Season {
    spring1(0, 0), spring2(1, 0), spring3(2, 0),
    summer1(0, 1), summer2(1, 1), summer3(2, 1),
    fall1(0, 2), fall2(1, 2), fall3(2, 2),
    winter1(0, 3), winter2(1, 3), winter3(2, 3);

    private final int spriteRow;
    private final int spriteColumn;

    Season(int spriteRow, int spriteColumn) {
        this.spriteRow = spriteRow;
        this.spriteColumn = spriteColumn;
    }

    public Season getNextSeason() {
        int next = this.ordinal() + 1;
        if (next >= values().length) {
            next = 0;
        }
        return values()[next];
    }

    public int getSpriteRow() {
        return spriteRow;
    }

    public int getSpriteColumn() {
        return spriteColumn;
    }
}
