package com.mine.castile.data.dom.enums;

public enum Season {
    spring1, spring2, spring3,
    summer1, summer2, summer3,
    fall1, fall2, fall3,
    winter1, winter2, winter3;

    public Season getNextSeason() {
        int next = this.ordinal() + 1;
        if (next >= values().length) {
            next = 0;
        }
        return values()[next];
    }
}
